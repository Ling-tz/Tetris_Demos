package io.example.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TetrisGame extends Game {
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // Langsung buka Main Menu saat game start
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render(); // Wajib ada agar Screen yang aktif bisa dirender
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
