package Engine.Rendering;

import Engine.Math.Matrix4f;

import java.util.HashMap;
import java.util.Map;

public class Scene {

    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.050f;
    private static final float Z_FAR = 1000.0f;

    private Matrix4f projection;

    private Camera camera;

    private Map<String, Model> modelMap;
    private TextureCache textureCache;

    public Scene(int width, int height) {
        textureCache = new TextureCache();
        modelMap = new HashMap<>();
        camera = new Camera();
        projection = new Matrix4f();
        projection.initPerspective(FOV, (float) width / height, Z_NEAR, Z_FAR);
    }

    public void addEntity(Entity entity) {
        String modelId = entity.getModelId();
        Model model = modelMap.get(modelId);
        if (model == null) {
            throw new RuntimeException("Could not find model [" + modelId + "]");
        }
        model.getEntityList().add(entity);
    }

    public void addModel(Model model) {
        modelMap.put(model.getId(), model);
    }

    public Texture addTexture(String texPath) {
        return textureCache.createTexture(texPath);
    }

    public void resize(int width, int height) {
        projection.initPerspective(FOV, (float) width / height, Z_NEAR, Z_FAR);
    }

    public TextureCache getTextureCache() {
        return textureCache;
    }

    public Map<String, Model> getModelMap() {
        return modelMap;
    }

    public Camera getCamera() {
        return camera;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public void cleanup() {
        modelMap.values().forEach(Model::cleanup);
    }

}