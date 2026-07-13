package Engine;

public interface IEngineLogic {

    void init(Window window, Scene scene, Render render);

    void input(Window window, Scene scene, long deltaTimeMillisec);

    void update(Window window, Scene scene, long deltaTimeMillisec);

    void cleanup();

}
