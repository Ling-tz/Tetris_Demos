package io.example.main;

import com.badlogic.gdx.graphics.Texture;
import io.example.main.models.Block;
import io.example.main.models.Tetromino;

import java.util.ArrayList;

public class Board {
    private final int ROWS = 20;
    private final int COLS = 10;
    private final int BLOCK_SIZE = 30;

    private ArrayList<ArrayList<Block>> grid;

    public Board() {
        grid = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            ArrayList<Block> row = new ArrayList<>();
            for (int j = 0; j < COLS; j++) {
                row.add(null);
            }
            grid.add(row);
        }
    }

    public void placePiece(Tetromino piece) {
        for (Block b : piece.getBlocks()) {
            if (b.getY() < ROWS && b.getY() >= 0) {
                grid.get(b.getY()).set(b.getX(), b);
            }
        }
    }

    // KEMBALI KE VERSI SIMPLE (Tanpa parameter Game)
    public int clearLines() {
        int linesCleared = 0;

        for (int i = 0; i < grid.size(); i++) {
            ArrayList<Block> row = grid.get(i);
            boolean full = true;

            for (Block b : row) {
                if (b == null) {
                    full = false;
                    break;
                }
            }

            if (full) {
                grid.remove(i);
                ArrayList<Block> newRow = new ArrayList<>();
                for(int k=0; k<COLS; k++) newRow.add(null);
                grid.add(newRow); // Tambah baris kosong di atas

                updateBlockCoordinates(i);
                linesCleared++;
                i--;
            }
        }
        return linesCleared;
    }

    private void updateBlockCoordinates(int startRow) {
        for (int i = startRow; i < ROWS; i++) {
            for (Block b : grid.get(i)) {
                if (b != null) b.setY(i);
            }
        }
    }

    public boolean checkCollision(Tetromino piece) {
        for (Block b : piece.getBlocks()) {
            if (b.getX() < 0 || b.getX() >= COLS || b.getY() < 0) return true;
            if (b.getY() < ROWS && grid.get(b.getY()).get(b.getX()) != null) return true;
        }
        return false;
    }

    public void render(com.badlogic.gdx.graphics.g2d.SpriteBatch batch) {
        for (ArrayList<Block> row : grid) {
            for (Block b : row) {
                if (b != null) b.render(batch);
            }
        }
    }

    public ArrayList<ArrayList<Block>> getGrid() { return grid; }

    // --- FITUR MONSTER ATTACK (TETAP ADA) ---
    public boolean addGarbageRow(Texture garbageTexture) {
        // Cek Game Over
        for (Block b : grid.get(ROWS - 1)) {
            if (b != null) return true;
        }

        // Geser Naik
        for (int row = ROWS - 1; row > 0; row--) {
            grid.get(row).clear();
            for (Block b : grid.get(row - 1)) {
                if (b != null) { b.setY(row); grid.get(row).add(b); }
                else { grid.get(row).add(null); }
            }
        }
        // Isi Bawah
        grid.get(0).clear();
        int holeIndex = (int)(Math.random() * COLS);
        for (int col = 0; col < COLS; col++) {
            if (col == holeIndex) grid.get(0).add(null);
            else grid.get(0).add(new Block(col, 0, BLOCK_SIZE, garbageTexture));
        }
        return false;
    }

    public void addRandomGarbage(Texture garbageTexture) {
        int count = 0; int attempts = 0;
        while (count < 3 && attempts < 20) {
            int r = (int)(Math.random() * (ROWS - 5));
            int c = (int)(Math.random() * COLS);
            if (grid.get(r).get(c) == null) {
                grid.get(r).set(c, new Block(c, r, BLOCK_SIZE, garbageTexture));
                count++;
            }
            attempts++;
        }
    }

    public boolean addHeavyGarbage(Texture garbageTexture) {
        boolean dead = false;
        dead = addGarbageRow(garbageTexture);
        if (!dead) dead = addGarbageRow(garbageTexture);
        if (!dead) dead = addGarbageRow(garbageTexture);
        return dead;
    }
}
