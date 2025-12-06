package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.example.main.Board;

public abstract class Enemy {
    protected String name;
    protected int maxHp;
    protected int currentHp;
    protected float attackInterval;
    protected float attackTimer;
    protected Texture texture;
    protected Board board;
    protected Texture garbageTexture;

    // TAMBAHAN: Tier
    protected int tier;

    public Enemy(String name, int baseHp, float baseInterval, int tier, Texture texture, Board board, Texture garbageTexture) {
        this.tier = tier;

        // KALKULASI STATUS BERDASARKAN TIER
        // HP naik 50 setiap tier
        this.maxHp = baseHp + ((tier - 1) * 50);
        this.currentHp = this.maxHp;

        // Attack Speed makin cepat 1 detik setiap tier
        this.attackInterval = Math.max(1.0f, baseInterval - ((tier - 1) * 1.0f));

        // Nama ditambah Tier (Contoh: "Zombie (Lv.2)")
        String tierName = (tier == 1) ? "Lemah" : (tier == 2) ? "Sedang" : "Kuat";
        this.name = name + " (" + tierName + ")";

        this.attackTimer = this.attackInterval;
        this.texture = texture;
        this.board = board;
        this.garbageTexture = garbageTexture;
    }

    // ... (Sisa method update, takeDamage, render, getter SAMA SAJA) ...
    public void update(float delta) {
        if (currentHp > 0) {
            attackTimer -= delta;
            if (attackTimer <= 0) {
                performAttack();
                attackTimer = attackInterval;
            }
        }
    }
    public void takeDamage(int amount) {
        currentHp -= amount;
        if (currentHp < 0) currentHp = 0;
    }
    public abstract void performAttack();
    public void render(SpriteBatch batch, float x, float y, float width, float height) {
        if (texture != null && currentHp > 0) batch.draw(texture, x, y, width, height);
    }
    public String getName() { return name; }
    public int getCurrentHp() { return currentHp; }
    public int getMaxHp() { return maxHp; }
    public float getAttackTimer() { return attackTimer; }
    public boolean isDead() { return currentHp <= 0; }
}
