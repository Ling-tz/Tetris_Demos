package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.example.main.Board;

public abstract class Enemy {
    private String name;
    private int maxHp;
    private int currentHp;
    protected float attackInterval;
    protected float attackTimer;
    protected Texture texture;
    protected Board board;
    protected Texture garbageTexture;
    protected int tier;

    public Enemy(String baseName, int baseHp, float baseInterval, int tier, Texture texture, Board board, Texture garbageTexture) {
        this.tier = tier;
        this.texture = texture;
        this.board = board;
        this.garbageTexture = garbageTexture;

        // Pindahkan logika kalkulasi ke method terpisah
        calculateStats(baseName, baseHp, baseInterval);
    }

    private void calculateStats(String baseName, int baseHp, float baseInterval) {
        this.maxHp = baseHp + ((tier - 1) * 50);
        this.currentHp = this.maxHp;
        this.attackInterval = Math.max(1.0f, baseInterval - ((tier - 1) * 1.0f));
        this.attackTimer = this.attackInterval;

        String tierName = (tier == 1) ? "Lemah" : (tier == 2) ? "Sedang" : "Kuat";
        this.name = baseName + " (" + tierName + ")";
    }

    public void update(float delta) {
        if (!isDead()) {
            attackTimer -= delta;
            if (attackTimer <= 0) {
                performAttack();
                attackTimer = attackInterval;
            }
        }
    }

    public void takeDamage(int amount) {
        currentHp = Math.max(0, currentHp - amount);
    }

    public abstract void performAttack();

    public void render(SpriteBatch batch, float x, float y, float width, float height) {
        if (texture != null && !isDead()) {
            batch.draw(texture, x, y, width, height);
        }
    }

    // Getters
    public String getName() { return name; }
    public int getCurrentHp() { return currentHp; }
    public int getMaxHp() { return maxHp; }
    public float getAttackTimer() { return attackTimer; }
    public boolean isDead() { return currentHp <= 0; }
    public float getHpPercent() { return (float) currentHp / maxHp; }
}
