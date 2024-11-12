package scripts;

import org.joml.Vector3f;

import engine.Script;

public class CameraScript extends Script {
    public boolean smoothMovement = true;
    // private float zOffset = 0;
    private Vector3f smoothedPosition(double deltaTime){
        Vector3f desiredPosition = new Vector3f(
            node.getParent().getLocalTransform().getPosition().x,
            transform.getPosition().y, // Maintain current Y position
            transform.getPosition().z // Maintain current Z position

            // node.getParent().getLocalTransform().getPosition().z + zOffset
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
        // zOffset=Math.abs(node.getParent().getLocalTransform().getPosition().z - transform.getPosition().z);
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
