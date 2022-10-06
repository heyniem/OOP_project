package entities.AnimatedEntities;

import entities.Entity;
import javafx.scene.image.Image;

public abstract class AnimatedEntity extends Entity {
    public AnimatedEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
}
