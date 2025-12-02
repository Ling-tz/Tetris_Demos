package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Block {
    private int x; // Posisi Grid X (0-9)
    private int y; // Posisi Grid Y (0-19)
    private Texture textureImage;
    private int size; // Ukuran pixel (misal 32px)

    public Block(int x, int y, int size, Texture texture) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.textureImage = texture;
    }

    public void render(SpriteBatch batch) {
        // Menggambar berdasarkan koordinat grid dikali ukuran pixel
        batch.draw(textureImage, x * size, y * size, size, size);
    }

    // Getter & Setter (Encapsulation)
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
}
