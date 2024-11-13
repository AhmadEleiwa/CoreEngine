package scenes;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import graphics.Mesh;
import utils.CollisionDirection;
import utils.CollisionResult;
import utils.Transformation;

public class CollisionShape2D extends MeshNode {
    private float width;
    private float height;
    private Vector4f collisionsDetected;
    private List<Vector3f> transformedVertices;

    public List<Vector3f> getTransformedVertices() {
        return transformedVertices;
    }

    public boolean isCollisionFromRight() {
        return collisionsDetected.y == 1 ? true : false;

    }

    public void calculateTransformedVertices() {
        Matrix4f transformMatrix = this.getGlobalTransform().getTransformationMatrix();
        List<Vector3f> transformedVertices = new ArrayList<>();

        // Define local vertices based on width and height (centered at origin)

        Vector3f[] localVertices = {
                new Vector3f(-width / 2, -height / 2, 0),
                new Vector3f(width / 2, -height / 2, 0),
                new Vector3f(width / 2, height / 2, 0),
                new Vector3f(-width / 2, height / 2, 0)
        };

        // Transform each vertex
        for (Vector3f vertex : localVertices) {
            Vector3f transformedVertex = new Vector3f();
            transformMatrix.transformPosition(vertex, transformedVertex);
            transformedVertices.add(transformedVertex);
        }

        // return transformedVertices;
        // System.out.println(transformedVertices.get(0).x);
        this.transformedVertices = transformedVertices;
    }

    public void setCollisionFromRight(boolean collisionFromRight) {

        collisionsDetected.y = collisionFromRight ? 1 : 0;
    }

    public boolean isCollisionFromLeft() {
        return collisionsDetected.x == 1 ? true : false;

    }

    public void setCollisionFromLeft(boolean collisionFromLeft) {
        collisionsDetected.x = collisionFromLeft ? 1 : 0;

    }

    public boolean isCollisionFromTop() {
        return collisionsDetected.z == 1;
    }

    public void setCollisionFromTop(boolean collisionFromTop) {
        collisionsDetected.z = collisionFromTop ? 1 : 0;
    }

    public boolean isCollisionFromBottom() {
        return collisionsDetected.w == 1;
    }

    public void setCollisionFromBottom(boolean collisionFromBottom) {
        collisionsDetected.w = collisionFromBottom ? 1 : 0;
    }

    public CollisionShape2D(float width, float height) {
        super("Collision", new Mesh(new float[] {
                -0.5f, -0.5f, 0.0f, // Bottom left
                0.5f, -0.5f, 0.0f, // Bottom right
                0.5f, 0.5f, 0.0f, // Top right
                -0.5f, 0.5f, 0.0f // Top left
        },
                new int[] {
                        0, 1, 2, 3
                // 0, 2, 3
                }, true));
        this.collisionsDetected = new Vector4f(0, 0, 0, 0);
        this.width = width;
        this.height = height;
        this.localTransform.setScale( new Vector3f(this.width, this.height, 1));
        calculateTransformedVertices();

    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
        calculateTransformedVertices();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public CollisionDirection checkCollisionDirection(CollisionShape2D other) {

        Vector3f posA = this.getGlobalTransform().position;
        Vector3f posB = other.getGlobalTransform().position;

        float deltaX = posA.x - posB.x;
        float deltaY = posA.y - posB.y;

        float combinedHalfWidth = (this.width + other.width) / 2;
        float combinedHalfHeight = (this.height + other.height) / 2;

        // Check for intersection
        if (Math.abs(deltaX) < combinedHalfWidth && Math.abs(deltaY) < combinedHalfHeight) {
            float overlapX = combinedHalfWidth - Math.abs(deltaX);
            float overlapY = combinedHalfHeight - Math.abs(deltaY);

            // Determine the collision direction based on the smallest overlap
            if (overlapX < overlapY) {
                return deltaX > 0 ? CollisionDirection.RIGHT : CollisionDirection.LEFT;
            } else {
                return deltaY > 0 ? CollisionDirection.TOP : CollisionDirection.BOTTOM;
            }
        }

        return CollisionDirection.NONE; // No collision
    }

    public CollisionResult checkCollisionDirectionV2(CollisionShape2D other) {
        float depth = Float.MAX_VALUE;
        Vector3f normal = new Vector3f(0,0,0);
        for (int i = 0; i < this.transformedVertices.size(); i++) {
            Vector3f va = this.transformedVertices.get(i);
            Vector3f vb = this.transformedVertices.get((i + 1) % this.transformedVertices.size());

            Vector3f edge = subtractVector3f(va, vb);
            Vector3f axis = new Vector3f(-edge.y, edge.x, 0).normalize();
            // Vector3f axis = new Vector3f(-edge.y, edge.x, 0);

            float[] sizeA = projectionVertices(this.transformedVertices, axis);
            float[] sizeB = projectionVertices(other.getTransformedVertices(), axis);

            if (sizeA[0] >= sizeB[1] || sizeB[0] >= sizeA[1]) {
                return new CollisionResult(normal,depth,false);
            }
            //
            float axisDepth = Math.min(sizeB[1] - sizeA[0], sizeA[1] - sizeB[0]);
            if (axisDepth < depth) {
                depth = axisDepth;
                normal = axis;
            }

        }

        for (int i = 0; i < other.getTransformedVertices().size(); i++) {
            Vector3f va = other.getTransformedVertices().get(i);
            Vector3f vb = other.getTransformedVertices().get((i + 1) % other.getTransformedVertices().size());

            Vector3f edge = subtractVector3f(va, vb);
            Vector3f axis = new Vector3f(-edge.y, edge.x, 0).normalize();
            // Vector3f axis = new Vector3f(-edge.y, edge.x, 0);

            float[] sizeA = projectionVertices(this.transformedVertices, axis);
            float[] sizeB = projectionVertices(other.getTransformedVertices(), axis);

            if (sizeA[0] >= sizeB[1] || sizeB[0] >= sizeA[1]) {
                return new CollisionResult(normal,depth,false);

            }
            //
            float axisDepth = Math.min(sizeB[1] - sizeA[0], sizeA[1] - sizeB[0]);

            if (axisDepth < depth) {
                depth = axisDepth;
                normal = axis;
            }

        }
        depth /= normal.length();
        normal.normalize();


        Vector3f centerA = findArithmeticMean(this.transformedVertices);
        Vector3f centerB = findArithmeticMean(other.getTransformedVertices());

        Vector3f direction = subtractVector3f(centerB, centerA);
        if(Transformation.dot(direction, normal) < 0){
            normal = Transformation.invert(normal);
        }

        return new CollisionResult(normal,depth,true);

    }
    private Vector3f findArithmeticMean(List<Vector3f> transformedVertices){
        float sumx = 0;
        float sumy = 0;
        for(int i=0; i<transformedVertices.size(); i++){
            Vector3f vec= transformedVertices.get(i);
            sumx += vec.x;
            sumy += vec.y;
        }
        return new Vector3f(sumx / (float)transformedVertices.size(), sumy/(float)transformedVertices.size(), 0);
    }
    private float[] projectionVertices(List<Vector3f> transformedVertices, Vector3f axis) {
        float min = Float.MAX_VALUE;
        float max = Float.MIN_VALUE;

        for (Vector3f vec : transformedVertices) {
            float proj = vec.dot(axis);
            if (proj < min)
                min = proj;
            if (proj > max)
                max = proj;
        }
        return new float[] { min, max };
    }

    private Vector3f subtractVector3f(Vector3f v1, Vector3f v2) {
        return new Vector3f(v1.x - v2.x, v1.y - v2.y, 0);
    }

    public boolean checkCollision(CollisionShape2D other) {
        Vector3f posA = this.getGlobalTransform().position;
        Vector3f posB = other.getGlobalTransform().position;

        return (Math.abs(posA.x - posB.x) < (this.width + other.width) / 2) &&
                (Math.abs(posA.y - posB.y) < (this.height + other.height) / 2);
    }
}
