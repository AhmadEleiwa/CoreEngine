package utils;

import org.lwjgl.opengl.GL11;
import java.io.IOException;

public class Texture {
    private int id;
    private int width;
    private int height;

    public Texture(String filepath) {
        try {
            id = TextureUtil.loadTexture(filepath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    public void unbind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
    }

    public void cleanup() {
        GL11.glDeleteTextures(id);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }
}
