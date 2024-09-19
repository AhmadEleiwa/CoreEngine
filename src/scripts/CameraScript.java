package scripts;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import engine.Script;
import input.Input;
import scenes.Sprite2D;

public class CameraScript extends Script {
    public boolean smoothMovement = true;
    private Vector3f smoothedPosition(double deltaTime){
        Vector3f desiredPosition = new Vector3f(
            node.getParent().getLocalTransform().getPosition().x,
            transform.getPosition().y, // Maintain current Y position
            transform.getPosition().z // Maintain current Z position
        );

        // Interpolate the camera's position towards the desired position
        return new Vector3f(
            transform.getPosition().lerp(desiredPosition, (float)deltaTime*4  )
        );
       
    }
    @Override
    public void start() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'start'");
        node.setInheritsTransform(false);
        transform.setPositionZ(5);
    }

    @Override
    public void update(double deltaTime) {
        // TODO Auto-generated method stub
        if(smoothMovement){
            transform.setPosition(smoothedPosition(deltaTime));
        }else{
            transform.setPositionX(node.getParent().getLocalTransform().getPosition().x);
        }
    }

}
