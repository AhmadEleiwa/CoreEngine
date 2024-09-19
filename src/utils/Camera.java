package utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.GameEngine;
import graphics.Renderer;
import graphics.ShaderProgram;
import scenes.Node;

public class Camera extends Node {
    protected Transformation transformation;
    // Target point to look at
    protected Vector3f target = new Vector3f(0, 0, 0);

    protected Matrix4f viewMatrix4f;
    protected Matrix4f projectionMatrix4f;
    protected GameEngine gameEngine;

    public Camera(String name) {
        super(name);
        // TODO Auto-generated constructor stub

        this.gameEngine = GameEngine.getInstance();
        transformation = new Transformation();
        projectionMatrix4f = transformation.getProjectionMatrix(70, 800, 600, 0.1f, 100);
        localTransform.setPosition(new Vector3f(0, 0, 10));
        viewMatrix4f = new Matrix4f().identity().lookAt(localTransform.position, target, up);
    }

    @Override
    public void render(Renderer renderer) {

        target = new Vector3f(globalTransform.position);
        target.z -= 1;
        viewMatrix4f = new Matrix4f().identity().lookAt(globalTransform.position, target, up);
        updateUniforms();

        super.render(renderer);
    }

    public void updateViewByShader(ShaderProgram shader) {
        shader.setUniformMatrix4f("view", viewMatrix4f);
        shader.setUniformMatrix4f("projection", transformation.getProjectionMatrix(70, 800, 600, 0.1f, 100));
    }

    @Override
    protected void updateUniforms() {
        super.updateUniforms();
    }

    public Vector3f getTarget() {
        return target;
    }

    public void setTarget(Vector3f target) {
        this.target = target;
    }
    public Matrix4f getProjecMatrix4f(){
        return this.projectionMatrix4f;
    }

    // Up direction (usually along the Y axis)
    protected Vector3f up = new Vector3f(0, 1, 0);

    public Vector3f getUp() {
        return up;
    }

    public void setUp(Vector3f up) {
        this.up = up;
    }
}
