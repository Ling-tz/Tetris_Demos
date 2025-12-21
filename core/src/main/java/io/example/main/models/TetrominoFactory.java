package io.example.main.models;

import io.example.main.utils.TextureManager;

public class TetrominoFactory {
    public static Tetromino create(int type, TextureManager tm, int blockSize) {
        switch (type) {
            case 0: return new L1_Mino(tm.getTexture("L1"), blockSize);
            case 1: return new L2_Mino(tm.getTexture("L2"), blockSize);
            case 2: return new S_Mino(tm.getTexture("S"), blockSize);
            case 3: return new Z_Mino(tm.getTexture("Z"), blockSize);
            case 4: return new O_Mino(tm.getTexture("O"), blockSize);
            case 5: return new I_Mino(tm.getTexture("I"), blockSize);
            case 6: return new T_Mino(tm.getTexture("T"), blockSize);
            default: return null;
        }
    }
}
