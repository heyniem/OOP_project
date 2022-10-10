package entities.AnimatedEntities.Characters.Enemies;

import entities.AnimatedEntities.AnimatedEntity;
import javafx.scene.image.Image;

import java.util.List;

public class Oreal extends Enemy {
    public Oreal(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    protected boolean isCollide(List<AnimatedEntity> entities, int newX, int newY) {
        return false;
    }
}
