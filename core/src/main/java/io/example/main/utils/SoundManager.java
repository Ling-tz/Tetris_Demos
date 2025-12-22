package io.example.main.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;

public class SoundManager {
    private HashMap<String, Sound> soundEffects;
    private Music backgroundMusic;

    public SoundManager() {
        soundEffects = new HashMap<>();
    }

    public void loadAllAudio() {
            loadMusic("audio/bgm.mp3");
            loadSound("move", "audio/move.wav");
            loadSound("rotate", "audio/rotate.wav");
            loadSound("drop", "audio/drop.wav");
            loadSound("clear", "audio/clear.wav");
            loadSound("hold", "audio/hold.wav");
            loadSound("gameover", "audio/gameover.wav");
    }

    public void loadSound(String key, String path) {
        if (Gdx.files.internal(path).exists()) {
            try {
                Sound sound = Gdx.audio.newSound(Gdx.files.internal(path));
                soundEffects.put(key, sound);
                System.out.println("SUKSES LOAD: " + path);
            } catch (Exception e) {
                System.err.println("GAGAL LOAD (Format Error): " + path);
            }
        } else {
            System.err.println("FILE TIDAK DITEMUKAN: " + path + " (Cek nama folder/file!)");
        }
    }

    public void loadMusic(String path) {
        if (Gdx.files.internal(path).exists()) {
            try {
                backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal(path));
                backgroundMusic.setLooping(true);
                backgroundMusic.setVolume(0.5f);
                System.out.println("SUKSES LOAD BGM: " + path);
            } catch (Exception e) {
                System.err.println("GAGAL LOAD BGM (Format Error): " + path);
            }
        } else {
            System.err.println("FILE BGM TIDAK DITEMUKAN: " + path);
        }
    }

    public void playSound(String key) {
        if (soundEffects.containsKey(key)) {
            soundEffects.get(key).play(1.0f);
        }
    }

    public void playMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.play();
        }
    }

    public void stopMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    public void dispose() {
        for (Sound s : soundEffects.values()) {
            if (s != null) s.dispose();
        }
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
    }
}
