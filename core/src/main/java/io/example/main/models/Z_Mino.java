package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;

public class Z_Mino extends Tetromino {

    public Z_Mino(Texture texture, int size) {
        super(texture, size);
    }

    @Override
    protected void initShape() {
        blocks.add(new Block(centerX + 1, centerY, size, texture));
        blocks.add(new Block(centerX, centerY, size, texture));
        blocks.add(new Block(centerX,centerY + 1, size, texture));
        blocks.add(new Block(centerX - 1, centerY + 1, size, texture));
    }
}
