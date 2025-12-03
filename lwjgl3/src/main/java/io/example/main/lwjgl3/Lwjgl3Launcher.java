package io.example.main.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import io.example.main.TetrisGame;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return;
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new TetrisGame(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Tetris");
        configuration.useVsync(true);

        // Fix: Ambil refresh rate tanpa variable var/DisplayMode
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);

        int windowWidth = 1280;
        int windowHeight = 720;
        configuration.setWindowedMode(windowWidth, windowHeight);

        // Fix: Logic Posisi Tengah Layar yang Aman
        int screenWidth = Lwjgl3ApplicationConfiguration.getDisplayMode().width;
        int screenHeight = Lwjgl3ApplicationConfiguration.getDisplayMode().height;

        int posX = (screenWidth - windowWidth) / 2;
        int posY = (screenHeight - windowHeight) / 2;

        configuration.setWindowPosition(posX, posY);

        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}
