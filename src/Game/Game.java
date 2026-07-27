package Game;

import Engine.IEngineLogic;
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
    final static Vector3f Y_AXIS = new Vector3f(0, 1, 0);
    final static Vector3f XYZ_AXES = new Vector3f(0.5f, 1, 1.2f);
    float angle = 0.0f;
    float multiple;

    public Game() {

    }

    @Override
    public void init(Window window, Scene scene, Renderer renderer) {
        float[] positions = new float[]{
                -0.5f, 0.5f, -0.5f, //V0
                -0.5f, -0.5f, -0.5f, //V1
                0.5f, -0.5f, -0.5f, //V2
                0.5f, 0.5f, -0.5f, //V3
                0.5f, -0.5f, 0.5f, //V4
                0.5f, 0.5f, 0.5f, //V5
                -0.5f, -0.5f, 0.5f, //V6
                -0.5f, 0.5f, 0.5f, //V7

        };
        float[] colors = new float[]{
                0.5f, 0.0f, 0.0f, //R
                0.0f, 0.5f, 0.0f, //G
                0.0f, 0.0f, 0.5f, //B
                0.0f, 0.5f, 0.0f, //G
                0.0f, 0.5f, 0.0f, //G
                0.5f, 0.0f, 0.0f, //R
                0.0f, 0.0f, 0.5f, //B
                0.0f, 0.5f, 0.0f  //G
        };
        int[] indices = new int[]{
                0, 1, 3,
                1, 2, 3,
                3, 2, 5,
                5, 2, 4,
                5, 4, 6,
                6, 7, 5,
                7, 6, 1,
                1, 0, 7,
                7, 0, 3,
                3, 5, 7,
                1, 6, 2,
                2, 6, 4
        };
        Mesh mesh = new Mesh(new MeshData(positions, colors, indices));
        ArrayList<Mesh> meshList = new ArrayList<>();
        meshList.add(mesh);
        String modelId = "cubeModel";
        Model model = new Model(modelId, meshList);
        scene.addModel(model);

        XYZ_AXES.norm();

        scale = 0;
        multiple = 0;
        position = new Vector3f(0.0f, 0.0f, -5.0f);
        rotation = new Quaternionf();

        entity = new Entity("cubeEntity", modelId);
        entity.setRotation(rotation);
        entity.setPosition(position);
        entity.updateModelMatrix();
        scene.addEntity(entity);
    }

    @Override
    public void input(Window window, Scene scene, long deltaTimeMillis) {
        float delta = (float) deltaTimeMillis / 1000.0f;
        if (window.isKeyPressed(GLFW_KEY_W)) {
            position.setY(5f * delta + entity.getPosition().getY());
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {
            position.setY(-5f * delta + entity.getPosition().getY());
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            position.setX(-5f * delta + entity.getPosition().getX());
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
            position.setX(5f * delta + entity.getPosition().getX());
        }
        if (window.isKeyPressed(GLFW_KEY_Q)) {
            position.setZ(-5f * delta + entity.getPosition().getZ());
        }
        if (window.isKeyPressed(GLFW_KEY_E)) {
            position.setZ(5f * delta + entity.getPosition().getZ());
        }
        angle += 5f * delta;
        multiple += delta;
        rotation.initRotation(angle, XYZ_AXES);
        scale = 1.5f + (float) Math.sin(Math.PI * multiple);
        entity.setScale(scale);
        entity.setRotation(rotation);
        entity.setPosition(position);
    }

    @Override
    public void update(Window window, Scene scene, long deltaTimeMillis) {
        entity.updateModelMatrix();
    }

    @Override
    public void cleanup() {

    }

}
