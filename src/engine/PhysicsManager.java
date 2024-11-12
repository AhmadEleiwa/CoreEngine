package engine;

import scenes.CollisionShape2D;
import scenes.Node;
import scenes.RigidBody2D;
import scenes.StaticBody2D;
import utils.CollisionDirection;

import java.util.ArrayList;
import java.util.List;

public class PhysicsManager {
    private List<CollisionShape2D> collisionShapes;

    public PhysicsManager() {
        collisionShapes = new ArrayList<>();
    }

    public void addCollision(CollisionShape2D collisionShape2D) {
        this.collisionShapes.add(collisionShape2D);
    }

    public void handlePhysics() {
        processCollisions();
    }

    private void processCollisions() {
        for (int i = 0; i < collisionShapes.size(); i++) {
            CollisionShape2D shapeA = collisionShapes.get(i);
            Node parentA = shapeA.getParent();
            shapeA.setCollisionFromLeft(false);
            shapeA.setCollisionFromRight(false);
            shapeA.setCollisionFromTop(false);
            shapeA.setCollisionFromBottom(false);
            for (int j = i + 1; j < collisionShapes.size(); j++) {
                CollisionShape2D shapeB = collisionShapes.get(j);
                Node parentB = shapeB.getParent();

                // Only check if at least one of the objects is a RigidBody2D (not both Static)
                if (parentA instanceof RigidBody2D && parentB instanceof StaticBody2D) {
                    // StaticBody2D staticBodyB = (StaticBody2D) parentB;

                    CollisionDirection collisionDirection = shapeA.checkCollisionDirection(shapeB);

                    // if (collisionDirection != CollisionDirection.NONE) {
                    handleStaticToRigidCollision(shapeA, collisionDirection);
                    // }
                } else if (parentB instanceof RigidBody2D && parentA instanceof StaticBody2D) {
                    // StaticBody2D staticBodyA = (StaticBody2D) parentA;

                    CollisionDirection collisionDirection = shapeA.checkCollisionDirection(shapeB);

                    // if (collisionDirection != CollisionDirection.NONE) {

                    handleStaticToRigidCollision(shapeB, collisionDirection);
                    // }
                }
                // Handle RigidBody2D to RigidBody2D collisions
                else if (parentA instanceof RigidBody2D && parentB instanceof RigidBody2D) {
                    RigidBody2D rigidBodyA = (RigidBody2D) parentA;
                    RigidBody2D rigidBodyB = (RigidBody2D) parentB;

                    CollisionDirection collisionDirection = shapeA.checkCollisionDirection(shapeB);
                    if (collisionDirection != null) {
                        handleRigidToRigidCollision(rigidBodyA, rigidBodyB, collisionDirection);
                    }
                }
            }
        }
    }

    // Handle collision between StaticBody2D and RigidBody2D
    private void handleStaticToRigidCollision(CollisionShape2D rigidBody, CollisionDirection direction) {
        rigidBody.setCollisionFromLeft(rigidBody.isCollisionFromLeft() | false);
        rigidBody.setCollisionFromRight(rigidBody.isCollisionFromRight() | false);
        rigidBody.setCollisionFromTop(rigidBody.isCollisionFromTop() | false);
        rigidBody.setCollisionFromBottom(rigidBody.isCollisionFromBottom() | false);

        if (direction == CollisionDirection.TOP) {

            rigidBody.setCollisionFromTop(true);

        } else if (direction == CollisionDirection.BOTTOM) {
            rigidBody.setCollisionFromBottom(true);

        } else if (direction == CollisionDirection.LEFT) {
            // Stop movement to the left if colliding with an object on the left
            // rigidBody.setCollisionFromRight(true);
            rigidBody.setCollisionFromLeft(true);

        } else if (direction == CollisionDirection.RIGHT) {
            // Stop movement to the right if colliding with an object on the right
            rigidBody.setCollisionFromRight(true);
            // rigidBody.setCollisionFromLeft(true);

        } else {

        }

    }

    // Handle collision between two RigidBody2D instances (Elastic Collision)
    private void handleRigidToRigidCollision(RigidBody2D rigidBodyA, RigidBody2D rigidBodyB,
            CollisionDirection direction) {
        // Calculate velocities after the collision (simplified)
        float massA = rigidBodyA.getMass();
        float massB = rigidBodyB.getMass();

        float newVelocityA = (rigidBodyA.getVelocity() * (massA - massB) + 2 * massB * rigidBodyB.getVelocity())
                / (massA + massB);
        float newVelocityB = (rigidBodyB.getVelocity() * (massB - massA) + 2 * massA * rigidBodyA.getVelocity())
                / (massA + massB);

        if (direction == CollisionDirection.LEFT || direction == CollisionDirection.RIGHT) {
            rigidBodyA.setHorizontalVelocity(newVelocityA);
            rigidBodyB.setHorizontalVelocity(newVelocityB);
        } else if (direction == CollisionDirection.TOP || direction == CollisionDirection.BOTTOM) {
            rigidBodyA.setVerticalVelocity(newVelocityA);
            rigidBodyB.setVerticalVelocity(newVelocityB);
        }
    }

}
