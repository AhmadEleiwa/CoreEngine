package input;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;

import engine.GameEngine;
import graphics.Renderer;

public class Input {
    private static long window;
    private static double mouseX, mouseY;

    public static void initialize(long windowHandle) {
        window = windowHandle;

        // Set up a callback to update the mouse position
        GLFW.glfwSetCursorPosCallback(window, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;
            }
        });
    }

    public static boolean isKeyDown(int key) {
        return GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
    }

    public static boolean isMouseButtonDown(int button) {
        return GLFW.glfwGetMouseButton(window, button) == GLFW.GLFW_PRESS;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public static Vector2f getMousePosition() {
        return new Vector2f((float)mouseX, (float)mouseY);
    }

    public static Vector3f getMouseWorldPosition() {
        if (GameEngine.getInstance() == null) {
            return null;
        }
        Renderer renderer = GameEngine.getInstance().getRenderer();
        int screenWidth = renderer.getWindowWidth();
        int screenHeight = renderer.getWindowHeight();

        // Get normalized device coordinates
        float x = (float) ((2.0f * mouseX) / screenWidth - 1.0f);
        float y = (float) (1.0f - (2.0f * mouseY) / screenHeight);
        float z = 1.0f;

        // Create a vector from NDC
        Vector3f ndc = new Vector3f(x, y, z);

        // Unproject the coordinates
        Matrix4f viewMatrix = renderer.getViewMatrix();
        Matrix4f projectionMatrix = renderer.getProjectionMatrix();
        Matrix4f viewProjectionMatrix = new Matrix4f();
        projectionMatrix.mul(viewMatrix, viewProjectionMatrix);
        Matrix4f inverseViewProjection = viewProjectionMatrix.invert();

        // Transform to world coordinates
        Vector3f worldPos = ndc.mulProject(inverseViewProjection);

        return worldPos;
    }
}
