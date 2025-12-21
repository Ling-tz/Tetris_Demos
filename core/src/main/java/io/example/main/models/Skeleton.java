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
        int placed = 0;
        int attempts = 0;
        while (placed < 3 && attempts < 30) {
            int r = (int)(Math.random() * 10);
            int c = (int)(Math.random() * 10);
            if (board.isCellEmpty(c, r)) {
                board.setBlockAt(c, r, new Block(c, r, 30, garbageTexture));
                placed++;
            }
            attempts++;
        }
    }
}
