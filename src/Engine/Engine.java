package Engine;

import Engine.Rendering.Renderer;
import Engine.Rendering.Scene;

public class Engine {

    private final IEngineLogic iEngineLogic;
    private final Window window;
    private final Renderer renderer;
    private final Scene scene;
    private boolean isRunning;
    private final int targetUps;
    private final int targetFps;

    public Engine(WindowData windowData, IEngineLogic iEngineLogic) {

        this.window = new Window(windowData, () -> {
            resize();
            return null;
        });
        this.iEngineLogic = iEngineLogic;
        targetFps = windowData.fps();
        targetUps = windowData.ups();
        renderer = new Renderer();
        scene = new Scene(windowData.windowWidth(), windowData.windowHeight());
        isRunning = false;
        iEngineLogic.init(window, scene, renderer);

    }

    public void start() {
        isRunning = true;
        run();
    }

    public void run() {
        long initialTime = System.currentTimeMillis();

        float timeUpdate = targetUps / 1000.0f; //Inverse of time between update calls
        float timeRender = targetFps / 1000.0f; //Inverse of time between render calls

        float deltaUps = 0;
        float deltaFps = 0;

        long updateTime = initialTime;

        while (isRunning && !window.windowShouldClose()) {

            window.pollEvents();
            long currentTime = System.currentTimeMillis();
            deltaUps += (currentTime - initialTime) * timeUpdate;
            deltaFps += (currentTime - initialTime) * timeRender;

            if (deltaFps >= 1) {
                iEngineLogic.input(window, scene, currentTime - initialTime);
            }
            if (deltaUps >= 1) {
                long deltaUpdateMillis = currentTime - updateTime;
                iEngineLogic.update(window, scene, deltaUpdateMillis);
                deltaUps--;
            }
            if (deltaFps >= 1) {
                renderer.render(window, scene);
                deltaFps--;
                window.update();
            }

            initialTime = currentTime;

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