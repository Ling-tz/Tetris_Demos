package io.example.main;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.example.main.models.Block;
import io.example.main.models.Tetromino;

import java.util.ArrayList;

public class Board {
    private final int ROWS = 20;
    private final int COLS = 10;
    private final int BLOCK_SIZE = 32;

    // Grid: List of Rows, dimana setiap Row adalah List of Blocks
    // Jika null, berarti kosong.
    private ArrayList<ArrayList<Block>> grid;

    public Board() {
        grid = new ArrayList<>();
        // Inisialisasi grid kosong
        for (int row = 0; row < ROWS; row++) {
            ArrayList<Block> rowList = new ArrayList<>();
            for (int col = 0; col < COLS; col++) {
                rowList.add(null); // Isi null sebagai penanda kosong
            }
            grid.add(rowList);
        }
    }

    public boolean checkCollision(Tetromino piece) {
        for (Block b : piece.getBlocks()) {
            // Cek batas dinding dan lantai
            if (b.getX() < 0 || b.getX() >= COLS || b.getY() < 0) {
                return true;
            }
            // Cek tumpukan block yang sudah mati (grid tidak null)
            // Pastikan Y valid sebelum cek grid untuk menghindari IndexOutOfBounds
            if (b.getY() < ROWS) {
                if (grid.get(b.getY()).get(b.getX()) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public void placePiece(Tetromino piece) {
        for (Block b : piece.getBlocks()) {
            if (b.getY() < ROWS && b.getY() >= 0) {
                grid.get(b.getY()).set(b.getX(), b);
            }
        }
        clearLines();
    }

    private void clearLines() {
        // Gunakan iterator atau loop terbalik agar aman saat menghapus row
        for (int i = grid.size() - 1; i >= 0; i--) {
            ArrayList<Block> row = grid.get(i);
            boolean full = true;
            for (Block b : row) {
                if (b == null) {
                    full = false;
                    break;
                }
            }

            if (full) {
                // Hapus baris ini
                grid.remove(i);
                // Tambah baris baru kosong di paling atas
                ArrayList<Block> newRow = new ArrayList<>();
                for(int k=0; k<COLS; k++) newRow.add(null);
                grid.add(newRow); // Tambah ke akhir (paling atas logika visual)

                // Karena kita remove elemen saat looping, sesuaikan index loop
                // Namun, karena kita langsung add lagi, size list tetap 20.
                // Tapi kita perlu update koordinat Y block yang tersisa di atasnya (turun ke bawah)
                updateBlockCoordinates(i);

                // Cek baris yang sama lagi karena baris atasnya turun
                i++;
            }
        }
    }

    private void updateBlockCoordinates(int startRowIndex) {
        for (int r = startRowIndex; r < ROWS; r++) {
            for (Block b : grid.get(r)) {
                if (b != null) {
                    b.setY(b.getY() - 1); // Turunkan posisi visual Y
                }
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (ArrayList<Block> row : grid) {
            for (Block b : row) {
                if (b != null) {
                    b.render(batch);
                }
            }
        }
    }
}
