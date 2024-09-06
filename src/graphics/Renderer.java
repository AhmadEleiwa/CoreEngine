package graphics;

public class Renderer {
    public Renderer(long window) {
    }
    public void clear() {
        // Clear the screen
        org.lwjgl.opengl.GL11.glClear(org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT | org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT);
        org.lwjgl.opengl.GL11.glClearColor(0.5f, 0.5f,0.5f, 0);
    }

    public void cleanup() {
        // Cleanup logic for rendering
    }
}
