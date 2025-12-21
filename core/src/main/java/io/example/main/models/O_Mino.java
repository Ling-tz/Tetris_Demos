package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;

public class O_Mino extends Tetromino {

    public O_Mino(Texture texture, int size) {
        super(texture, size);
    }

    @Override
    protected void initShape() {
        // Bentuk O (Kotak):
        // [2][3]
        // [0][1]

        blocks.add(new Block(centerX,     centerY,     size, texture));
        blocks.add(new Block(centerX + 1, centerY,     size, texture));
        blocks.add(new Block(centerX,     centerY - 1, size, texture));
        blocks.add(new Block(centerX + 1, centerY - 1, size, texture));
    }

    // Opsional: Override rotate agar tidak melakukan apa-apa (O-Mino tidak perlu putar)
    @Override
    public void rotate() {
        // Do nothing
    }
}
