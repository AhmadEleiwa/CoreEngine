package scripts;

import org.lwjgl.glfw.GLFW;
import engine.Script;
import input.Input;
import scenes.RigidBody2D;

public class PlayerScript extends Script {
    private RigidBody2D player;
    private final float speed = 5f;         // Horizontal movement speed
    private final float jumpSpeed = 7f;     // Vertical jump speed

    @Override
    public void start() {
        System.out.println("Hello World");
        transform.moveY(3); // Initial position
        player = (RigidBody2D) node; // Access the player RigidBody2D component
    }

    @Override
    public void update(double deltaTime) {
        // Horizontal movement
        float horizontalVelocity = 0;
        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
            horizontalVelocity = speed;  // Move right
        player.setHorizontalVelocity(horizontalVelocity);

            transform.setScaleX(1);      // Face right
        } else if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
            horizontalVelocity = -speed; // Move left
            transform.setScaleX(-1);     // Face left
        player.setHorizontalVelocity(horizontalVelocity);

        }else{
        player.setHorizontalVelocity(horizontalVelocity);

        }
        // System.out.println(horizontalVelocity);
        // Set the horizontal velocity on the player

        // Jumping Logic

        if (Input.isKeyDown(GLFW.GLFW_KEY_W) && player.isGrounded()) {
            // player.jump(jumpSpeed);; // Set jump velocity
            player.jump(jumpSpeed); // Apply the jump force
            // player.setIsGrounded(false);

        }

        // Update position and apply gravity
        // player.update(deltaTime);
    }
}
