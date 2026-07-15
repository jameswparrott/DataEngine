package Engine.Rendering;

import Engine.Utils;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL30.*;

public class ShaderProgram {

    private final int programId;

    public ShaderProgram(List<ShaderData> shaderDataList) {
        programId = glCreateProgram();
        if (programId == 0) {
            throw new RuntimeException("Could not create shader!");
        }

        List<Integer> shaderModules = new ArrayList<>();
        shaderDataList.forEach(shaderData -> shaderModules.add(createShader(shaderData.path(), shaderData.type())));
        link(shaderModules);
    }

    private int createShader(String path, int type) {
        int shaderId = glCreateShader(type);
        if (shaderId == 0) {
            throw new RuntimeException("Error creating shader. Type: " + type);
        }
        glShaderSource(shaderId, Utils.readFile(path));
        glCompileShader(shaderId);
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0){
            throw new RuntimeException("Error compiling shader code: " + glGetShaderInfoLog(shaderId, 1024));
        }
        glAttachShader(programId, shaderId);
        return shaderId;
    }

    private void link(List<Integer> shaderModules) {
        glLinkProgram(programId);
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0){
            throw new RuntimeException("Error compiling shader code: " + glGetProgramInfoLog(programId, 1024));
        }
        shaderModules.forEach(shaderModule -> glDetachShader(programId, shaderModule));
        shaderModules.forEach(GL30::glDeleteShader);
    }

    public void validate(){
        glValidateProgram(programId);
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0){
            throw new RuntimeException("Error validating shader code: " + glGetProgramInfoLog(programId, 1024));
        }
    }

    public int getProgramId(){
        return this.programId;
    }

    public void bind() {
        glUseProgram(programId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void cleanup() {
        unbind();
        if (programId != 0) {
            glDeleteProgram(programId);
        }

    }

}