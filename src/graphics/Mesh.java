package graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

import utils.Texture;

public class Mesh {
    protected int vaoId;
    protected int vboId;
    protected int eboId;
    protected int elementCount;
    protected int texVboId;
    protected Texture texture;
    protected boolean outline = false;
    protected Mesh(){

    }
    public Mesh(float[] vertices, int[] indices, boolean outline){
        this( vertices,  indices);
        this.outline = outline;
    }
    public Mesh(float[] vertices, int[] indices) {
        this.elementCount = indices.length;

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);

        glBufferData(GL_ARRAY_BUFFER,vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0); 


        int eboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);

        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW); 


        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public  Mesh(Texture texture, int width, int height){
        // int width = texture.getWidth();
        // int height = texture.getHeight();
        
        // Calculate aspect ratio, ensuring the result is a float
        float aspectRatio = (float) width / (float) height;
        
        // Define vertices with scaling on the X-axis based on the aspect ratio
        float[] vertices = new float[] {
            -0.5f * aspectRatio, -0.5f, 0.0f, // Bottom-left
             0.5f * aspectRatio, -0.5f, 0.0f, // Bottom-right
             0.5f * aspectRatio,  0.5f, 0.0f, // Top-right
            -0.5f * aspectRatio,  0.5f, 0.0f  // Top-left
        };
    
        // Define indices for two triangles forming the rectangle
        int[] indices = new int[] {
            0, 1, 2, // First triangle
            2, 3, 0  // Second triangle
        };
    
        // Define texture coordinates for the vertices
        float[] texCoords = {
            0.0f, 1.0f, // Bottom-left
            1.0f, 1.0f, // Bottom-right
            1.0f, 0.0f, // Top-right
            0.0f, 0.0f  // Top-left
        };
        
        // Initialize the mesh with the texture and indices
        this.texture = texture;
        this.elementCount = indices.length;

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);

        glBufferData(GL_ARRAY_BUFFER,vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0); 

        texVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, texVboId);
        
        glBufferData(GL_ARRAY_BUFFER, texCoords, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(1);

        int eboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);

        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW); 


        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

    }
    public Mesh(float[] vertices, float[] texCoords, int[] indices, Texture texture) {
        this.texture = texture;
        this.elementCount = indices.length;

        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);

        glBufferData(GL_ARRAY_BUFFER,vertices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0); 

        texVboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, texVboId);
        
        glBufferData(GL_ARRAY_BUFFER, texCoords, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(1);

        int eboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);

        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW); 


        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }
    public void render() {
        if(texture != null)
            texture.bind();
        glBindVertexArray(vaoId);
    if (outline) {
        // Draw an outlined rectangle using GL_LINE_LOOP
        for (int i = 0; i < elementCount; i += 4) {
            glDrawElements(GL_LINE_LOOP, 4, GL_UNSIGNED_INT, i * Integer.BYTES);
        }
    } else {
        // Draw filled triangles as usual
        glDrawElements(GL_TRIANGLES, elementCount, GL_UNSIGNED_INT, 0);
    }
        glBindVertexArray(0);
        if(texture != null)
            texture.unbind();
    }

    public void cleanup() {
        glDeleteVertexArrays(vaoId);
        glDeleteBuffers(vboId);
        glDeleteBuffers(eboId);
    }
    public Texture getTexture(){
        return this.texture;
    }
}