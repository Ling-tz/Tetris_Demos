package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;

public class L2_Mino extends Tetromino {

    public L2_Mino(Texture texture, int size) {
        super(texture, size);
    }

    @Override
    protected void initShape() {
        blocks.add(new Block(centerX - 1, centerY,     size, texture)); // Kiri
        blocks.add(new Block(centerX,     centerY,     size, texture)); // Tengah (Pivot)
        blocks.add(new Block(centerX + 1, centerY,     size, texture)); // Kanan
        blocks.add(new Block(centerX - 1, centerY + 1, size, texture));
    }
}
