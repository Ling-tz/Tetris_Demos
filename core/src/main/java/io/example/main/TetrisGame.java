package io.example.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.example.main.models.*;

import java.util.Random;

public class TetrisGame extends ApplicationAdapter {
    SpriteBatch batch;
    TextureManager textureManager;
    Board board;
    Tetromino currentPiece; // Polymorphism variable

    float timeSeconds = 0f;
    float period = 0.5f;

    @Override
    public void create() {
        batch = new SpriteBatch();
        textureManager = new TextureManager();

        // L1 (Biasanya Orange) -> Kita pakai PINK
        textureManager.loadTexture("L1", "pink_block.png");

        // L2 (Biasanya Blue) -> Sesuai aset blue_block.png
        textureManager.loadTexture("L2", "blue_block.png");

        // S (Green)
        textureManager.loadTexture("S",  "green_block.png");

        // Z (Red)
        textureManager.loadTexture("Z",  "red_block.png");

        // O (Yellow - Kotak)
        textureManager.loadTexture("O",  "yellow_block.png");

        // I (Cyan - Garis Panjang)
        textureManager.loadTexture("I",  "cyan_block.png");

        // T (Purple)
        textureManager.loadTexture("T",  "purple_block.png");

        board = new Board();
        spawnNewPiece();
    }

    private void spawnNewPiece() {
        Random rand = new Random();
        int type = rand.nextInt(7); // Ada 7 bentuk (0 sampai 6)
        int size = 32; // Ukuran block

        // Factory Pattern sederhana menggunakan Switch Case
        switch (type) {
            case 0:
                currentPiece = new L1_Mino(textureManager.getTexture("L1"), size);
                break;
            case 1:
                currentPiece = new L2_Mino(textureManager.getTexture("L2"), size);
                break;
            case 2:
                currentPiece = new S_Mino(textureManager.getTexture("S"), size);
                break;
            case 3:
                currentPiece = new Z_Mino(textureManager.getTexture("Z"), size);
                break;
            case 4:
                currentPiece = new O_Mino(textureManager.getTexture("O"), size);
                break;
            case 5:
                currentPiece = new I_Mino(textureManager.getTexture("I"), size);
                break;
            case 6:
                currentPiece = new T_Mino(textureManager.getTexture("T"), size);
                break;
        }
    }

    @Override
    public void render() {
        // Logic Update dan Input sama seperti kode sebelumnya...
        handleInput();

        timeSeconds += Gdx.graphics.getDeltaTime();
        if (timeSeconds > period) {
            timeSeconds -= period;
            currentPiece.moveDown();

            if (board.checkCollision(currentPiece)) {
                // Kembalikan ke atas karena nabrak
                for(Block b : currentPiece.getBlocks()) b.setY(b.getY() + 1);

                // Tempel ke board
                board.placePiece(currentPiece);
                spawnNewPiece(); // Spawn piece baru (acak lagi)

                if (board.checkCollision(currentPiece)) {
                    System.out.println("GAME OVER");
                    // Reset board atau exit game
                    Gdx.app.exit();
                }
            }
        }

        // Render
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1); // Background agak abu gelap
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        board.render(batch);
        if (currentPiece != null) {
            currentPiece.render(batch);
        }
        batch.end();
    }

    // Method handleInput() sama persis seperti jawaban sebelumnya
    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            currentPiece.moveLeft();
            if (board.checkCollision(currentPiece)) currentPiece.moveRight();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            currentPiece.moveRight();
            if (board.checkCollision(currentPiece)) currentPiece.moveLeft();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            currentPiece.rotate();
            if (board.checkCollision(currentPiece)) {
                // Simplifikasi undo rotate (putar 3x)
                currentPiece.rotate();
                currentPiece.rotate();
                currentPiece.rotate();
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            currentPiece.moveDown();
            if (board.checkCollision(currentPiece)) {
                for(Block b : currentPiece.getBlocks()) b.setY(b.getY() + 1);
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        textureManager.dispose();
    }
}
