package scripts;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import engine.GameEngine;
import engine.Script;
import input.Input;
import scenes.Node;
import utils.Collision2D;
import utils.CollisionDirection;
import java.util.List;

public class PlayerScript extends Script {
    float speed = 5f;
    float dir = 1;
    final float gravity = -9.78f;
    final float jumpSpeed = 7f; // Adjust jump speed as needed

    // Track vertical velocity (the change in y position over time)
    float verticalVelocity = 0f;
    boolean isJumping = false;
    GameEngine engine = GameEngine.getInstance();
    // public Node other;
    public List<Collision2D> grounds; // List of ground collision objects

    @Override
    public void start() {
        System.out.println("Hello World");
        transform.moveY(3);
    }

    @Override
    public void update(double deltaTime) {
        // var x = (Collision2D) (other.getChildByName("Collision"));
        var thisXoll = (Collision2D) (node.getChildByName("Collision"));
        // var collide = thisXoll.checkCollisionDirection(x);

        boolean isOnGround = false;

        // Check collision with any ground in the list
        for (Collision2D ground : grounds) {
            var collideGround = thisXoll.checkCollisionDirection(ground);
            if (collideGround == CollisionDirection.TOP) {
                isOnGround = true;
                break; // No need to check further if on ground
            }
        }

        if (Input.isKeyDown(GLFW.GLFW_KEY_D)) {
            dir = 1;
            transform.setScaleX(1);
        } else if (Input.isKeyDown(GLFW.GLFW_KEY_A)) {
            dir = -1;
            transform.setScaleX(-1);
        } else {
            dir = 0;
        }

        // Jump logic: only jump if the player is on the ground
        if (Input.isKeyDown(GLFW.GLFW_KEY_W) && isOnGround) {
            verticalVelocity = jumpSpeed;
            isOnGround = false; // Mark as not on ground to initiate jump
        }

        // Apply gravity if not on ground
        if (!isOnGround) {
            verticalVelocity += gravity * deltaTime;
        } else {
            verticalVelocity = 0; // Stop vertical movement on ground
        }

        // Apply vertical velocity
        transform.moveY((float) (verticalVelocity * deltaTime));

        // Horizontal movement
        transform.moveX((float) (speed * deltaTime) * dir);
        if(transform.getPosition().y <= -5){
            transform.setPosition(new Vector3f(0,3,0));
        }
    }
}
