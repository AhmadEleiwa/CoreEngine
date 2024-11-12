package scenes;

import engine.GameEngine;

public class StaticBody2D extends Node {

    public StaticBody2D(String name) {
        super(name);

    }
    @Override
    public void addChild(Node child) {
        // TODO Auto-generated method stub
        super.addChild(child);
        if(child instanceof CollisionShape2D){
            GameEngine.getInstance().addCollision((CollisionShape2D)child);
        }
    }

}