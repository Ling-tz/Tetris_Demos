package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;
import io.example.main.Board;

import java.util.ArrayList;

public class Zombie extends Enemy {
    public Zombie(int tier, Texture texture, Board board, Texture garbageTexture) {
        super("Zombie", 150, 10.0f, tier, texture, board, garbageTexture);
    }

    @Override
    public void performAttack() {
        ArrayList<Block> garbageRow = new ArrayList<>();
        int holeIndex = (int)(Math.random() * 10);

        for (int col = 0; col < 10; col++) {
            if (col == holeIndex) garbageRow.add(null);
            else garbageRow.add(new Block(col, 0, 30, garbageTexture));
        }

        board.pushRowsUp(garbageRow);
    }
}

