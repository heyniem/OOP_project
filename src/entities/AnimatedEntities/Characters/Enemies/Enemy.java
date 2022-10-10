package entities.AnimatedEntities.Characters.Enemies;

import entities.AnimatedEntities.Characters.Character;
import javafx.scene.image.Image;

public abstract class Enemy extends Character {
    protected boolean dead = false;
    protected boolean canMoveThroughWall = false;
    public Enemy(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
    }
}
