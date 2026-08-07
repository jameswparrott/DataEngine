package Engine;

import org.lwjgl.glfw.*;
import org.lwjgl.system.MemoryUtil;

import java.util.concurrent.Callable;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;

public class Window {

    private final long windowHandle;
    private int windowHeight;
    private int windowWidth;
    private final Input input;
    private final Callable<Void> resizeFunction;
    private final GLFWErrorCallback errorCallback;
    private final GLFWFramebufferSizeCallback framebufferSizeCallback;
    private final GLFWKeyCallback keyCallback;
    private final GLFWMouseButtonCallback mouseButtonCallback;
    private final GLFWCursorPosCallback cursorPosCallback;

    public Window(String title, boolean isProfileCompatible, boolean vsync, int width, int height, Input input, Callable<Void> resizeFunction) {

        this.input = input;
        this.resizeFunction = resizeFunction;

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        if (isProfileCompatible) {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);
        } else {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        }

        if (width > 0 && height > 0) {
            this.windowWidth = width;
            this.windowHeight = height;
        } else {
            glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            if (vidMode != null) {
                windowWidth = vidMode.width();
                windowHeight = vidMode.height();
            }
        }

        windowHandle = glfwCreateWindow(windowWidth, windowHeight, title, NULL, NULL);
        if (windowHandle == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        framebufferSizeCallback = glfwSetFramebufferSizeCallback(windowHandle, (window, w, h) -> resized(w, h));
        errorCallback = glfwSetErrorCallback((int errorCode, long msgPtr) -> System.err.println("Error code: " + errorCode + ", msg: " + MemoryUtil.memUTF8(msgPtr)));
        keyCallback = glfwSetKeyCallback(windowHandle, (window, key, scancode, action, mods) -> {
            input.keyEvent(key, action);
        });
        mouseButtonCallback = glfwSetMouseButtonCallback(windowHandle, (window, button, action, mods) -> {
            input.mouseEvent(button, action);
        });
        cursorPosCallback = glfwSetCursorPosCallback(windowHandle, (window, xpos, ypos) -> {
            input.cursorEvent(xpos, ypos);
        });

        glfwMakeContextCurrent(windowHandle);
        glfwSwapInterval(vsync ? 1 : 0);
        glfwShowWindow(windowHandle);

        int[] arrWidth = new int[1];
        int[] arrHeight = new int[1];
        glfwGetFramebufferSize(windowHandle, arrWidth, arrHeight);
        windowWidth = arrWidth[0];
        windowHeight = arrHeight[0];
    }

    protected void resized(int width, int height) {
        this.windowWidth = width;
        this.windowHeight = height;
        try {
            resizeFunction.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setWindowShouldClose() {
            glfwSetWindowShouldClose(windowHandle, true);
    }

    public void pollEvents() {
        glfwPollEvents();
    }

    public void update() {
        glfwSwapBuffers(windowHandle);
    }

    public long getWindowHandle() {
        return this.windowHandle;
    }

    public int getWindowHeight() {
        return this.windowHeight;
    }

    public int getWindowWidth() {
        return this.windowWidth;
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(windowHandle);
    }

    public void cleanup() {
        if (errorCallback != null) {
            errorCallback.free();
        }
        glfwFreeCallbacks(windowHandle);
        glfwDestroyWindow(windowHandle);
        glfwTerminate();
    }

}
