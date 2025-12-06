package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;
import io.example.main.Board;

public class Creeper extends Enemy {
    public Creeper(int tier, Texture texture, Board board, Texture garbageTexture) {
        // HP Tebal (250), Attack Lambat (12 detik)
        super("Creeper", 250, 12.0f, tier, texture, board, garbageTexture);
    }

    @Override
    public void performAttack() {
        // CREEPER: Ledakan besar / 3 Baris (addHeavyGarbage)
        board.addHeavyGarbage(garbageTexture);
    }
}
