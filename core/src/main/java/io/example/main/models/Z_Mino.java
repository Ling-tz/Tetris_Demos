package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;

public class Z_Mino extends Tetromino {

    public Z_Mino(Texture texture, int size) {
        super(texture, size);
        this.centerX = 5;
        this.centerY = 19;
    }

    @Override
    protected void initShape() {
        // Bentuk Z:
        // [2][3]
        //    [1][0]

        blocks.add(new Block(6, 19, size, texture)); // Kanan Bawah
        blocks.add(new Block(5, 19, size, texture)); // Tengah Bawah (Pivot)
        blocks.add(new Block(5, 20, size, texture)); // Tengah Atas
        blocks.add(new Block(4, 20, size, texture)); // Kiri Atas
    }
}
