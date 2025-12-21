package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;

public class I_Mino extends Tetromino {

    public I_Mino(Texture texture, int size) {
        super(texture, size);
    }

    @Override
    protected void initShape() {
        blocks.add(new Block(centerX - 2, centerY, size, texture));
        blocks.add(new Block(centerX - 1, centerY, size, texture));
        blocks.add(new Block(centerX,     centerY, size, texture)); // Pivot di sini
        blocks.add(new Block(centerX + 1, centerY, size, texture));
    }
}
