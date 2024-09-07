package input;

import org.lwjgl.glfw.GLFW;

public class Input {
    private static long window;

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
