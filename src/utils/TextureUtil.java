package utils;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

public class TextureUtil {

    public static int[] loadTexture(String filePath) throws IOException {
        // Load image data
        ByteBuffer image;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            // Read the image file
            image = STBImage.stbi_load(filePath, width, height, channels, STBImage.STBI_rgb_alpha);
            if (image == null) {
                throw new IOException("Failed to load image: " + STBImage.stbi_failure_reason());
            }

            int w = width.get();
            int h = height.get();
            int format = GL_RGBA;

            // Generate texture
            int textureID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureID);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w, h, 0, format, GL_UNSIGNED_BYTE, image);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            

            glBindTexture(GL_TEXTURE_2D, 0);

            // Free image memory
            STBImage.stbi_image_free(image);
  
            return new int[]{textureID,w,h};
        }
    }
}
