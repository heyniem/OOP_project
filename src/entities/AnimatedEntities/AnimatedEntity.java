package entities.AnimatedEntities;

import entities.Entity;
import javafx.scene.image.Image;
import Sound.Sound;
import static Database.Database.sound;
import static entities.Map.explodeScene;
import static graphics.Sprite.SCALED_SIZE;

public abstract class AnimatedEntity extends Entity {
    public boolean dead = false;
    protected int id;

    public int getId() {
        return id;
    }

    public AnimatedEntity(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img);
        this.id = id;
    }

    public boolean isDead() {
        return false;
    }

    public void checkDead() {
        int nextX_1 = x / SCALED_SIZE;
        int nextY_1 = y / SCALED_SIZE;

        int nextX_2 = (x + SCALED_SIZE - 1) / SCALED_SIZE;
        int nextY_2 = y / SCALED_SIZE;

        int nextX_3 = x / SCALED_SIZE;
        int nextY_3 = (y + SCALED_SIZE - 1) / SCALED_SIZE;

        int nextX_4 = (x + SCALED_SIZE - 1) / SCALED_SIZE;
        int nextY_4 = (y + SCALED_SIZE - 1) / SCALED_SIZE;

        if (((explodeScene[nextY_1][nextX_1] >=1) ||
                (explodeScene[nextY_2][nextX_2] >= 1) ||
                (explodeScene[nextY_3][nextX_3] >= 1) ||
                (explodeScene[nextY_4][nextX_4] >= 1))) {
            dead = true;
        }
    }
}
