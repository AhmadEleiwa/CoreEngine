package engine;

import scenes.Node;
import utils.Transform;

public abstract class Script {
    protected Transform transform;
    protected Node node;

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public abstract void start();

    public abstract void update(double deltaTime);

    public void onCollision() {

    };
}