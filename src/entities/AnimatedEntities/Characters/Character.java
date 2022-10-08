package entities.AnimatedEntities.Characters;

import entities.AnimatedEntities.AnimatedEntity;
import javafx.scene.image.Image;

import java.util.List;

public abstract class Character extends AnimatedEntity {
    public Character(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
    }

    @Override
    public void update() {

    }

    protected abstract boolean isCollide(List<AnimatedEntity> entities, int newX, int newY);
}
