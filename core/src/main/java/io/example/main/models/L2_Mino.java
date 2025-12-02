package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;

public class L2_Mino extends Tetromino {

    public L2_Mino(Texture texture, int size) {
        super(texture, size);
        this.centerX = 5;
        this.centerY = 19;
    }

    @Override
    protected void initShape() {
        // Bentuk J:
        // [3]
        // [0][1][2]
        // Index 1 adalah Pivot

        blocks.add(new Block(4, 19, size, texture)); // Kiri
        blocks.add(new Block(5, 19, size, texture)); // Tengah (Pivot)
        blocks.add(new Block(6, 19, size, texture)); // Kanan
        blocks.add(new Block(4, 20, size, texture)); // Atas-Kiri
    }
}
