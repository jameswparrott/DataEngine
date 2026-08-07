package Engine;

import Engine.Rendering.Renderer;
import Engine.Rendering.Scene;

public interface IEngineLogic {

    void init(Scene scene, Renderer renderer);

    void input(Input input, Scene scene, float deltaTimeSeconds);

    void update(Scene scene, float deltaTimeSeconds);

    void cleanup();

}
