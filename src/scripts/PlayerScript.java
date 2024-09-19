package scripts;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import engine.GameEngine;
import engine.Script;
import input.Input;

public class PlayerScript extends Script {
    float speed = 5f;
    float dir = 1;
    GameEngine engine = GameEngine.getInstance();

    @Override
    public void start() {
        System.out.println("Hello World");

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

        transform.moveX((float) (speed * deltaTime) * dir);

    }

}
