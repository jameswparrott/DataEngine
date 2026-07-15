package Game;

import Engine.IEngineLogic;
import Engine.Rendering.Mesh;
import Engine.Rendering.MeshData;
import Engine.Rendering.Renderer;
import Engine.Rendering.Scene;
import Engine.Window;

public class Game implements IEngineLogic {

    public Game() {

    }

    @Override
    public void init(Window window, Scene scene, Renderer renderer) {
        float[] positions = new float[]{
                0.0f, 0.2f, 0.0f,
                -0.2f, -0.2f, 0.0f,
                0.2f, -0.2f, 0.0f
        };
        Mesh mesh = new Mesh(new MeshData(3, positions));
        scene.addMesh("triangle", mesh);
    }

    @Override
    public void input(Window window, Scene scene, long deltaTimeMillis) {

    }

    @Override
    public void update(Window window, Scene scene, long deltaTimeMillis) {

    }

    @Override
    public void cleanup() {

    }

}
