package Engine.Rendering;

import Engine.Window;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {

    private final ShaderProgram shaderProgram;

    public Renderer() {
        GL.createCapabilities();
        List<ShaderData> shaderDataList = new ArrayList<>();
        shaderDataList.add(new ShaderData("res/shaders/basic.vert", GL_VERTEX_SHADER));
        shaderDataList.add(new ShaderData("res/shaders/basic.frag", GL_FRAGMENT_SHADER));
        shaderProgram = new ShaderProgram(shaderDataList);
    }

    public void render(Window window, Scene scene) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        shaderProgram.bind();

        scene.getMeshMap().values().forEach(mesh -> {
            glBindVertexArray(mesh.getVaoId());
            shaderProgram.validate();
            glDrawArrays(GL_TRIANGLES, 0, mesh.getNumVertices());
        });

        glBindVertexArray(0);

        shaderProgram.unbind();
    }

    public void cleanup() {
        shaderProgram.cleanup();
    }

}