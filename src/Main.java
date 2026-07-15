import Engine.Engine;
import Game.Game;
import Engine.WindowData;

public class Main {

    public static void main(String[] args) {
        WindowData windowData = new WindowData("TEST", false, 60, 30, 800, 600);
        Game game = new Game();
        Engine engine = new Engine(windowData, game);
        engine.start();
    }

}