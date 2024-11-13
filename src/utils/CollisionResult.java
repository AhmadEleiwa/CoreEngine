package utils;

import org.joml.Vector3f;

public class CollisionResult {
    public Vector3f normal;
    public float depth = 0;
    public boolean colide;
    public CollisionResult(Vector3f normal, float depth, boolean colide) {
        this.normal = normal;
        this.depth = depth;
        this.colide = colide;
    }
    
}
