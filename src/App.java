import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joml.Vector3f;
import engine.GameEngine;
import scenes.CollisionShape2D;
import scenes.Node;
import scenes.RigidBody2D;
import scenes.Sprite2D;
import scenes.StaticBody2D;
import scripts.CameraScript;
import scripts.PlayerScript;
import scripts.RectScript;
import utils.Camera;

public class App {

    public static void main(String[] args) {
        GameEngine gameEngine = GameEngine.getInstance();

        Node node = new Node("root");

     

        Sprite2D player = new Sprite2D("sprite2D", gameEngine.createTexture("assets/images/Player.png"));
        RigidBody2D rigidBody2D = new  RigidBody2D("Palyer", 1);
        rigidBody2D.addChild(player);
        CollisionShape2D collPlayer = new CollisionShape2D(0.7f, 0.8f);
        collPlayer.getTransform().moveY(0.1f);
        rigidBody2D.addChild(collPlayer);
        rigidBody2D.register(new PlayerScript());
        // PlayerScript sc = new PlayerScript();

        StaticBody2D ground2 = new StaticBody2D("ground2");
        ground2.addChild(new CollisionShape2D(1, 1));
        ground2.getLocalTransform().moveY(-1);
        ground2.getLocalTransform().moveX(0f);


        node.addChild(ground2);

        StaticBody2D ground = new StaticBody2D("ground");
        ground.addChild(new CollisionShape2D(20, 1));
        ground.getLocalTransform().moveY(-2);

        node.addChild(ground);

  


        // player.register(sc);
        node.addChild(rigidBody2D);
        Camera camera = new Camera("MainCamera");
        CameraScript cameraScript = new CameraScript();
        camera.register(cameraScript);

        rigidBody2D.addChild(camera);

        // var grounds = App.getValidGroundSprites();
        // sc.grounds = grounds;

        // List<CollisionShape2D> groundsColl = new ArrayList<CollisionShape2D>();
        // for (Sprite2D s : grounds) {
        //     node.addChild(s);
        //     groundsColl.add((CollisionShape2D) s.getChildByName("Collision"));
        // }
        // sc.grounds = groundsColl;

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
            CollisionShape2D groundColl = new CollisionShape2D(1.8f, 0.2f);
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
