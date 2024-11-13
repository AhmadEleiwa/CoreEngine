package utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation {

    public Matrix4f getProjectionMatrix(float fov, float width, float height, float near, float far) {
        Matrix4f projectionMatrix = new Matrix4f();
        float aspectRatio = width / height;
        return projectionMatrix.perspective((float) Math.toRadians(fov), aspectRatio, near, far);
    }

    public static Vector3f mulScaler(Vector3f vec, float val) {
        return new Vector3f(vec.x * val, vec.y * val, vec.z * val);
    }

    public static Vector3f invert(Vector3f vec) {
        return new Vector3f(-vec.x, -vec.y, -vec.z);
    }

    public static float dot(Vector3f vec1, Vector3f vec2) {
        return vec1.x * vec2.x +  vec1.y * vec2.y+ vec1.z * vec2.z;
    }
    public static Vector3f subtractVector3f(Vector3f v1, Vector3f v2) {
        return new Vector3f(v1.x - v2.x, v1.y - v2.y, 0);
    }
}