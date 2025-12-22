package io.example.main.models;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.example.main.Movable;

import java.util.ArrayList;
import java.util.List;

public abstract class Tetromino implements Movable {
    protected ArrayList<Block> blocks;
    final  Texture texture;
    final int size;

    protected int centerX = 5;
    protected int centerY = 19;

    public Tetromino(final Texture texture, final int size) {
        this.texture = texture;
        this.size = size;
        this.blocks = new ArrayList<>();
        initShape();
    }

    protected int rotationState = 0;

    public void addRotationState(int delta) {
        rotationState = (rotationState + delta) % 4;
        if (rotationState < 0) rotationState += 4;
    }

    public int getRotationState() {
        return rotationState;
    }

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
    public void setPosition(int targetX, int targetY) {
        int dx = targetX - centerX;
        int dy = targetY - centerY;

        centerX = targetX;
        centerY = targetY;

        for (Block b : blocks) {
            b.setX(b.getX() + dx);
            b.setY(b.getY() + dy);
        }
    }
    public void moveByOffset(int dx, int dy) {
        this.centerX += dx;
        this.centerY += dy;

        for (Block b : blocks) {
            b.setX(b.getX() + dx);
            b.setY(b.getY() + dy);
        }
    }
}
