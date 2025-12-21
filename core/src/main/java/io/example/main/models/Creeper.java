package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;
import io.example.main.Board;

import java.util.ArrayList;

public class Creeper extends Enemy {
    public Creeper(int tier, Texture texture, Board board, Texture garbageTexture) {
        // HP Tebal (250), Attack Lambat (12 detik)
        super("Creeper", 250, 12.0f, tier, texture, board, garbageTexture);
    }

    @Override
    public void performAttack() {
        for (int i = 0; i < 3; i++) {
            addOneGarbageRow();
        }
    }

    private void addOneGarbageRow() {
        ArrayList<Block> row = new ArrayList<>();
        int hole = (int)(Math.random() * 10);
        for(int c=0; c<10; c++) {
            row.add(c == hole ? null : new Block(c, 0, 30, garbageTexture));
        }
        board.pushRowsUp(row);
    }
}
