package Engine;

import Engine.Rendering.Renderer;
import Engine.Rendering.Scene;

public class Engine {

    private final IEngineLogic iEngineLogic;
    private final Input input;
    private final Window window;
    private final Time time;
    private final Renderer renderer;
    private final Scene scene;
    private boolean isRunning;

    public Engine(IEngineLogic iEngineLogic) {

        this.input = new Input();
        this.window = new Window("", false, true, 800, 600, input,
                () -> {
                    resize();
                    return null;
                });
        this.iEngineLogic = iEngineLogic;
        time = new Time();
        renderer = new Renderer();
        scene = new Scene(window.getWindowWidth(), window.getWindowHeight());
        isRunning = false;
        iEngineLogic.init(scene, renderer);

    }

    public void start() {
        isRunning = true;
        run();
    }

    public void run() {
        time.frame();
        while (isRunning && !window.windowShouldClose()) {
            time.frame();
            input.update();
            window.pollEvents();
            if (input.isKeyHeld(Input.Key.ESCAPE)){
                window.setWindowShouldClose();
            }
            iEngineLogic.input(input, scene, time.getDeltaFrameTimeSeconds());
            iEngineLogic.update(scene, time.getDeltaFrameTimeSeconds());
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