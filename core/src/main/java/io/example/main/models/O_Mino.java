package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;

public class O_Mino extends Tetromino {

    public O_Mino(Texture texture, int size) {
        super(texture, size);
        // O-Mino tidak punya 'center' sejati di grid integer, kita pilih kiri bawah sebagai anchor
        this.centerX = 5;
        this.centerY = 19;
    }

    @Override
    protected void initShape() {
        // Bentuk O (Kotak):
        // [2][3]
        // [0][1]

        blocks.add(new Block(5, 19, size, texture)); // Kiri Bawah
        blocks.add(new Block(6, 19, size, texture)); // Kanan Bawah
        blocks.add(new Block(5, 20, size, texture)); // Kiri Atas
        blocks.add(new Block(6, 20, size, texture)); // Kanan Atas
    }

    // Opsional: Override rotate agar tidak melakukan apa-apa (O-Mino tidak perlu putar)
    @Override
    public void rotate() {
        // Do nothing
    }
}
