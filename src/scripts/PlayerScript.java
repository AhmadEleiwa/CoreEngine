package scripts;

import org.lwjgl.glfw.GLFW;
import engine.Script;
import input.Input;
import scenes.RigidBody2D;

public class PlayerScript extends Script {
    private RigidBody2D player;
    private final float speed = 3f; // Horizontal movement speed
    private final float jumpSpeed = 7f; // Vertical jump speed

    @Override
    public void start() {
        System.out.println("Hello World");
        // transform.moveY(3); // Initial position
        transform.moveX(-5);
        player = (RigidBody2D) node; // Access the player RigidBody2D component
    }

    @Override
    public void update(double deltaTime) {
        // Horizontal movement
        float horizontalVelocity = 0;
        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
            horizontalVelocity = speed; // Move right
            player.getVelocity().x = horizontalVelocity;

        } else if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
            horizontalVelocity = -speed; // Move left
            player.getVelocity().x = horizontalVelocity;
        } else {
            player.getVelocity().x = 0;
        }
        // Jumping Logic
        if (Input.isKeyDown(GLFW.GLFW_KEY_W) && player.getOnGround()) {
            player.getVelocity().y = jumpSpeed;
            player.setOnGround(false);
        }

    }
}
