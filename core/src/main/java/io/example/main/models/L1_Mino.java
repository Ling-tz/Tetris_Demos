package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;

public class L1_Mino extends Tetromino {

    public L1_Mino(Texture texture, int size) {
        super(texture, size);
        this.centerX = 5; // Pivot
        this.centerY = 19;
    }

    @Override
    protected void initShape() {
        // Bentuk L:
        //       [3]
        // [0][1][2]
        // Index 1 adalah Pivot (5,19)

        blocks.add(new Block(4, 19, size, texture)); // Kiri
        blocks.add(new Block(5, 19, size, texture)); // Tengah (Pivot)
        blocks.add(new Block(6, 19, size, texture)); // Kanan
        blocks.add(new Block(6, 20, size, texture)); // Atas-Kanan
    }
}
