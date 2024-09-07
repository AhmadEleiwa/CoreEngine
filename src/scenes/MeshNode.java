package scenes;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import engine.GameEngine;
import graphics.Renderer;
import graphics.ShaderProgram;
import graphics.Mesh;


public class MeshNode extends Node {
    protected Mesh mesh;
    protected ShaderProgram shader;
    protected HashMap<String, Object> unifroms = new HashMap<>();

    protected MeshNode(String name) {
        super(name);
    }

    public MeshNode(String name, Mesh mesh) {
        super(name);
        this.mesh = mesh;
        this.shader = GameEngine.getInstance().createShaderProgram("meshNode", "assets/shaders/vertix.glsl",
                "assets/shaders/fragment.glsl");
    }

    public MeshNode(String name, Mesh mesh, ShaderProgram shader) {
        super(name);
        this.mesh = mesh;
        this.shader = shader;
    }

    @Override
    public void render(Renderer renderer) {
        shader.start();
        if (!renderer.isMainCameraNull()) {
            renderer.updateViewByShader(shader);
        }
        updateUniforms();
        mesh.render();

        shader.stop();

        super.render(renderer);
    }

    public void cleanup() {
        mesh.cleanup();
    }

    @Override
    protected void updateUniforms() {
        /**
         * Method to update the uniforms
         * Make sure to call the super to call the base class and add your code
         */
        shader.setUniformMatrix4f("model", globalTransform.getTransformationMatrix());

        for (Map.Entry<String, Object> entry : unifroms.entrySet()) {
            String uniformName = entry.getKey();
            Object value = entry.getValue();

            // Set the uniform based on its type
            if (value instanceof Vector3f) {
                shader.setUniform3v(uniformName, (Vector3f) value);
            } else if (value instanceof Matrix4f) {
                // Handle other data types as needed
            } else if (value instanceof Integer) {
                shader.setUniform1ui(uniformName, (Integer) value);
            } else {
                System.err.println("Unsupported uniform type: " + value.getClass().getName());
            }
        }
    }
}
