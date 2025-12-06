package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;
import io.example.main.Board;

public class Zombie extends Enemy {
    public Zombie(int tier, Texture texture, Board board, Texture garbageTexture) {
        super("Zombie", 150, 10.0f, tier, texture, board, garbageTexture);
    }

    @Override
    public void performAttack() {
        // ZOMBIE: Dorong dari bawah (addGarbageRow)
        board.addGarbageRow(garbageTexture);
    }
}
