package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;
import io.example.main.Board;

public class Skeleton extends Enemy {
    public Skeleton(int tier, Texture texture, Board board, Texture garbageTexture) {
        // HP lebih tipis (100), Attack lebih cepat (6 detik)
        super("Skeleton", 100, 6.0f, tier, texture, board, garbageTexture);
    }

    @Override
    public void performAttack() {
        // SKELETON: Tembak acak (addRandomGarbage)
        // Pastikan method ini dipanggil, BUKAN addGarbageRow!
        board.addRandomGarbage(garbageTexture);
    }
}
