import Engine.Engine;
import Game.Game;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        Engine engine = new Engine(game);
        engine.start();
    }

}