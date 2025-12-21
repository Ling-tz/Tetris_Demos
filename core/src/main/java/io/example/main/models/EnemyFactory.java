package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;
import io.example.main.Board;
import io.example.main.utils.TextureManager;

public class EnemyFactory {
    public static Enemy createEnemy(int type, int tier, Board board, TextureManager tm, Texture bedrock) {
        switch (type) {
            case 0: return new Zombie(tier, tm.getTexture("zombie"), board, bedrock);
            case 1: return new Skeleton(tier, tm.getTexture("skeleton"), board, bedrock);
            case 2: return new Creeper(tier, tm.getTexture("creeper"), board, bedrock);
            default: return new Zombie(tier + 3, tm.getTexture("zombie"), board, bedrock);
        }
    }
}
