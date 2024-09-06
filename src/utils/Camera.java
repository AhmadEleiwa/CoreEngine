package utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.GameEngine;
import engine.ProgramManager;
import graphics.Renderer;
import scenes.Node;
import scenes.ShaderProgram;

public class Camera extends Node{
    protected ShaderProgram shader;
    protected Transformation transformation;

    public Camera(String name) {
        super(name);
        //TODO Auto-generated constructor stub
        GameEngine gameEngine = GameEngine.getInstance();

        this.shader = gameEngine.createShaderProgram("default", "assets/textureShader/vertix.glsl","assets/textureShader/fragment.glsl");
        transformation = new Transformation();
    }
    @Override
    public void render(Renderer renderer) {
        shader.start();
        updateUniforms();
        shader.setUniformMatrix4f("view", new Matrix4f().identity().translate(new Vector3f(0,0,-5)));
        shader.setUniformMatrix4f("projection", transformation.getProjectionMatrix(70, 800, 600, 0.1f, 100));
        shader.stop();
        super.render(renderer);
    }
    
}
