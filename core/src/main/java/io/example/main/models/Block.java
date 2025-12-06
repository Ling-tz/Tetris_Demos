package io.example.main.models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Block {
    private int x;
    private int y;
    private Texture textureImage;
    private int size;

    // 0: Normal
    // 1: TNT (Ledakan Area)
    // 2: TOTEM (Extra Life - Disimpan saat di-clear)
    // 3: PEARL (Hapus Semua Bedrock)
    private int abilityType = 0;

    public Block(int x, int y, int size, Texture texture) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.textureImage = texture;
        this.abilityType = 0; // Default normal
    }

    public void render(SpriteBatch batch) {
        batch.draw(textureImage, x * size, y * size, size, size);
    }

    // --- GETTER SETTER BARU ---
    public void setAbilityType(int type) { this.abilityType = type; }
    public int getAbilityType() { return abilityType; }
    public void setTexture(Texture tex) { this.textureImage = tex; } // Buat ganti gambar jadi TNT

    // Getter Setter lama
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public Texture getTexture() { return textureImage; }
}
