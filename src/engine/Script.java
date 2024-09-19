package engine;

import scenes.Node;
import utils.Transform;

public abstract class Script {
    protected Transform transform;
    protected Node node;

    public void init(Transform transform, Node node) {
        this.transform = transform;
        this.node = node;

    }

    public abstract void start();

    public abstract void update(double deltaTime);

    public void onCollision() {

    };

}