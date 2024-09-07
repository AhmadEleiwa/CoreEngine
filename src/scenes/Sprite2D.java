package scenes;

import engine.GameEngine;
import graphics.Mesh;
import utils.Texture;

public class Sprite2D extends MeshNode {
    Texture texture;

    public Sprite2D(String name, Texture texture) {
        super(name);
        this.texture = texture;
        this.mesh = new Mesh(texture);
        this.unifroms.put("texture1", 0);
        this.shader = GameEngine.getInstance().createShaderProgram("default", "assets/shaders/textureShaders/vertix.glsl",
                "assets/shaders/textureShaders/fragment.glsl");

    }
}
