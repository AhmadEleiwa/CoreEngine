package scenes;

import java.util.ArrayList;
import java.util.List;

import org.joml.Quaternionf;
import org.joml.Vector3f;

import engine.GameEngine;
import engine.Script;
import graphics.Renderer;
import utils.Camera;
import utils.Transform;

public class Node {
    protected String name;
    protected Node parent;
    protected List<Node> children;
    protected Transform localTransform;
    protected Transform globalTransform;

    protected Script script;
    // protected GameEngine gameEngine ;

    public Node(String name) {
        this.name = name;
        this.children = new ArrayList<>();
        this.localTransform = new Transform();
        globalTransform = new Transform();
        this.localTransform.addObserver(this::updateGlobalTransform);
        
    }

   
    public void register(Script script) {
        this.script = script;
        this.script.setTransform(localTransform);
        this.script.setNode(this);
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
        if(child instanceof Camera){
            GameEngine.getInstance().setMainCamera((Camera)child);
        }
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
        return  localTransform;
    }

    public Transform getLocalTransform() {
        return localTransform;
    }

    public Transform getGlobalTransform() {
        return globalTransform;
    }

    private void updateGlobalTransform() {
        if (parent != null) {
            // Combine parent global transform with local transform
            this.globalTransform = combineTransforms(parent.getGlobalTransform(), this.localTransform);
        } else {
            this.globalTransform = new Transform(this.localTransform);
        }
        // Update children recursively
        for (Node child : children) {
            child.updateGlobalTransform();
        }
    }

    private Transform combineTransforms(Transform parentTransform, Transform localTransform) {
        Transform combined = new Transform();
        combined.setPosition( parentTransform.getPosition().add(localTransform.getPosition(), new Vector3f()));
        combined.setRotation( parentTransform.getRotation().mul(localTransform.getRotation(), new Quaternionf()));
        combined.setScale(parentTransform.getScale().mul(localTransform.getScale(), new Vector3f()));
        return combined;
    }


}
