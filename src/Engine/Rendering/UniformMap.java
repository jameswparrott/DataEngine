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

    private int programId;
    private Map<String, Integer> uniforms;

    public UniformMap(int programId) {
        this.programId = programId;
        uniforms = new HashMap<>();
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
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer matrixBuffer = stack.mallocFloat(16);
            matrixBuffer.put(0, matrix.getComponents());
            glUniformMatrix4fv(location, false, matrixBuffer);
        }
        //TODO: Rewrite to assign memory allocated buffer for duration of rendering loop
        /*FloatBuffer matrixBuffer = MemoryUtil.memCallocFloat(Matrix4f.LENGTH);
        matrixBuffer.put(0, matrix.getComponents());
        glUniformMatrix4fv(location, false, matrixBuffer);
        MemoryUtil.memFree(matrixBuffer);*/
    }

    public void cleanup() {
        //TODO: Free memory
    }

}
