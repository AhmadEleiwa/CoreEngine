import org.joml.Vector3f;
import engine.GameEngine;
import graphics.Mesh;
import scenes.MeshNode;
import scenes.Node;
import scenes.Sprite2D;
import scripts.CameraScript;
import scripts.PlayerScript;
import scripts.RectScript;
import utils.Camera;

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

        Node node = new Node("root");
        MeshNode node2 = new MeshNode("Rect2", new Mesh(rectangleVertices, rectangleIndices));

        Sprite2D player = new Sprite2D("player", gameEngine.createTexture("assets/images/Player.png"));
        player.register(new PlayerScript());
        Sprite2D player2 = new Sprite2D("player2", gameEngine.createTexture("assets/images/Player.png"));

        player2.getLocalTransform().setPosition(new Vector3f(-2, 0, 0));
        player.getLocalTransform().setPosition(new Vector3f(0, 0, 0));
        Camera camera = new Camera("MainCamera");
        CameraScript cameraScript  = new CameraScript();
        camera.register(cameraScript);
        player2.addChild(node2);
        node2.getLocalTransform().setPosition(new Vector3f(4, 0, 0));

        player.addChild(camera);
        node.addChild(player2);
        node.addChild(player);
        // gameEngine.setMainCamera(camera);
        gameEngine.instantiate(node);


        node2.register(new RectScript());


        gameEngine.run();

    }
}
