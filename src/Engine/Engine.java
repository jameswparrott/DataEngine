package Engine;

public class Engine {

    private final IEngineLogic iEngineLogic;
    private final Window window;
    private Render render;
    private Scene scene;
    private boolean isRunning;
    private int targetUps;
    private int targetFps;

    public Engine(WindowData windowData, IEngineLogic iEngineLogic){

        this.window = new Window(windowData, () -> {resize(); return null;});
        this.iEngineLogic = iEngineLogic;
        targetFps = windowData.fps();
        targetUps = windowData.ups();
        render = new Render();
        scene = new Scene();
        isRunning = false;
        iEngineLogic.init(window, scene, render);

    }

    public void start(){
        isRunning = true;
        run();
    }

    public void run(){
        long initialTime = System.currentTimeMillis();

        float timeUpdate = targetUps/1000.0f; //Inverse of time between update calls
        float timeRender = targetFps/1000.0f; //Inverse of time between render calls

        float deltaUps   = 0;
        float deltaFps   = 0;

        long updateTime = initialTime;

        while(isRunning && !window.windowShouldClose()){

            window.pollEvents();
            long currentTime = System.currentTimeMillis();
            deltaUps += (currentTime - initialTime) * timeUpdate;
            deltaFps += (currentTime - initialTime) * timeRender;

            if (deltaFps >= 1){
                iEngineLogic.input(window, scene, currentTime - initialTime);
            }
            if (deltaUps >= 1){
                long deltaUpdateMillisec = currentTime - updateTime;
                iEngineLogic.update(window, scene, deltaUpdateMillisec);
                deltaUps --;
            }
            if (deltaFps >= 1){
                render.render(window, scene);
                deltaFps --;
                window.update();
            }

            initialTime = currentTime;

        }

        cleanup();

    }

    public void resize(){

    }

    public void stop(){
        isRunning = false;
    }

    public void cleanup(){
        iEngineLogic.cleanup();
        render.cleanup();
        scene.cleanup();
        window.cleanup();
    }

}