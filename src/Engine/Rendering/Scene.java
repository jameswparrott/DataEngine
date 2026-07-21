package Engine.Rendering;

import Engine.Math.Matrix4f;

import java.util.HashMap;
import java.util.Map;

public class Scene {

    private static final float FOV = (float) Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.050f;
    private static final float Z_FAR = 1000.0f;

    private Matrix4f projection;

    private Map<String, Mesh> meshMap;

    public Scene(int width, int height) {
        meshMap = new HashMap<>();
        projection = new Matrix4f();
        projection.initPerspective(FOV, (float) width / height, Z_NEAR, Z_FAR);
        System.out.println(projection);
    }

    public void addMesh(String meshId, Mesh mesh) {
        meshMap.put(meshId, mesh);
    }

    public Map<String, Mesh> getMeshMap() {
        return meshMap;
    }

    public void resize(int width, int height) {
        projection.initPerspective(FOV, (float) width / height, Z_NEAR, Z_FAR);
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public void cleanup() {

    }

}