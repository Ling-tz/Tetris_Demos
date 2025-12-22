package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Block {
    private int x;
    private int y;
    private Texture textureImage;
    private int size;

    public Block(int x, int y, int size, Texture texture) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.textureImage = texture;
    }

    public void render(SpriteBatch batch) {
        batch.draw(textureImage, x * size, y * size, size, size);
    }

    public void setTexture(Texture tex) { this.textureImage = tex; }

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public Texture getTexture() { return textureImage; }
}
