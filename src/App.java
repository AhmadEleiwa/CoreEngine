import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector3f;
import engine.GameEngine;
import scenes.Node;
import scenes.Sprite2D;
import scripts.CameraScript;
import scripts.PlayerScript;
import utils.Camera;
import utils.Collision2D;

public class App {

    public static void main(String[] args) {
        GameEngine gameEngine = GameEngine.getInstance();

        Node node = new Node("root");

        Sprite2D player = new Sprite2D("player", gameEngine.createTexture("assets/images/Player.png"));
        player.addChild(new Collision2D(0.8f, 0.6f));

        PlayerScript sc = new PlayerScript();

        player.register(sc);

        player.getLocalTransform().setPosition(new Vector3f(0, 0, 0));
        Camera camera = new Camera("MainCamera");
        CameraScript cameraScript = new CameraScript();
        camera.register(cameraScript);
        var grounds = App.getValidGroundSprites();
        // sc.grounds = grounds;
        player.addChild(camera);
        node.addChild(player);
        List<Collision2D> groundsColl = new ArrayList<Collision2D>();
        for (Sprite2D s : grounds) {
            node.addChild(s);
            groundsColl.add((Collision2D) s.getChildByName("Collision"));
        }
        sc.grounds = groundsColl;

        // Background
        Sprite2D bg1 = new Sprite2D("bg1", gameEngine.createTexture("assets/images/background_layer_1.png"));
        bg1.getLocalTransform().addScale(new Vector3f(100, 100, 1));
        bg1.getLocalTransform().setPosition(new Vector3f(0, 0, -50));
        node.addChild(bg1);

        Sprite2D bg2 = new Sprite2D("bg1", gameEngine.createTexture("assets/images/background_layer_2.png"));
        bg2.getLocalTransform().addScale(new Vector3f(70, 70, 1));
        bg2.getLocalTransform().setPosition(new Vector3f(0, 0, -30));
        node.addChild(bg2);

        Sprite2D bg3 = new Sprite2D("bg1", gameEngine.createTexture("assets/images/background_layer_3.png"));
        bg3.getLocalTransform().addScale(new Vector3f(40, 40, 1));
        bg3.getLocalTransform().setPosition(new Vector3f(0, 0, -20));
        node.addChild(bg3);
        Sprite2D bg4 = new Sprite2D("bg1", gameEngine.createTexture("assets/images/background_layer_3.png"));
        bg4.getLocalTransform().addScale(new Vector3f(40, 40, 1));
        bg4.getLocalTransform().setPosition(new Vector3f(70, 0, -20));
        node.addChild(bg4);
        gameEngine.instantiate(node);

        gameEngine.run();

    }

    public static List<Sprite2D> getValidGroundSprites() {
        GameEngine gameEngine = GameEngine.getInstance();
        List<Sprite2D> validGroundSprites = new ArrayList<>();
        Random random = new Random();

        // Define boundaries and parameters
        final float minY = -1.5f;  // Minimum Y position
        final float maxY = 3f;     // Maximum Y position
        final float minXDistance = 4f;  // Minimum horizontal distance between platforms
        final float maxXDistance = 6f; // Maximum horizontal distance between platforms
        final float minYChange = -1.5f; // Minimum change in Y (up or down)
        final float maxYChange = 1.5f;  // Maximum change in Y (up or down)
        
        float currentY = 0f; // Starting Y position
        float currentX = 0f; // Starting X position

        // Generate 15 ground sprites
        for (int i = 0; i < 15; i++) {
            // Create the collision shape for the ground sprite
            Collision2D groundColl = new Collision2D(1.8f, 0.2f);
            groundColl.getLocalTransform().setPositionY(-0.2f); // Position adjustment for collision

            // Create the ground sprite with texture
            Sprite2D groundSprite2d = new Sprite2D("Ground", gameEngine.createTexture("assets/images/background.png"));
            groundSprite2d.addChild(groundColl);
            groundSprite2d.getGlobalTransform().addScale(new Vector3f(2, 2, 1)); // Scaling the sprite

            // Randomize Y position within specified range and clamp it
            currentY += minYChange + random.nextFloat() * (maxYChange - minYChange);
            currentY = Math.max(minY, Math.min(maxY, currentY)); // Clamp Y to minY and maxY bounds

            // Set the sprite's position
            groundSprite2d.getLocalTransform().setPosition(new Vector3f(currentX, currentY, 0));
            validGroundSprites.add(groundSprite2d);

            // Increment X by a random distance for the next sprite
            currentX += minXDistance + random.nextFloat() * (maxXDistance - minXDistance);
        }

        return validGroundSprites;
    }

}
