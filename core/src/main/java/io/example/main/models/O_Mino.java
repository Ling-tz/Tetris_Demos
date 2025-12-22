package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;

public class O_Mino extends Tetromino {

    public O_Mino(final Texture texture, final int size) {
        super(texture, size);
    }

    @Override
    protected void initShape() {
        blocks.add(new Block(centerX, centerY,  size, texture));
        blocks.add(new Block(centerX + 1, centerY, size, texture));
        blocks.add(new Block(centerX,centerY - 1, size, texture));
        blocks.add(new Block(centerX + 1, centerY - 1, size, texture));
    }

    @Override
    public void rotate() {
        // Do nothing
    }
}
