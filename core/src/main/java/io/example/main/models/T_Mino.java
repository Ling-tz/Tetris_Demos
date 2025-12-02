package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;

public class T_Mino extends Tetromino {

    public T_Mino(Texture texture, int size) {
        super(texture, size);
        this.centerX = 5;
        this.centerY = 19;
    }

    @Override
    protected void initShape() {
        // Bentuk T
        //    [1]
        // [0][P][2]
        blocks.add(new Block(4, 19, size, texture));
        blocks.add(new Block(5, 20, size, texture));
        blocks.add(new Block(6, 19, size, texture));
        blocks.add(new Block(5, 19, size, texture)); // Pivot
    }
}
