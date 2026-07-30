package Engine.Rendering;

import Engine.Math.Matrix4f;
import Engine.Math.Quaternionf;
import Engine.Math.Vector3f;

public class Entity {

    private final String id;
    private final String modelId;
    private float scale;
    private Vector3f position;
    private Quaternionf rotation;
    private Matrix4f modelMatrix;

    public Entity(String id, String modelId) {
        this.id = id;
        this.modelId = modelId;

        scale = 1.0f;
        position = new Vector3f();
        rotation = new Quaternionf();
        modelMatrix = new Matrix4f();
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setPosition(Vector3f position) {
        this.position.set(position);
    }

    public void setRotation(Quaternionf rotation) {
        this.rotation.set(rotation);
    }

    public void updateModel() {
        modelMatrix.identity().translate(position).rotate(rotation).scale(scale);
    }

    public String getId() {
        return id;
    }

    public String getModelId() {
        return modelId;
    }

    public float getScale() {
        return scale;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public Matrix4f getModelMatrix() {
        return modelMatrix;
    }

}
