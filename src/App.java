import org.joml.Vector3f;
import engine.GameEngine;
import scenes.Mesh;
import scenes.NewMeshNode;
import scenes.SceneManager;
import scenes.Sprite2D;
import scripts.PlayerScript;
import utils.Texture;

public class App {
    public static void main(String[] args) {
        GameEngine gameEngine = GameEngine.getInstance();
        float[] rectangleVertices = {
                -0.5f, -0.5f, 0.0f, // Bottom left
                0.5f, -0.5f, 0.0f, // Bottom right
                0.5f, 0.5f, 0.0f, // Top right
                -0.5f, 0.5f, 0.0f // Top left
        };
        int[] rectangleIndices = {  
                0, 1, 2,
                0, 2, 3
        };
        
        // NewMeshNode node = new NewMeshNode("Rect", new Mesh(rectangleVertices, rectangleIndices));
        // node.setColor(new Vector3f(1f, 1f, 1f));

        // NewMeshNode node2 = new NewMeshNode("Rect2", new Mesh(rectangleVertices, rectangleIndices));
        // node2.setColor(new Vector3f(1f, 1f, 1f));
        Sprite2D player =  new Sprite2D("player", gameEngine.createTexture("assets/images/Player.png"));
        player.register(new PlayerScript());
        // player.addChild(node);
        // node.addChild(node2);
        gameEngine.instantiate(player);
        // gameEngine.instantiate(player);
        // System.err.println(gameEngine.getNode("player/Rect/Rect2").getName());
        
        gameEngine.run();

    }
}
