package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;

public class S_Mino extends Tetromino {

    public S_Mino(final Texture texture, final int size) {
        super(texture, size);
    }

    @Override
    protected void initShape() {
        blocks.add(new Block(centerX - 1, centerY, size, texture));
        blocks.add(new Block(centerX, centerY, size, texture));
        blocks.add(new Block(centerX,centerY + 1, size, texture));
        blocks.add(new Block(centerX + 1, centerY + 1, size, texture));
    }
}
