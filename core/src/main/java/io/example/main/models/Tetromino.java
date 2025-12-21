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

    protected int centerX = 5;
    protected int centerY = 19;

    public Tetromino(Texture texture, int size) {
        this.texture = texture;
        this.size = size;
        this.blocks = new ArrayList<>();
        initShape(); // Abstract logic dipanggil di sini
    }

    protected int rotationState = 0; // 0, 1, 2, 3

    // Method untuk update state (dipanggil saat berhasil putar)
    public void addRotationState(int delta) {
        // Delta 1 = putar kanan. Modulo 4 agar loop 0->1->2->3->0
        rotationState = (rotationState + delta) % 4;
        if (rotationState < 0) rotationState += 4;
    }

    public int getRotationState() {
        return rotationState;
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
    // Tambahkan method ini di dalam class Tetromino.java
    public void setPosition(int targetX, int targetY) {
        // Hitung pergeseran (offset) dari pusat sekarang ke target
        int dx = targetX - centerX;
        int dy = targetY - centerY;

        // Geser pivot
        centerX = targetX;
        centerY = targetY;

        // Geser semua block
        for (Block b : blocks) {
            b.setX(b.getX() + dx);
            b.setY(b.getY() + dy);
        }
    }
    public void moveByOffset(int dx, int dy) {
        // 1. Geser Titik Pusat (Pivot)
        this.centerX += dx;
        this.centerY += dy;

        // 2. Geser semua block anak-anaknya
        for (Block b : blocks) {
            b.setX(b.getX() + dx);
            b.setY(b.getY() + dy);
        }
    }
}
