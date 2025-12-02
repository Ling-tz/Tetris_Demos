package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;

public class I_Mino extends Tetromino {

    public I_Mino(Texture texture, int size) {
        super(texture, size);
        this.centerX = 5;
        this.centerY = 19;
    }

    @Override
    protected void initShape() {
        // Garis Lurus Horizontal
        blocks.add(new Block(3, 19, size, texture));
        blocks.add(new Block(4, 19, size, texture));
        blocks.add(new Block(5, 19, size, texture)); // Pivot
        blocks.add(new Block(6, 19, size, texture));
    }
}
