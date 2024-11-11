package scripts;


import engine.GameEngine;
import engine.Script;
import scenes.Sprite2D;


public class RectScript extends Script {

    @Override
    public void start() {
        // TODO Auto-generated method stub
        
        Sprite2D sprite2d = new Sprite2D("item", GameEngine.getInstance().createTexture("assets/images/rock.png"));
        sprite2d.getLocalTransform().moveX(4);
        sprite2d.getLocalTransform().moveZ(0);
        // node.addChild(sprite2d);
        GameEngine.getInstance().getRoot().addChild(sprite2d);

    }

    @Override
    public void update(double deltaTime) {
        // TODO Auto-generated method stub

        transform.rotateX((float)(5*deltaTime));
    }
    
}
