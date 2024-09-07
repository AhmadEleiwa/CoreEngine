package scenes;

import engine.GameEngine;
import utils.Texture;

public class Sprite2D extends MeshNode {
    Texture texture;

    public Sprite2D(String name, Texture texture) {
        super(name);
        this.texture = texture;
        this.mesh = new Mesh(texture);
        this.unifroms.put("texture1", 0);
        GameEngine gameEngine = GameEngine.getInstance();
        this.shader = gameEngine.createShaderProgram("default", "assets/shaders/textureShaders/vertix.glsl",
                "assets/shaders/textureShaders/fragment.glsl");

    }
}
