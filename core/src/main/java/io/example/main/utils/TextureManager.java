package io.example.main.utils;

import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;

public class TextureManager {
    // HashMap menggantikan array untuk penyimpanan aset
    private HashMap<String, Texture> textures;

    public TextureManager() {
        textures = new HashMap<>();
    }

    public void loadTexture(String key, String path) {
        textures.put(key, new Texture(path));
    }

    public Texture getTexture(String key) {
        return textures.get(key);
    }

    public void dispose() {
        for (Texture t : textures.values()) {
            t.dispose();
        }
    }
}
