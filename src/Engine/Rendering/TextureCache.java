package Engine.Rendering;

import java.util.HashMap;

public class TextureCache {

    public static final String DEFAULT_TEXTURE = "rsc/textures/default_texture.png";

    private HashMap<String, Texture> textureMap;

    public TextureCache() {
        textureMap = new HashMap<>();
        textureMap.put(DEFAULT_TEXTURE, new Texture(DEFAULT_TEXTURE));
    }

    public Texture createTexture(String texturePath) {
        return textureMap.computeIfAbsent(texturePath, Texture::new);
    }

    public Texture getTexture(String texturePath) {
        Texture texture = null;
        if (texturePath != null) {
            texture = textureMap.get(texturePath);
        }
        if (texture == null) {
            texture = textureMap.get(DEFAULT_TEXTURE);
        }
        return texture;
    }

    public void cleanup() {
        textureMap.values().forEach(Texture::cleanup);
    }

}
