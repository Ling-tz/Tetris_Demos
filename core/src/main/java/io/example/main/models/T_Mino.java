package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;

public class T_Mino extends Tetromino {

    public T_Mino(Texture texture, int size) {
        super(texture, size);
    }

    @Override
    protected void initShape() {
        blocks.add(new Block(centerX - 1, centerY,     size, texture)); // Kiri [0]
        blocks.add(new Block(centerX,     centerY + 1, size, texture)); // Atas [1]
        blocks.add(new Block(centerX + 1, centerY,     size, texture)); // Kanan [2]
        blocks.add(new Block(centerX,     centerY,     size, texture));// Pivot
    }
}
