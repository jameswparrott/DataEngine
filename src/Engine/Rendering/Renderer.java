package Engine.Rendering;

import Engine.Window;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {

    private final ShaderProgram shaderProgram;
    private final UniformMap uniformMap;

    public Renderer() {
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        List<ShaderData> shaderDataList = new ArrayList<>();
        shaderDataList.add(new ShaderData("res/shaders/vertex.glsl", GL_VERTEX_SHADER));
        shaderDataList.add(new ShaderData("res/shaders/fragment.glsl", GL_FRAGMENT_SHADER));
        shaderProgram = new ShaderProgram(shaderDataList);
        uniformMap = new UniformMap(shaderProgram.getProgramId());
        createUniforms();
    }

    public void createUniforms() {
        uniformMap.createUniform("projection");
        uniformMap.createUniform("model");
    }

    public void render(Window window, Scene scene) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        shaderProgram.bind();
        uniformMap.setUniform("projection", scene.getProjection());
        Collection<Model> models = scene.getModelMap().values();
        for (Model model : models) {
            model.getMeshList().forEach(mesh -> {
                glBindVertexArray(mesh.getVaoId());
                List<Entity> entities = model.getEntityList();
                for (Entity entity : entities) {
                    uniformMap.setUniform("model", entity.getModelMatrix());
                    glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
                }
            });
        }
        glBindVertexArray(0);
        shaderProgram.unbind();
    }

    public void cleanup() {
        shaderProgram.cleanup();
        uniformMap.cleanup();
    }

}