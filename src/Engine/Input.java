package Engine;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class Input {

    public enum Key {
        ESCAPE(GLFW_KEY_ESCAPE),
        SPACE(GLFW_KEY_SPACE),
        W(GLFW_KEY_W),
        A(GLFW_KEY_A),
        S(GLFW_KEY_S),
        D(GLFW_KEY_D),
        Q(GLFW_KEY_Q),
        E(GLFW_KEY_E),
        O(GLFW_KEY_O);

        private final int glfwKeyCode;

        Key(int glfwKeyCode){
            this.glfwKeyCode = glfwKeyCode;
        }

        public int glfwKeyCode(){
            return glfwKeyCode;
        }
    }

    private static final Key[] glfwKeys = new Key[GLFW_KEY_LAST + 1];

    public boolean[] keyPressed;
    public boolean[] keyReleased;
    public boolean[] keyHeld;

    public Input() {

        for (Key key : Key.values()){
            glfwKeys[key.glfwKeyCode()] = key;
        }

        keyPressed = new boolean[Key.values().length];
        keyReleased = new boolean[Key.values().length];
        keyHeld = new boolean[Key.values().length];

    }

    public void keyEvent(int key, int action) {
        Key mappedKey = glfwKeys[key];
        if (mappedKey == null){
            return;
        }
        switch (action){
            case GLFW_PRESS -> {
                keyPressed[mappedKey.ordinal()] = true;
                keyHeld[mappedKey.ordinal()] = true;
            }
            case GLFW_RELEASE -> {
                keyReleased[mappedKey.ordinal()] = true;
                keyHeld[mappedKey.ordinal()] = false;
            }
        }
    }

    public void mouseEvent(int button, int action) {
    }

    public void cursorEvent(double xpos, double ypos) {
    }

    public void update(){
        Arrays.fill(keyPressed, false);
        Arrays.fill(keyReleased, false);
    }

    public boolean isKeyPressed(Key key){
        return keyPressed[key.ordinal()];
    }

    public boolean isKeyReleased(Key key){
        return keyReleased[key.ordinal()];
    }

    public boolean isKeyHeld(Key key){
        return keyHeld[key.ordinal()];
    }

}
