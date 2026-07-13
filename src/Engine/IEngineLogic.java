package Engine;

public interface IEngineLogic {

    void init(Window window, Scene scene, Render render);

    void input(Window window, Scene scene, long deltaTimeMillis);

    void update(Window window, Scene scene, long deltaTimeMillis);

    void cleanup();

}
