package engine;

import scenes.CollisionShape2D;
import scenes.Node;
import scenes.RigidBody2D;
import scenes.StaticBody2D;
import signals.Event;
import signals.TriggerEvent;
import utils.CollisionResult;
import utils.Transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.joml.Vector3f;

public class PhysicsManager {
    private List<CollisionShape2D> collisionShapes;
    private HashMap<String, CollisionShape2D> triggers;

    public PhysicsManager() {
        collisionShapes = new ArrayList<>();
        triggers = new HashMap<>();
    }

    public void addCollision(CollisionShape2D collisionShape2D) {
        this.collisionShapes.add(collisionShape2D);
    }

    public void handlePhysics(HashMap<String, Event> eventMap) {
        processCollisions();
        if (triggers.size() == 0) {
            if (eventMap.get("OnTrigger") != null)
                eventMap.remove("OnTrigger");
        } else {
            eventMap.put("OnTrigger", new TriggerEvent(triggers));
        }
    }

    private void processCollisions() {
        triggers.clear();
        for (int i = 0; i < collisionShapes.size(); i++) {
            CollisionShape2D shapeA = collisionShapes.get(i);
            Node parentA = shapeA.getParent();
            shapeA.setCollisionFromBottom(false);
            shapeA.setCollisionFromTop(false);
            shapeA.setCollisionFromBottom(false);
            shapeA.setCollisionFromBottom(false);

            for (int j = i + 1; j < collisionShapes.size(); j++) {
                CollisionShape2D shapeB = collisionShapes.get(j);
                Node parentB = shapeB.getParent();
                if (parentA instanceof StaticBody2D && parentB instanceof StaticBody2D) {
                    continue;
                }
                CollisionResult res = shapeA.checkCollisionDirectionV2(shapeB);
                if (parentA.getName() == "Palyer" && res.normal.x < 0)
                    System.out.println(res.normal.x);
                switch ((int) res.normal.x) {
                    case -1:
                        shapeA.setCollisionFromLeft(true);
                        break;
                    case 1:
                        shapeA.setCollisionFromRight(true);
                        break;
                    default:
                        shapeA.setCollisionFromLeft(false);
                        shapeA.setCollisionFromRight(false);
                        break;
                }

                shapeA.setCollisionFromBottom(false);
                if (res.colide) {
                    triggers.put(shapeA.getName(), shapeB);
                    if (parentA instanceof StaticBody2D) {
                        if (!((StaticBody2D) parentA).onTrigger) {
                            parentB.getLocalTransform().move(Transformation.mulScaler(res.normal, res.depth));
                            ((RigidBody2D) parentB).setOnGround(true);
                        } else {
                            continue;

                        }

                    } else if (parentB instanceof StaticBody2D) {
                        if (!((StaticBody2D) parentB).onTrigger) {
                            parentA.getLocalTransform()
                                    .move(Transformation.mulScaler(Transformation.invert(res.normal), res.depth));
                            ((RigidBody2D) parentA).setOnGround(true);
                        } else {
                            continue;
                        }
                    } else {
                        ((RigidBody2D) parentA).setOnGround(true);

                        parentA.getLocalTransform()
                                .move(Transformation.mulScaler(Transformation.invert(res.normal), res.depth / 2f));
                        parentB.getLocalTransform().move(Transformation.mulScaler(res.normal, res.depth / 2f));
                    }
                    resolveCollison((RigidBody2D) parentA, (RigidBody2D) parentB, res.normal, res.depth);
                }

            }
        }
    }

    private void resolveCollison(RigidBody2D bodyA, RigidBody2D bodyB, Vector3f normal, float depth) {
        Vector3f relativeVelocity = Transformation.subtractVector3f(bodyB.getVelocity(), bodyA.getVelocity());
        if (Transformation.dot(normal, relativeVelocity) > 0f) {
            return;
        }
        float e = Math.min(bodyA.getRestitution(), bodyB.getRestitution());
        float j = -(1f + e) * Transformation.dot(normal, relativeVelocity);
        j /= bodyA.getInvMass() + bodyB.getInvMass();
        bodyA.getVelocity().sub(Transformation.mulScaler(normal, j * bodyA.getInvMass()));
        bodyB.getVelocity().add(Transformation.mulScaler(normal, j * bodyB.getInvMass()));

    }

    public int getSize() {
        return collisionShapes.size();
    }
}
