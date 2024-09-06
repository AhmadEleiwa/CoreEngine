package utils;

import org.joml.Matrix4f;

public class Transformation {

    public Matrix4f getProjectionMatrix(float fov, float width, float height, float near, float far) {
        Matrix4f projectionMatrix = new Matrix4f();
        float aspectRatio = width / height;
        return projectionMatrix.perspective((float) Math.toRadians(fov), aspectRatio, near, far);
    }
}