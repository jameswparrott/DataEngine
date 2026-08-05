package Engine;

import Engine.Rendering.Renderer;
import Engine.Rendering.Scene;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;

public class Engine {

    private final IEngineLogic iEngineLogic;
    private final Window window;
    private final Time time;
    private final Renderer renderer;
    private final Scene scene;
    private boolean isRunning;

    public Engine(IEngineLogic iEngineLogic) {

        this.window = new Window("", false, true, 800, 600, () -> {
            resize();
            return null;
        });
        this.iEngineLogic = iEngineLogic;
        time = new Time();
        renderer = new Renderer();
        scene = new Scene(window.getWindowWidth(), window.getWindowHeight());
        isRunning = false;
        iEngineLogic.init(window, scene, renderer);

    }

    public void start() {
        isRunning = true;
        run();
    }

    public void run() {
        time.frame();
        while (isRunning && !window.windowShouldClose()) {
            window.pollEvents();
            time.frame();
            if (window.isKeyPressed(GLFW_KEY_P)) {
                System.out.println("update delta: " + time.getFramesPerSecond());
            }
            iEngineLogic.input(window, scene, time.getDeltaFrameTimeSeconds());
            iEngineLogic.update(window, scene, time.getDeltaFrameTimeSeconds());
            renderer.render(window, scene);
            window.update();
        }
        cleanup();
    }

    public void resize() {
        scene.resize(window.getWindowWidth(), window.getWindowHeight());
    }

    public void stop() {
        isRunning = false;
    }

    public void cleanup() {
        iEngineLogic.cleanup();
        renderer.cleanup();
        scene.cleanup();
        window.cleanup();
    }

}