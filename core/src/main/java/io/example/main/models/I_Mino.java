package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;

public class I_Mino extends Tetromino {

    public I_Mino(final Texture texture, final int size) {
        super(texture, size);
    }

    @Override
    protected void initShape() {
        blocks.add(new Block(centerX - 2, centerY, size, texture));
        blocks.add(new Block(centerX - 1, centerY, size, texture));
        blocks.add(new Block(centerX, centerY, size, texture)); //
        blocks.add(new Block(centerX + 1, centerY, size, texture));
    }
}
