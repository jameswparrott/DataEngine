package Engine.Rendering;

import Engine.Matrix4f;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

public class UniformMap {

    private final int programId;
    private final Map<String, Integer> uniforms;
    private final FloatBuffer matrixBuffer;

    public UniformMap(int programId) {
        this.programId = programId;
        uniforms = new HashMap<>();
        matrixBuffer = MemoryUtil.memCallocFloat(Matrix4f.LENGTH);
    }

    public void createUniform(String uniformName) {
        int uniformLocation = glGetUniformLocation(programId, uniformName);
        if (uniformLocation < 0) {
            throw new RuntimeException("Could not find uniform [" + uniformName + "] in shader program [" + programId + "].");
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public void setUniform(String uniformName, Matrix4f matrix) {
        Integer location = uniforms.get(uniformName);
        if (location == null) {
            throw new RuntimeException("Could not find uniform [" + uniformName + "].");
        }
        matrixBuffer.clear();
        matrix.put(matrixBuffer);
        matrixBuffer.flip();
        glUniformMatrix4fv(location, false, matrixBuffer);
    }

    public void cleanup() {
        MemoryUtil.memFree(matrixBuffer);
    }

}
