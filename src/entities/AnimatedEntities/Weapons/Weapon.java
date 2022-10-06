package entities.AnimatedEntities.Weapons;

import entities.AnimatedEntities.AnimatedEntity;
import javafx.scene.image.Image;

public abstract class Weapon extends AnimatedEntity {
    public Weapon(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
}
