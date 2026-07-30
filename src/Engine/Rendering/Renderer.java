package Engine.Rendering;

import Engine.Window;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.GL_CCW;
import static org.lwjgl.opengl.GL30.*;

public class Renderer {

    private final ShaderProgram shaderProgram;
    private final UniformMap uniformMap;

    public Renderer() {
        GL.createCapabilities();
        glFrontFace(GL_CCW);
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        glEnable(GL_DEPTH_TEST);
        List<ShaderData> shaderDataList = new ArrayList<>();
        shaderDataList.add(new ShaderData("rsc/shaders/vertex.glsl", GL_VERTEX_SHADER));
        shaderDataList.add(new ShaderData("rsc/shaders/fragment.glsl", GL_FRAGMENT_SHADER));
        shaderProgram = new ShaderProgram(shaderDataList);
        uniformMap = new UniformMap(shaderProgram.getProgramId());
        createUniforms();
    }

    public void createUniforms() {
        uniformMap.createUniform("projection");
        uniformMap.createUniform("view");
        uniformMap.createUniform("model");
        uniformMap.createUniform("texSampler");
    }

    public void render(Window window, Scene scene) {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        shaderProgram.bind();
        uniformMap.setUniform("projection", scene.getProjection());
        uniformMap.setUniform("view", scene.getCamera().getView());
        uniformMap.setUniform("texSampler", 0);
        Collection<Model> models = scene.getModelMap().values();
        TextureCache textureCache = scene.getTextureCache();
        for (Model model : models) {
            List<Entity> entityList = model.getEntityList();
            for (Material material : model.getMaterialList()) {
                Texture texture = textureCache.getTexture(material.getTexturePath());
                glActiveTexture(GL_TEXTURE0);
                texture.bind();
                for (Mesh mesh : material.getMeshList()) {
                    glBindVertexArray(mesh.getVaoId());
                    for (Entity entity : entityList) {
                        uniformMap.setUniform("model", entity.getModelMatrix());
                        glDrawElements(GL_TRIANGLES, mesh.getNumVertices(), GL_UNSIGNED_INT, 0);
                    }
                }
            }
        }
        glBindVertexArray(0);
        shaderProgram.unbind();
    }

    public void cleanup() {
        shaderProgram.cleanup();
        uniformMap.cleanup();
    }

}