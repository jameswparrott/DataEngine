package Engine.Rendering;

import Engine.Math.Matrix4f;
import Engine.Math.Quaternionf;
import Engine.Math.Vector3f;

public class Camera {

    private Vector3f position;
    private Quaternionf rotation;
    private Matrix4f view;

    public Camera() {
        this(new Vector3f());
    }

    public Camera(Vector3f position) {
        this.position = position;
        this.rotation = new Quaternionf();
        this.view = new Matrix4f();
    }

    public void setPosition(Vector3f position) {
        this.position.set(position);
    }

    public void setRotation(Quaternionf rotation) {
        this.rotation.set(rotation);
    }

    public void updateView() {
        view.identity().rotate(rotation).translateInverse(position);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public Vector3f getForward(Vector3f forward) {
        forward.setX(-view.get(2, 0));
        forward.setY(-view.get(2, 1));
        forward.setZ(-view.get(2, 2));
        return forward;
    }

    public Vector3f getUpward(Vector3f upward) {
        upward.setX(view.get(1, 0));
        upward.setY(view.get(1, 1));
        upward.setZ(view.get(1, 2));
        return upward;
    }

    public Vector3f getRight(Vector3f right) {
        right.setX(view.get(0, 0));
        right.setY(view.get(0, 1));
        right.setZ(view.get(0, 2));
        return right;
    }

    public Matrix4f getView() {
        return view;
    }

}
