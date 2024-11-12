package scenes;

import engine.GameEngine;

public class RigidBody2D extends Node {
    private float mass;
    private float horizontalVelocity = 0;
    private float verticalVelocity = 0;
    private final float gravity = -9.8f;
    private boolean isGrounded = false;
    private CollisionShape2D collision;
    private final float groundTolerance = 0.01f; // Small value to prevent player from being slightly inside the ground
    private final float ceilingTolerance = 0.01f; // Small value for ceiling collision tolerance
    private final float leftTolerance = 0.001f; // Small value for left side collision tolerance
    private final float rightTolerance = 0.001f; // Small value for right side collision tolerance

    public RigidBody2D(String name, float mass) {
        super(name);
        this.mass = mass;
    }

    @Override
    public void addChild(Node child) {
        super.addChild(child);
        if (child instanceof CollisionShape2D) {
            collision = (CollisionShape2D) child;
            GameEngine.getInstance().addCollision((CollisionShape2D) child);
        }
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        updatePosition(deltaTime);
        // applyGravity(deltaTime);
    }

    public boolean isGrounded() {
        return this.isGrounded;
    }

    public void setIsGrounded(boolean isGrounded) {
        this.isGrounded = isGrounded;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getHorizontalVelocity() {

        return horizontalVelocity;
    }

    public void setHorizontalVelocity(float horizontalVelocity) {
        this.horizontalVelocity = horizontalVelocity;
    }

    public float getVerticalVelocity() {
        return verticalVelocity;
    }

    public void setVerticalVelocity(float verticalVelocity) {
        this.verticalVelocity = verticalVelocity;
    }

    public void jump(float jumpForce) {
        if (isGrounded()) { // Only allow jumping if grounded
            verticalVelocity = jumpForce; // Apply upward force
            setIsGrounded(false); // Mark as airborne
        }
    }

    public void updatePosition(double deltaTime) {
        // System.out.println();
        float newX = localTransform.getPosition().x + horizontalVelocity * (float) deltaTime;

        if (this.collision.isCollisionFromLeft() && horizontalVelocity > 0
                || this.collision.isCollisionFromRight() && horizontalVelocity < 0) {
            // localTransform.setPositionX(0);
            if(this.collision.isCollisionFromLeft() && horizontalVelocity > 0)
                newX = localTransform.getPosition().x - leftTolerance;
            else
                newX = localTransform.getPosition().x + rightTolerance;
            
            localTransform.setPositionX(newX);

        } else {
            // newX = localTransform.getPosition().x - groundTolerance;
            localTransform.setPositionX(newX);
        }


        
        if (!this.collision.isCollisionFromTop() && Math.abs(horizontalVelocity) > 0) {
            isGrounded = false;
        }
        // Apply gravity when not grounded
        if (!isGrounded()) {
            verticalVelocity += gravity * deltaTime;
        }
        float newY = localTransform.getPosition().y + verticalVelocity * (float) deltaTime;

        // Collision detection from top (landing)
        if (collision.isCollisionFromTop()) {
            // If the player is colliding from above, stop the fall (landing)
            setIsGrounded(true); // Player is grounded now
            verticalVelocity = 0; // Stop downward velocity

            // Adjust position to prevent player from being inside the ground
            if (verticalVelocity <= 0 && localTransform.getPosition().y < newY + ceilingTolerance) {
                newY = localTransform.getPosition().y + groundTolerance; // Push player up above the ground
            }
        }
        // Collision detection from bottom (collision with floor or platform)
        if (collision.isCollisionFromBottom()) {
            // If the player is colliding from below (floor/platform), stop their downward
            // motion
            verticalVelocity = 0; // Stop downward velocity (prevents sinking into the ground)

            // Adjust position to prevent player from overlapping with the surface below
            if (verticalVelocity >= 0 && localTransform.getPosition().y > newY - groundTolerance) {
                newY = localTransform.getPosition().y - groundTolerance; // Push player up slightly above the floor
            }
        }
        // localTransform.setPositionY(newY);
        localTransform.setPositionY(newY);

    }

    public float getVelocity() {
        return (float) Math.sqrt(horizontalVelocity * horizontalVelocity + verticalVelocity * verticalVelocity);
    }

}
