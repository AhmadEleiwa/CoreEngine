package scenes;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import engine.GameEngine;
import engine.Script;
import graphics.Renderer;
import utils.Transform;

public class Node {
    protected String name;
    protected Node parent;
    protected List<Node> children;
    protected Transform transform;
    protected Script script;
    // protected GameEngine gameEngine ;

    public Node(String name) {
        this.name = name;
        this.children = new ArrayList<>();
        this.transform = new Transform();
    }

    public void register(Script script) {
        this.script = script;
        this.script.setTransform(transform);
    }

    public String getName() {
        return this.name;
    }

    public Node getChildByName(String name) {
        for (Node child : children) {
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }

    public void addChild(Node child) {
        child.setParent(this);
        this.children.add(child);
    }

    public void removeChild(Node child) {
        child.setParent(null);
        this.children.remove(child);
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void start() {
        if (this.script != null) {
            this.script.start();
        }
        // Override in subclasses to update node logic
        for (Node child : this.children) {
            child.start();
        }
    }
    public void update(double deltaTime) {
        // if (this.script != null) {
        //     this.script.update(deltaTime);
        // }
        // Override in subclasses to update node logic
        if (this.script != null) {
            this.script.update(deltaTime);
        }
        for (Node child : this.children) {
            child.update(deltaTime);
        }
    }

    public void render(Renderer renderer) {
        // Override in subclasses to render the node
        for (Node child : this.children) {
            child.render(renderer);
        }
    }

    protected void updateUniforms() {
        // Update uniforms
    }

    public List<Node> getChildren() {
        return children;
    }

    public Transform geTransform() {
        return this.transform;
    }

    public Vector3f getPosition() {
        return this.transform.position;
    }

}
