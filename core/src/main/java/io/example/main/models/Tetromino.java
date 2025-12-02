package io.example.main.models;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.example.main.Movable;

import java.util.ArrayList;
import java.util.List;

public abstract class Tetromino implements Movable {
    // List menggantikan Array[]
    protected ArrayList<Block> blocks;
    protected Texture texture;
    protected int size;

    // Titik pusat rotasi (pivot)
    protected int centerX;
    protected int centerY;

    public Tetromino(Texture texture, int size) {
        this.texture = texture;
        this.size = size;
        this.blocks = new ArrayList<>();
        initShape(); // Abstract logic dipanggil di sini
    }

    // Method abstract yang wajib diisi oleh anak kelas (Polymorphism)
    protected abstract void initShape();

    @Override
    public void moveDown() {
        for (Block b : blocks) {
            b.setY(b.getY() - 1);
        }
        centerY--;
    }

    @Override
    public void moveLeft() {
        for (Block b : blocks) {
            b.setX(b.getX() - 1);
        }
        centerX--;
    }

    @Override
    public void moveRight() {
        for (Block b : blocks) {
            b.setX(b.getX() + 1);
        }
        centerX++;
    }

    @Override
    public void rotate() {
        // Logika rotasi sederhana (putar 90 derajat searah jarum jam terhadap pivot)
        // Rumus rotasi: x' = y - pivotY + pivotX; y' = -(x - pivotX) + pivotY
        // Perlu pengecekan collision board di implementasi real
        for (Block b : blocks) {
            int relativeX = b.getX() - centerX;
            int relativeY = b.getY() - centerY;

            int newX = centerX + relativeY;
            int newY = centerY - relativeX;

            b.setX(newX);
            b.setY(newY);
        }
    }

    public void render(SpriteBatch batch) {
        for (Block b : blocks) {
            b.render(batch);
        }
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }
}
