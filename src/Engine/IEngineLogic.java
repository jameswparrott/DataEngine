package Engine;

import Engine.Rendering.Renderer;
import Engine.Rendering.Scene;

public interface IEngineLogic {

    void init(Window window, Scene scene, Renderer renderer);

    void input(Window window, Scene scene, long deltaTimeMillis);

    void update(Window window, Scene scene, long deltaTimeMillis);

    void cleanup();

}
