package scenes;

import engine.GameEngine;

public class StaticBody2D extends RigidBody2D {
    public boolean onTrigger;
    public StaticBody2D(String name) {
        super(name,0);
        this.onTrigger = false;
        this.setRestitution(1);
    }
    @Override
    public void addChild(Node child) {
        // TODO Auto-generated method stub
        super.addChild(child);
        if(child instanceof CollisionShape2D){
            GameEngine.getInstance().addCollision((CollisionShape2D)child);
        }
    }
    @Override
    public void updatePosition(double deltaTime) {
        
    }

}