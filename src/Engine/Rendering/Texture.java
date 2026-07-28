package Engine.Rendering;

import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private int width, height;

    private int textureId;
    private final String texturePath;

    public Texture(int width, int height, ByteBuffer byteBuffer) {
        this.texturePath = "";
        generateTexture(width, height, byteBuffer);
    }

    public Texture(String texturePath) {
        this.texturePath = texturePath;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer c = stack.mallocInt(1);
            stbi_set_flip_vertically_on_load(true);
            ByteBuffer byteBuffer = stbi_load(texturePath, w, h, c, 4);
            if (byteBuffer == null) {
                throw new RuntimeException("Failed to load STB image from [" + texturePath + "]: " + stbi_failure_reason());
            }
            int width = w.get();
            int height = h.get();
            generateTexture(width, height, byteBuffer);
            stbi_image_free(byteBuffer);
        }
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    public void generateTexture(int width, int height, ByteBuffer byteBuffer) {
        textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, byteBuffer);
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    public int getTextureId() {
        return textureId;
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void cleanup() {
        glDeleteTextures(textureId);
    }

}
