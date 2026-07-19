package Engine.Rendering;

import Engine.Window;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {

    private final ShaderProgram shaderProgram;
    private final UniformMap uniformMap;

    public Renderer() {
        GL.createCapabilities();
        List<ShaderData> shaderDataList = new ArrayList<>();
        shaderDataList.add(new ShaderData("res/shaders/vertex.glsl", GL_VERTEX_SHADER));
        shaderDataList.add(new ShaderData("res/shaders/fragment.glsl", GL_FRAGMENT_SHADER));
        shaderProgram = new ShaderProgram(shaderDataList);
        uniformMap = new UniformMap(shaderProgram.getProgramId());
        createUniforms();
    }

    public void createUniforms() {
        uniformMap.createUniform("projection");
    }

    public void render(Window window, Scene scene) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        shaderProgram.bind();
        uniformMap.setUniform("projection", scene.getProjection());
        scene.getMeshMap().values().forEach(mesh -> {
            glBindVertexArray(mesh.getVaoId());
            shaderProgram.validate();
            glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
        });
        glBindVertexArray(0);
        shaderProgram.unbind();
    }

    public void cleanup() {
        shaderProgram.cleanup();
        uniformMap.cleanup();
    }

}