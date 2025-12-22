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

    public void loadGameAssets() {
        // Blok Tetris
        loadTexture("L1", "pink_block.png");
        loadTexture("L2", "blue_block.png");
        loadTexture("S",  "green_block.png");
        loadTexture("Z",  "red_block.png");
        loadTexture("O",  "yellow_block.png");
        loadTexture("I",  "cyan_block.png");
        loadTexture("T",  "purple_block.png");

        // RPG & Monster
        loadTexture("zombie", "monster/zombie.jpg");
        loadTexture("skeleton", "monster/skeleton.png");
        loadTexture("creeper", "monster/creeper.jpg");
        loadTexture("bedrock", "bedrock.png");

        // UI
        loadTexture("bg", "Button/bg_in_game.png");
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
