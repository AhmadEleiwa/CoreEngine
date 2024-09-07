package engine;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import graphics.Renderer;
import graphics.SceneManager;
import graphics.ShaderProgram;
import input.Input;

import scenes.Node;
import utils.Camera;
import utils.Texture;

public class GameEngine {
    private Renderer renderer;
    private SceneManager sceneManager;
    private ProgramManager programManager;

    private long window;

    private HashMap<String, Texture> textureStore;

    private static GameEngine instance;


    private int width = 800;
    private int height = 600;

    public static GameEngine getInstance() {
        if (instance == null) {
            synchronized (GameEngine.class) {
                if (instance == null) { // Double-checked locking
                    instance = new GameEngine();
                }
            }
        }
        return instance;
    }

    private GameEngine() {
        init();

        renderer = new Renderer(window);
        sceneManager = new SceneManager();
        programManager = new ProgramManager();
        // inputManager = new InputManager(window);

        textureStore = new HashMap<>();
    }

    private void init() {
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints(); // optional, the current window hints are already the default
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // the window will stay hidden after creation
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE); // the window will be resizable
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        // Create a window
        window = GLFW.glfwCreateWindow(width, height, "Game Engine", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        Input.initialize(window);

        // Center the window
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        if (vidMode != null) {
            GLFW.glfwSetWindowPos(
                    window,
                    (vidMode.width() - 800) / 2,
                    (vidMode.height() - 600) / 2);
        }

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GLFW.glfwSetWindowSizeCallback(window, (window, width, height) -> {
            // Update the viewport to match the new window size
            GL11.glViewport(0, 0, width, height);
        });
        GLFW.glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            GL11.glViewport(0, 0, width, height);
        });
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_DEBUG_CONTEXT, GLFW.GLFW_TRUE);
        GLFWErrorCallback errorCallback = GLFW.glfwSetErrorCallback(null);
        if (errorCallback != null) {
            errorCallback.free();
        }

        // Make the window visible
        GLFW.glfwShowWindow(window);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GLFW.glfwSwapInterval(1); // Enable V-Sync

    }

    public void run() {
        double currentFrame = GLFW.glfwGetTime();
        double lastFrame = currentFrame;
        double deltaTime;
        sceneManager.start();
        while (!GLFW.glfwWindowShouldClose(window)) {
            currentFrame = GLFW.glfwGetTime();
            deltaTime = currentFrame - lastFrame;
            lastFrame = currentFrame;

            // Input.update();
            // programManager.
            sceneManager.update(deltaTime);

            renderer.clear();
            sceneManager.render(renderer);

            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();

        }
        cleanup();
    }

    private void cleanup() {
        sceneManager.cleanup();
        renderer.cleanup();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
    // Controllers

    public Texture createTexture(String path) {
        if (textureStore.get(path) == null) {
            Texture texture = new Texture(path);
            textureStore.put(path, texture);
            return texture;
        } else {
            return textureStore.get(path);
        }
    }

    public void instantiate(Node node) {
        sceneManager.addRootNode(node);
    }
    public void setMainCamera(Camera camera){
        renderer.setMainCamera(camera);
    }
    public List<Node> findNodesByName(String name) {
        return sceneManager.getNodesByName(name);
    }

    public Node getNode(String path) {
        return sceneManager.getNodeByPath(path);
    }

    public ShaderProgram createShaderProgram(String name, String vertixShaderPath, String fragmentShaderPath) {
        return programManager.instance(name, vertixShaderPath, fragmentShaderPath);
    }
    public ShaderProgram getProgram(String name){
        return programManager.getProgram(name);
    }
    public void startProgram(String name){
        programManager.startProgram(name);
    }
    public void stopProgram(String name){
        programManager.stopProgram(name);
    }
    public Set<String> getProgramsNameSet(){
        return programManager.getKeys();
    }
    public boolean isKeyPressed(int key) {
        return GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
    }

}
