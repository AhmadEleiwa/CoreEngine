package utils;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Quaternionf;

public class Transform {
    protected Vector3f position;
    protected Quaternionf rotation;
    protected Vector3f scale;

    private final List<Runnable> observers = new ArrayList<>();

    public Transform() {
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Quaternionf();
        this.scale = new Vector3f(1, 1, 1);
    }

    public Transform(Transform other) {
        this.position = new Vector3f(other.position);
        this.rotation = new Quaternionf(other.rotation);
        this.scale = new Vector3f(other.scale);
    }

    public void addObserver(Runnable observer) {
        observers.add(observer);
    }

    public void removeObserver(Runnable observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (Runnable observer : observers) {
            observer.run();
        }
    }

    public Matrix4f getTransformationMatrix() {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(position);
        matrix.rotate(rotation);
        matrix.scale(scale);
        return matrix;
    }

    public void setTransform(Transform transform) {
        this.position = new Vector3f(transform.position);
        this.rotation = new Quaternionf(transform.rotation);
        this.scale = new Vector3f(transform.scale);
    }

    @Override
    public boolean equals(Object obj) {
        Transform other = (Transform) obj;
        return position.equals(other.position) && scale.equals(other.scale) && rotation.equals(other.rotation);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
        notifyObservers();
    }

    public void setPositionX(float x) {
        this.position.x = x;
        notifyObservers();
    }

    public void setPositionY(float y) {
        this.position.y = y;
        notifyObservers();
    }

    public void setPositionZ(float z) {
        this.position.z = z;
        notifyObservers();
    }

    public void move(Vector3f vector3f) {
        this.position.add(vector3f);
        notifyObservers();
    }

    public void moveX(float x) {
        this.position.x += x;
        notifyObservers();
    }

    public void moveY(float y) {
        this.position.y += y;
        notifyObservers();
    }

    public void moveZ(float z) {
        this.position.z += z;
        notifyObservers();
    }

    // Rotation Handling

    public Quaternionf getRotation() {
        return rotation;
    }

    public void setRotation(Quaternionf rotation) {
        this.rotation = rotation;
        notifyObservers();
    }

    public void setRotationX(float angle) {
        this.rotation.rotateX(angle);
        notifyObservers();
    }

    public void setRotationY(float angle) {
        this.rotation.rotateY(angle);
        notifyObservers();
    }

    public void setRotationZ(float angle) {
        this.rotation.rotateZ(angle);
        notifyObservers();
    }

    public void rotate(Vector3f rotation, float angle) {
        this.rotation.rotateAxis(angle, rotation);
        notifyObservers();
    }

    public void rotateX(float angleX) {
        this.rotation.rotateX(angleX);
        notifyObservers();
    }

    public void rotateY(float angleY) {
        this.rotation.rotateY(angleY);
        notifyObservers();
    }

    public void rotateZ(float angleZ) {
        this.rotation.rotateZ(angleZ);
        notifyObservers();
    }
    // Scale Handling

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
        notifyObservers();
    }

    public void setScaleX(float x) {
        this.scale.x = x;
        notifyObservers();
    }

    public void setScaleY(float y) {
        this.scale.y = y;
        notifyObservers();
    }

    public void setScaleZ(float z) {
        this.scale.z = z;
        notifyObservers();
    }

    public void addScale(Vector3f vector3f) {
        this.scale.add(vector3f);
        notifyObservers();
    }

    public void addScaleX(float x) {
        this.scale.x += x;
        notifyObservers();
    }

    public void addScaleY(float y) {
        this.scale.y += y;
        notifyObservers();
    }

    public void addScaleZ(float z) {
        this.scale.z += z;
        notifyObservers();
    }

    public List<Runnable> getObservers() {
        return observers;
    }

}
