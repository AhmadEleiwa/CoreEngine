package scenes;

import org.joml.Vector3f;
import org.joml.Vector4f;

import graphics.Mesh;
import utils.CollisionDirection;

public class CollisionShape2D extends MeshNode {
    private float width;
    private float height;
    private Vector4f collisionsDetected;

    public boolean isCollisionFromRight() {
        return collisionsDetected.y == 1 ? true : false;

    }

    public void setCollisionFromRight(boolean collisionFromRight) {

        collisionsDetected.y = collisionFromRight ? 1 : 0;
    }

    public boolean isCollisionFromLeft() {
        return collisionsDetected.x == 1 ? true : false;

    }

    public void setCollisionFromLeft(boolean collisionFromLeft) {
        collisionsDetected.x = collisionFromLeft ? 1 : 0;
        
    }
    public boolean isCollisionFromTop() {
        return collisionsDetected.z == 1;
    }
    
    public void setCollisionFromTop(boolean collisionFromTop) {
        collisionsDetected.z = collisionFromTop ? 1 : 0;
    }
    
    public boolean isCollisionFromBottom() {
        return collisionsDetected.w == 1;
    }
    
    public void setCollisionFromBottom(boolean collisionFromBottom) {
        collisionsDetected.w = collisionFromBottom ? 1 : 0;
    }
        
    public CollisionShape2D(float width, float height) {
        super("Collision", new Mesh(new float[] {
                -0.5f, -0.5f, 0.0f, // Bottom left
                0.5f, -0.5f, 0.0f, // Bottom right
                0.5f, 0.5f, 0.0f, // Top right
                -0.5f, 0.5f, 0.0f // Top left
        },
                new int[] {
                        0, 1, 2, 3
                // 0, 2, 3
                }, true));
        this.collisionsDetected = new Vector4f(0, 0, 0, 0);
        this.width = width;
        this.height = height;
        this.localTransform.scale = new Vector3f(this.width, this.height, 1);

    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public CollisionDirection checkCollisionDirection(CollisionShape2D other) {
        Vector3f posA = this.getGlobalTransform().position;
        Vector3f posB = other.getGlobalTransform().position;

        float deltaX = posA.x - posB.x;
        float deltaY = posA.y - posB.y;

        float combinedHalfWidth = (this.width + other.width) / 2;
        float combinedHalfHeight = (this.height + other.height) / 2;

        // Check for intersection
        if (Math.abs(deltaX) < combinedHalfWidth && Math.abs(deltaY) < combinedHalfHeight) {
            float overlapX = combinedHalfWidth - Math.abs(deltaX);
            float overlapY = combinedHalfHeight - Math.abs(deltaY);

            // Determine the collision direction based on the smallest overlap
            if (overlapX < overlapY) {
                return deltaX > 0 ? CollisionDirection.RIGHT : CollisionDirection.LEFT;
            } else {
                return deltaY > 0 ? CollisionDirection.TOP : CollisionDirection.BOTTOM;
            }
        }

        return CollisionDirection.NONE; // No collision
    }

    public boolean checkCollision(CollisionShape2D other) {
        Vector3f posA = this.getGlobalTransform().position;
        Vector3f posB = other.getGlobalTransform().position;

        return (Math.abs(posA.x - posB.x) < (this.width + other.width) / 2) &&
                (Math.abs(posA.y - posB.y) < (this.height + other.height) / 2);
    }
}
