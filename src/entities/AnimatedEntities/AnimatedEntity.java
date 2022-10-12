package entities.AnimatedEntities;

import entities.Entity;
import javafx.scene.image.Image;

public abstract class AnimatedEntity extends Entity {
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

    public void checkDead(){};
}
