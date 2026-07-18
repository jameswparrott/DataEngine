package Game;

import Engine.IEngineLogic;
import Engine.Matrix4f;
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
                -0.5f, 0.5f, -2.75f, //V0
                0.5f, 0.5f, -2.75f,  //V1
                0.5f, -0.5f, -2.75f, //V2
                -0.5f, -0.5f, -2.75f //V3
        };
        float[] colors = new float[]{
                0.5f, 0.0f, 0.0f, //R
                0.0f, 0.5f, 0.0f, //G
                0.0f, 0.0f, 0.5f, //B
                0.0f, 0.5f, 0.0f  //G
        };
        int[] indices = new int[]{
                0, 3, 1, 1, 3, 2
        };
        Mesh mesh = new Mesh(new MeshData(positions, colors, indices));
        scene.addMesh("quad", mesh);
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
