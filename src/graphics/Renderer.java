package graphics;

import org.joml.Matrix4f;

import utils.Camera;

public class Renderer {
    private long window;
    private int windowWidth;
    private int windowHeight;

    private Camera mainCamera;

    public Renderer(long window, int windowWidth,  int windowHeight) {
        this.window  =  window;
        this.windowWidth  = windowWidth;
        this.windowHeight = windowHeight;
    }
    public void clear() {
        // Clear the screen
        org.lwjgl.opengl.GL11.glClear(org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT | org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT);
        org.lwjgl.opengl.GL11.glClearColor(0.5f, 0.5f,0.5f, 0);
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
    public boolean isMainCameraNull(){
        return mainCamera == null;
    }
    public void setMainCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
    }
    public Matrix4f getViewMatrix(){
        return mainCamera.getLocalTransform().getTransformationMatrix();    
    }
    public Matrix4f getProjectionMatrix(){
        return mainCamera.getProjecMatrix4f();    
    }
    public int getWindowWidth() {
        return windowWidth;
    }
    public int getWindowHeight() {
        return windowHeight;
    }

}
