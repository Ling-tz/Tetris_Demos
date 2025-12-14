package io.example.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen extends ScreenAdapter {

    private final TetrisGame game;
    private Stage stage;

    // Assets
    private Texture bgTexture;
    private Texture playTexture, playShadowTexture;
    private Texture quitTexture, quitShadowTexture;

    // --- KONSTANTA BARU UNTUK UKURAN DAN JARAK ---
    private final float BUTTON_WIDTH = 500f; // Lebar tombol agar tidak terlalu besar
    private final float BUTTON_HEIGHT = 250f; // Tinggi tombol
    private final float BUTTON_PADDING = 10f; // Jarak vertikal antar tombol

    public MainMenuScreen(TetrisGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // --- LOAD ASSETS (Sesuaikan path folder assets kamu) ---
        bgTexture = new Texture(Gdx.files.internal("Button/minecraft_bg.png"));
        // Asumsi nama asset yang baru diunggah untuk Play dan Quit
        playTexture = new Texture(Gdx.files.internal("Button/play_button_mine.png"));
        playShadowTexture = new Texture(Gdx.files.internal("Button/play_button_mine_shadow.png"));
        quitTexture = new Texture(Gdx.files.internal("Button/quit_button_mine.png"));
        quitShadowTexture = new Texture(Gdx.files.internal("Button/quit_button_mine_shadow.png"));

        // 1. Background
        Image bgImage = new Image(bgTexture);
        bgImage.setFillParent(true);
        stage.addActor(bgImage);

        // 2. Table Layout (di tengah layar)
        Table table = new Table();
        table.setFillParent(true);
        table.center(); // Memastikan tabel berada di tengah layar
        stage.addActor(table);

        // 3. Play Button Style
        ImageButton.ImageButtonStyle playStyle = new ImageButton.ImageButtonStyle();
        playStyle.up = new TextureRegionDrawable(new TextureRegion(playTexture));
        playStyle.down = new TextureRegionDrawable(new TextureRegion(playShadowTexture));

        ImageButton playBtn = new ImageButton(playStyle);
        playBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });

        // 4. Quit Button Style
        ImageButton.ImageButtonStyle quitStyle = new ImageButton.ImageButtonStyle();
        quitStyle.up = new TextureRegionDrawable(new TextureRegion(quitTexture));
        quitStyle.down = new TextureRegionDrawable(new TextureRegion(quitShadowTexture));

        ImageButton quitBtn = new ImageButton(quitStyle);
        quitBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // 5. Masukkan ke Table dengan pengaturan ukuran dan jarak (padding)
        // Set ukuran (size) dan tambahkan padding di bawah (padBottom)
        table.add(playBtn).size(BUTTON_WIDTH, BUTTON_HEIGHT).padBottom(BUTTON_PADDING).row();

        // Set ukuran (size)
        table.add(quitBtn).size(BUTTON_WIDTH, BUTTON_HEIGHT).row();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        // Pastikan semua texture di-dispose
        if (bgTexture != null) bgTexture.dispose();
        if (playTexture != null) playTexture.dispose();
        if (playShadowTexture != null) playShadowTexture.dispose();
        if (quitTexture != null) quitTexture.dispose();
        if (quitShadowTexture != null) quitShadowTexture.dispose();
    }
}
