package scripts;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import engine.GameEngine;
import engine.Script;
import input.Input;

public class PlayerScript extends Script {
    float speed = 5f;
    float dir = 1;
    final float gravity = -9.78f;
    final float jumpSpeed =7f; // Adjust jump speed as needed

    // Track vertical velocity (the change in y position over time)
    float verticalVelocity = 0f;
    boolean isJumping = false;
    GameEngine engine = GameEngine.getInstance();

    @Override
    public void start() {
        System.out.println("Hello World");
        transform.moveY(3);
    }

    @Override
    public void update(double deltaTime) {

        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
            dir = 1;
            transform.setScaleX(1);
        } else if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
            dir = -1;
            transform.setScaleX(-1);
        } else {
            dir = 0;
        }
        boolean isOnGround = transform.getPosition().y == 0;

        // Jumping Logic
        if (Input.isKeyDown(GLFW.GLFW_KEY_W) && isOnGround) {
            verticalVelocity = jumpSpeed; // Set the vertical velocity to jump speed
        }
        
        // Gravity application (gravity is always applied when not on the ground)
        if (!isOnGround) {
            verticalVelocity += gravity * deltaTime;  // Apply gravity
        }
        
        // Apply vertical velocity to move the player
        transform.moveY((float)(verticalVelocity * deltaTime));
        
        // Check if the player has hit the ground
        if (transform.getPosition().y <= 0) {
            transform.getPosition().y = 0;  // Ensure player doesn't fall below the ground
            isOnGround = true;  // Player is back on the ground
            verticalVelocity = 0;  // Reset vertical velocity once the player is grounded
        } else {
            isOnGround = false;  // Player is in the air
        }

        // if(Input.isKeyDown(GLFW.GLFW_KEY_UP)){
        //     transform.moveZ((float) (-speed * deltaTime));
        // }
        // if(Input.isKeyDown(GLFW.GLFW_KEY_DOWN)){
        //     transform.moveZ((float) (speed * deltaTime));

        // }
        transform.moveX((float) (speed * deltaTime) * dir);

    }

}
