package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;

public class S_Mino extends Tetromino {

    public S_Mino(Texture texture, int size) {
        super(texture, size);
        this.centerX = 5;
        this.centerY = 19;
    }

    @Override
    protected void initShape() {
        // Bentuk S:
        //    [2][3]
        // [0][1]

        blocks.add(new Block(4, 19, size, texture)); // Kiri Bawah
        blocks.add(new Block(5, 19, size, texture)); // Tengah Bawah (Pivot)
        blocks.add(new Block(5, 20, size, texture)); // Tengah Atas
        blocks.add(new Block(6, 20, size, texture)); // Kanan Atas
    }
}
