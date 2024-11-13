package graphics;

import java.util.HashMap;

import org.joml.Matrix4f;

import signals.Event;
import utils.Camera;

public class Renderer {
    private long window;
    private int windowWidth;
    private int windowHeight;

    private Camera mainCamera;

    HashMap<String, Event> eventMap;


    public HashMap<String, Event> getEvent() {
        return eventMap;
    }

    public Renderer(long window, int windowWidth, int windowHeight,  HashMap<String, Event>  event) {
        this.window = window;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.eventMap = event;
    }

    public void clear() {
        // Clear the screen
        org.lwjgl.opengl.GL11
                .glClear(org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT | org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT);
        org.lwjgl.opengl.GL11.glClearColor(0.53f, 0.81f, 0.98f, 0); // Light Sky Blue

    }

    public void cleanup() {
        // Cleanup logic for rendering
    }

    public long getWindow() {
        return window;
    }

    public void updateViewByShader(ShaderProgram shader) {
        mainCamera.updateViewByShader(shader);
    }

    public boolean isMainCameraNull() {
        return mainCamera == null;
    }

    public void setMainCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
    }

    public Matrix4f getViewMatrix() {
        return mainCamera.getLocalTransform().getTransformationMatrix();
    }

    public Matrix4f getProjectionMatrix() {
        return mainCamera.getProjecMatrix4f();
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

}
