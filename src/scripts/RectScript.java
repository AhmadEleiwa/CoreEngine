package scripts;

import engine.Script;
import scenes.CollisionShape2D;
import signals.OnTrigger;

public class RectScript extends Script {

    @Override
    public void start() {
        // TODO Auto-generated method stub

        // Sprite2D sprite2d = new Sprite2D("item",
        // GameEngine.getInstance().createTexture("assets/images/rock.png"));
        // sprite2d.getLocalTransform().moveX(4);
        // sprite2d.getLocalTransform().moveZ(0);
        // // node.addChild(sprite2d);
        // GameEngine.getInstance().getRoot().addChild(sprite2d);
        registerSignal(new OnTrigger() {

            @Override
            public void onTrigger(CollisionShape2D other) {
                // TODO Auto-generated method stub
                // throw new UnsupportedOperationException("Unimplemented method 'onTrigger'");
                System.out.println(other.getName());
            }

        });
    }

    @Override
    public void update(double deltaTime) {
        // TODO Auto-generated method stub

    }

}
