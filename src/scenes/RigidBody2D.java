package scenes;

import org.joml.Vector3f;

import engine.GameEngine;
import utils.Transformation;

public class RigidBody2D extends Node {
    private final float gravity = -9.8f;
    private CollisionShape2D collision;

    private Vector3f force;
    private Vector3f rotationVelocity;
    private Vector3f velocity;

    private float density;
    private float mass;
    private float restitution = 0.5f;
    private float area;
    private float invMass;

    private boolean onGround = false;

    public RigidBody2D(String name, float mass) {
        super(name);
        this.mass = mass;
        if (mass == 0)
            this.invMass = 0;
        else
            this.invMass = 1f / this.mass;
    }

    @Override
    public void addChild(Node child) {
        super.addChild(child);
        if (child instanceof CollisionShape2D) {
            collision = (CollisionShape2D) child;
            int nth = GameEngine.getInstance().getCollisionsSize();
            child.name  += nth; 
            GameEngine.getInstance().addCollision((CollisionShape2D) child);
        }
        this.force = new Vector3f(0, 0, 0);
        this.velocity = new Vector3f(0);
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        updatePosition(deltaTime);
        // applyGravity(deltaTime);
    }

    public float getInvMass() {
        return invMass;
    }

    public void setInvMass(float invMass) {
        this.invMass = invMass;
    }

    public float getRestitution() {
        return restitution;
    }

    public void setRestitution(float restitution) {
        this.restitution = restitution;
    }

    public void setVelocity(Vector3f vec) {
        velocity = vec;
    }

    public Vector3f getForce() {
        return force;
    }

    public void setForce(Vector3f force) {
        this.force = force;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public void setOnGround(boolean val) {
        this.onGround = val;
    }

    public boolean getOnGround() {
        return this.onGround;
    }

    public void updatePosition(double deltaTime) {
        this.velocity.y += gravity * (float) deltaTime;
        localTransform.move(Transformation.mulScaler(velocity, (float) deltaTime));
    }

    public Vector3f getVelocity() {
        return this.velocity;
    }

}
