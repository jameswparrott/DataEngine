package Game;

import Engine.IEngineLogic;
import Engine.Input;
import Engine.Math.Quaternionf;
import Engine.Math.Vector3f;
import Engine.Rendering.*;
import Engine.Window;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Game implements IEngineLogic {

    Entity entity;
    float scale;
    Vector3f position;
    Quaternionf rotation;
    Vector3f newCameraPosition;
    Quaternionf newCameraRotation;
    final static Vector3f Y_AXIS = new Vector3f(0, 1, 0);
    final static Vector3f XYZ_AXES = new Vector3f(0.5f, 1, 1.2f);
    float angle = 0.0f;
    float multiple;

    public Game() {

    }

    @Override
    public void init(Scene scene, Renderer renderer) {
        float[] positions = new float[]{
                // Front
                -1, -1, 1,
                1, -1, 1,
                1, 1, 1,
                -1, 1, 1,

                // Back
                1, -1, -1,
                -1, -1, -1,
                -1, 1, -1,
                1, 1, -1,

                // Left
                -1, -1, -1,
                -1, -1, 1,
                -1, 1, 1,
                -1, 1, -1,

                // Right
                1, -1, 1,
                1, -1, -1,
                1, 1, -1,
                1, 1, 1,

                // Top
                -1, 1, 1,
                1, 1, 1,
                1, 1, -1,
                -1, 1, -1,

                // Bottom
                -1, -1, -1,
                1, -1, -1,
                1, -1, 1,
                -1, -1, 1
        };
        float[] texCoords = new float[]{
                0, 0, 1, 0, 1, 1, 0, 1,
                0, 0, 1, 0, 1, 1, 0, 1,
                0, 0, 1, 0, 1, 1, 0, 1,
                0, 0, 1, 0, 1, 1, 0, 1,
                0, 0, 1, 0, 1, 1, 0, 1,
                0, 0, 1, 0, 1, 1, 0, 1
        };
        int[] indices = new int[]{
                0, 1, 2, 2, 3, 0, // Front
                4, 5, 6, 6, 7, 4, // Back
                8, 9, 10, 10, 11, 8, // Left
                12, 13, 14, 14, 15, 12, // Right
                16, 17, 18, 18, 19, 16, // Top
                20, 21, 22, 22, 23, 20  // Bottom
        };

        Texture texture = scene.addTexture("rsc/textures/default_texture.png");
        Mesh mesh = new Mesh(new MeshData(positions, texCoords, indices));
        Material material = new Material();
        material.setTexturePath(texture.getTexturePath());
        ArrayList<Material> materialList = new ArrayList<>();
        material.addMesh(mesh);
        materialList.add(material);

        String modelId = "cubeModel";

        Model model = new Model(modelId, materialList);
        scene.addModel(model);

        newCameraPosition = new Vector3f();
        newCameraRotation = new Quaternionf();

        XYZ_AXES.norm();
        scale = 1;
        multiple = 0;
        position = new Vector3f(0.0f, 0.0f, -3.0f);
        rotation = new Quaternionf();

        entity = new Entity("cubeEntity", modelId);
        entity.setRotation(rotation);
        entity.setPosition(position);
        entity.updateModel();
        scene.addEntity(entity);
    }

    @Override
    public void input(Input input, Scene scene, float deltaTimeSeconds) {
        Vector3f oldCameraPosition = scene.getCamera().getPosition();
        Quaternionf oldCameraRotation = scene.getCamera().getRotation();
        newCameraPosition.set(oldCameraPosition);
        newCameraRotation.set(oldCameraRotation);

        Vector3f moveVector = new Vector3f();
        Vector3f forward = new Vector3f();
        Vector3f backward = new Vector3f();
        Vector3f right = new Vector3f();
        Vector3f left = new Vector3f();

        scene.getCamera().getForward(forward);
        backward.set(forward).scale(-1.0f);
        scene.getCamera().getRight(right);
        left.set(right).scale(-1.0f);

        float moveAmount = deltaTimeSeconds * 5.0f;
        float rotationAmount = 0;

        if (input.isKeyHeld(Input.Key.W)) {
            moveVector.add(forward);
        }
        if (input.isKeyHeld(Input.Key.S)) {
            moveVector.add(backward);
        }
        if (input.isKeyHeld(Input.Key.A)) {
            moveVector.add(left);
        }
        if (input.isKeyHeld(Input.Key.D)) {
            moveVector.add(right);
        }
        if (input.isKeyHeld(Input.Key.Q)) {
            rotationAmount += 1;
        }
        if (input.isKeyHeld(Input.Key.E)) {
            rotationAmount -= 1;
        }
        if (input.isKeyHeld(Input.Key.O)) {
            System.out.println("input delta: " + deltaTimeSeconds);
        }
        if (moveVector.lenSq() != 0 && moveVector.lenSq() != 1) {
            moveVector.norm();
        }
        moveVector.scale(moveAmount);
        rotationAmount *= 4 * deltaTimeSeconds;
        newCameraPosition.add(moveVector);
        Quaternionf updateRotation = new Quaternionf();
        updateRotation.initRotation(rotationAmount, Y_AXIS);
        newCameraRotation.mul(updateRotation);
        newCameraRotation.norm();

        angle += deltaTimeSeconds;
        multiple += deltaTimeSeconds;
        rotation.initRotation(angle, Y_AXIS);
    }

    @Override
    public void update(Scene scene, float deltaTimeSeconds) {
        entity.updateModel();
        Camera camera = scene.getCamera();
        camera.setRotation(newCameraRotation);
        camera.setPosition(newCameraPosition);
        camera.updateView();
    }

    @Override
    public void cleanup() {

    }

}
