package io.example.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.example.main.models.*;
import io.example.main.utils.TextureManager;
import io.example.main.utils.WallKickMgr;
import io.example.main.utils.SoundManager; // Import SoundManager
import java.util.ArrayList;
import java.util.Random;

public class TetrisGame extends ApplicationAdapter {
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    BitmapFont font;

    // --- MANAGERS ---
    TextureManager textureManager;
    SoundManager soundManager;     // Manager Audio
    WallKickMgr kickMgr;           // Manager Wall Kicks

    Board board;

    Tetromino currentPiece;
    Tetromino nextPiece;
    Tetromino heldPiece;

    ArrayList<Block> ghostBlocks;

    // Status Game
    int score = 0;
    int lines = 0;
    boolean isGameOver = false;
    boolean canHold = true;

    // --- INPUT TIMERS (DAS) ---
    float leftTimer = 0;
    float rightTimer = 0;
    float downTimer = 0;

    final float DAS_DELAY = 0.2f;
    final float DAS_SPEED = 0.08f;
    final float SOFT_DROP_SPEED = 0.05f;

    float timeSeconds = 0f;
    float period = 0.5f;

    // --- LAYOUT ---
    final int BLOCK_SIZE = 30;
    final int BOARD_OFFSET_X = 240;
    final int BOARD_OFFSET_Y = 50;
    final int NEXT_PREVIEW_X = 14;
    final int NEXT_PREVIEW_Y = 16;
    final int HOLD_BOX_X = 50;
    final int HOLD_BOX_Y = 500;
    final int SCORE_X = 50;
    final int SCORE_Y = 300;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.getData().setScale(1.5f);

        // 1. Load Textures
        textureManager = new TextureManager();
        textureManager.loadTexture("L1", "pink_block.png");
        textureManager.loadTexture("L2", "blue_block.png");
        textureManager.loadTexture("S",  "green_block.png");
        textureManager.loadTexture("Z",  "red_block.png");
        textureManager.loadTexture("O",  "yellow_block.png");
        textureManager.loadTexture("I",  "cyan_block.png");
        textureManager.loadTexture("T",  "purple_block.png");

        // 2. Load Sounds
        soundManager = new SoundManager();
        // Pastikan nama file ini ada di folder assets!
        soundManager.loadMusic("audio/bgm.mp3");
        soundManager.loadSound("move", "audio/move.wav");
        soundManager.loadSound("rotate","audio/rotate.wav");
        soundManager.loadSound("drop", "audio/drop.wav");
        soundManager.loadSound("clear", "audio/clear.wav");
        soundManager.loadSound("hold", "audio/hold.wav");
        soundManager.loadSound("gameover", "audio/gameover.wav");

        // Mulai Musik
        soundManager.playMusic();

        // 3. Init Logic
        kickMgr = new WallKickMgr();
        ghostBlocks = new ArrayList<>();

        startNewGame();
    }

    private void startNewGame() {
        board = new Board();
        score = 0;
        lines = 0;
        isGameOver = false;
        heldPiece = null;
        canHold = true;

        leftTimer = 0;
        rightTimer = 0;
        downTimer = 0;

        // Restart Music jika sebelumnya Game Over
        soundManager.playMusic();

        nextPiece = generateRandomPiece();
        spawnNewPiece();
    }

    private Tetromino createPieceByType(int type) {
        switch (type) {
            case 0: return new L1_Mino(textureManager.getTexture("L1"), BLOCK_SIZE);
            case 1: return new L2_Mino(textureManager.getTexture("L2"), BLOCK_SIZE);
            case 2: return new S_Mino(textureManager.getTexture("S"), BLOCK_SIZE);
            case 3: return new Z_Mino(textureManager.getTexture("Z"), BLOCK_SIZE);
            case 4: return new O_Mino(textureManager.getTexture("O"), BLOCK_SIZE);
            case 5: return new I_Mino(textureManager.getTexture("I"), BLOCK_SIZE);
            case 6: return new T_Mino(textureManager.getTexture("T"), BLOCK_SIZE);
            default: return null;
        }
    }

    private Tetromino generateRandomPiece() {
        Random rand = new Random();
        Tetromino piece = createPieceByType(rand.nextInt(7));
        if (piece != null) piece.setPosition(NEXT_PREVIEW_X, NEXT_PREVIEW_Y);
        return piece;
    }

    private void spawnNewPiece() {
        currentPiece = nextPiece;
        currentPiece.setPosition(5, 19);

        nextPiece = generateRandomPiece();
        canHold = true;
        timeSeconds = 0;

        updateGhostPiece();

        if (board.checkCollision(currentPiece)) {
            triggerGameOver();
        }
    }

    private void triggerGameOver() {
        isGameOver = true;
        soundManager.stopMusic();
        soundManager.playSound("gameover");
    }

    private void lockPiece() {
        int cleared = board.placePiece(currentPiece);
        if (cleared > 0) {
            lines += cleared;
            // Skor Tetris
            switch(cleared) {
                case 1: score += 100; break;
                case 2: score += 300; break;
                case 3: score += 500; break;
                case 4: score += 800; break;
            }
            // SFX Clear
            soundManager.playSound("clear");
        } else {
            // SFX Drop biasa
            soundManager.playSound("drop");
        }

        spawnNewPiece();

        if (board.checkCollision(currentPiece)) {
            triggerGameOver();
        }
    }

    private void updateGhostPiece() {
        if (currentPiece == null) return;

        ghostBlocks.clear();
        for (Block b : currentPiece.getBlocks()) {
            ghostBlocks.add(new Block(b.getX(), b.getY(), BLOCK_SIZE, b.getTexture()));
        }

        boolean colliding = false;
        while (!colliding) {
            for (Block gb : ghostBlocks) gb.setY(gb.getY() - 1);

            for (Block gb : ghostBlocks) {
                if (gb.getY() < 0 || (gb.getY() < 20 && board.getGrid().get(gb.getY()).get(gb.getX()) != null)) {
                    colliding = true;
                    break;
                }
            }
        }
        for (Block gb : ghostBlocks) gb.setY(gb.getY() + 1);
    }

    @Override
    public void render() {
        if (!isGameOver) {
            updateGameLogic();
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) startNewGame();
        }

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawUI_Grid_And_Boxes();

        batch.begin();

        // --- BOARD ---
        batch.getTransformMatrix().setToTranslation(BOARD_OFFSET_X, BOARD_OFFSET_Y, 0);
        batch.setTransformMatrix(batch.getTransformMatrix());

        board.render(batch);

        // Ghost (Transparan)
        if (!isGameOver && currentPiece != null) {
            batch.setColor(1, 1, 1, 0.3f);
            for(Block gb : ghostBlocks) gb.render(batch);
            batch.setColor(1, 1, 1, 1f);
        }

        if (currentPiece != null) currentPiece.render(batch);
        if (nextPiece != null) nextPiece.render(batch);

        // --- HOLD ---
        if (heldPiece != null) {
            batch.getTransformMatrix().setToTranslation(HOLD_BOX_X + 40, HOLD_BOX_Y + 40, 0);
            batch.setTransformMatrix(batch.getTransformMatrix());
            heldPiece.render(batch);
        }

        // --- TEXT ---
        batch.getTransformMatrix().setToTranslation(0, 0, 0);
        batch.setTransformMatrix(batch.getTransformMatrix());

        font.setColor(Color.WHITE);
        font.draw(batch, "NEXT", BOARD_OFFSET_X + (NEXT_PREVIEW_X * BLOCK_SIZE) - 10, BOARD_OFFSET_Y + (NEXT_PREVIEW_Y + 3) * BLOCK_SIZE);
        font.draw(batch, "HOLD", HOLD_BOX_X + 10, HOLD_BOX_Y + (5 * BLOCK_SIZE));

        font.setColor(Color.CYAN);
        font.draw(batch, "SCORE", SCORE_X, SCORE_Y);
        font.setColor(Color.WHITE);
        font.draw(batch, String.valueOf(score), SCORE_X, SCORE_Y - 30);

        font.setColor(Color.GREEN);
        font.draw(batch, "LINES", SCORE_X, SCORE_Y - 70);
        font.setColor(Color.WHITE);
        font.draw(batch, String.valueOf(lines), SCORE_X, SCORE_Y - 100);

        if (isGameOver) {
            font.setColor(Color.RED);
            font.draw(batch, "GAME OVER", BOARD_OFFSET_X + 50, BOARD_OFFSET_Y + 300);
            font.draw(batch, "Press R", BOARD_OFFSET_X + 70, BOARD_OFFSET_Y + 260);
        }

        batch.end();
    }

    private void updateGameLogic() {
        handleInput();

        timeSeconds += Gdx.graphics.getDeltaTime();
        if (timeSeconds > period) {
            timeSeconds -= period;
            currentPiece.moveDown();

            if (board.checkCollision(currentPiece)) {
                movePiece(0, 1); // Undo
                lockPiece();
            }
            updateGhostPiece();
        }
    }

    private void handleInput() {
        float dt = Gdx.graphics.getDeltaTime();
        boolean moved = false;

        // 1. HARD DROP (Space)
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            while (!board.checkCollision(currentPiece)) currentPiece.moveDown();
            movePiece(0, 1);

            // SFX Drop Keras
            soundManager.playSound("drop");

            lockPiece();
            return;
        }

        // 2. MOVE LEFT (DAS)
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
                movePieceCheck(-1, 0);
                leftTimer = 0; moved = true;
                soundManager.playSound("move");
            } else {
                leftTimer += dt;
                if (leftTimer > DAS_DELAY && leftTimer > DAS_DELAY + DAS_SPEED) {
                    movePieceCheck(-1, 0);
                    leftTimer = DAS_DELAY; moved = true;
                    soundManager.playSound("move");
                }
            }
        } else { leftTimer = 0; }

        // 3. MOVE RIGHT (DAS)
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                movePieceCheck(1, 0);
                rightTimer = 0; moved = true;
                soundManager.playSound("move");
            } else {
                rightTimer += dt;
                if (rightTimer > DAS_DELAY && rightTimer > DAS_DELAY + DAS_SPEED) {
                    movePieceCheck(1, 0);
                    rightTimer = DAS_DELAY; moved = true;
                    soundManager.playSound("move");
                }
            }
        } else { rightTimer = 0; }

        // 4. SOFT DROP (Down)
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            downTimer += dt;
            if (downTimer > SOFT_DROP_SPEED) {
                currentPiece.moveDown();
                if (board.checkCollision(currentPiece)) {
                    movePiece(0, 1);
                } else {
                    timeSeconds = 0;
                }
                downTimer = 0; moved = true;
            }
        } else { downTimer = 0; }

        // 5. ROTATE (Up) - SRS
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            performWallKickRotation();
            moved = true;
        }

        // 6. HOLD (C)
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            if (canHold) {
                if (heldPiece == null) {
                    heldPiece = currentPiece; spawnNewPiece();
                } else {
                    Tetromino temp = currentPiece; currentPiece = heldPiece; heldPiece = temp;
                    currentPiece.setPosition(5, 19);
                }
                heldPiece.setPosition(0, 0); canHold = false;

                soundManager.playSound("hold");
                updateGhostPiece();
            }
        }

        if (moved) updateGhostPiece();
    }

    private void performWallKickRotation() {
        int oldState = currentPiece.getRotationState();
        int newState = (oldState + 1) % 4;

        currentPiece.rotate();

        String type = currentPiece.getClass().getSimpleName();
        ArrayList<WallKickMgr.Offset> kicks = kickMgr.getKicks(type, oldState, newState);

        boolean success = false;
        if (kicks != null) {
            for (WallKickMgr.Offset offset : kicks) {
                movePiece(offset.x, offset.y);
                if (!board.checkCollision(currentPiece)) {
                    success = true;
                    currentPiece.addRotationState(1);
                    soundManager.playSound("rotate");
                    break;
                } else {
                    movePiece(-offset.x, -offset.y);
                }
            }
        } else {
            if (!board.checkCollision(currentPiece)) {
                success = true;
                currentPiece.addRotationState(1);
                soundManager.playSound("rotate");
            }
        }

        if (!success) {
            currentPiece.rotate();
            currentPiece.rotate();
            currentPiece.rotate();
        }
    }

    // Helper: Gunakan moveByOffset agar Pivot ikut geser
    private void movePiece(int dx, int dy) {
        if (currentPiece != null) {
            currentPiece.moveByOffset(dx, dy);
        }
    }

    private void movePieceCheck(int dx, int dy) {
        movePiece(dx, dy);
        if (board.checkCollision(currentPiece)) {
            movePiece(-dx, -dy);
        }
    }

    private void drawUI_Grid_And_Boxes() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(new Color(1, 1, 1, 0.2f));

        for (int i = 0; i <= 10; i++) {
            float x = BOARD_OFFSET_X + i * BLOCK_SIZE;
            shapeRenderer.line(x, BOARD_OFFSET_Y, x, BOARD_OFFSET_Y + 20 * BLOCK_SIZE);
        }
        for (int i = 0; i <= 20; i++) {
            float y = BOARD_OFFSET_Y + i * BLOCK_SIZE;
            shapeRenderer.line(BOARD_OFFSET_X, y, BOARD_OFFSET_X + 10 * BLOCK_SIZE, y);
        }

        shapeRenderer.setColor(Color.YELLOW);
        shapeRenderer.rect(BOARD_OFFSET_X + (NEXT_PREVIEW_X - 2) * BLOCK_SIZE, BOARD_OFFSET_Y + (NEXT_PREVIEW_Y - 2) * BLOCK_SIZE, 6 * BLOCK_SIZE, 6 * BLOCK_SIZE);
        shapeRenderer.rect(HOLD_BOX_X, HOLD_BOX_Y, 5 * BLOCK_SIZE, 5 * BLOCK_SIZE);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    @Override
    public void dispose() {
        batch.dispose();
        textureManager.dispose();
        shapeRenderer.dispose();
        font.dispose();
        soundManager.dispose(); // Hapus Audio
    }
}
