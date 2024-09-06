package utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Quaternionf;

public class Transform {
    public Vector3f position;
    public Quaternionf rotation;
    public Vector3f scale;

    public Transform() {
        position = new Vector3f(0.0f,
                0.0f, 0.0f);
        rotation = new Quaternionf(1.0f, 0.0f, 0.0f, 0.0f);
        scale = new Vector3f(1.0f, 1.0f, 1.0f);
    }

    public Matrix4f getTransformationMatrix() {
        Matrix4f matrix = new Matrix4f();
        matrix.translate(position);
        matrix.rotate(rotation);
        matrix.scale(scale);
        return matrix;
    }

}
