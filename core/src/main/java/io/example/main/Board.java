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

    public void pushRowsUp(ArrayList<Block> newBottomRow) {
        grid.remove(ROWS - 1);
        grid.add(0, newBottomRow);

        for (int y = 0; y < ROWS; y++) {
            for (Block b : grid.get(y)) {
                if (b != null) b.setY(y);
            }
        }
    }

    public void setBlockAt(int x, int y, Block block) {
        if (y >= 0 && y < ROWS && x >= 0 && x < COLS) {
            grid.get(y).set(x, block);
        }
    }

    public boolean isCellEmpty(int x, int y) {
        return grid.get(y).get(x) == null;
    }
}
