package input;

import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class Input {
    private static long window;

    private static final Map<Integer, Boolean> keyStates = new HashMap<>();
    private static final Map<Integer, Boolean> mouseButtonStates = new HashMap<>();

    public static void initialize(long windowHandle) {
        window = windowHandle;
    }



    public static boolean isKeyDown(int key) {
        return GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;

    }

    public static boolean isMouseButtonDown(int button) {
        return GLFW.glfwGetMouseButton(window, button) == GLFW.GLFW_PRESS;
    }

}
