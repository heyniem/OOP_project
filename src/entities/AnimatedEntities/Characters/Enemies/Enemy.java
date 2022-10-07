package entities.AnimatedEntities.Characters.Enemies;

import entities.AnimatedEntities.Characters.Character;
import entities.Entity;
import javafx.scene.image.Image;

import java.util.List;

public abstract class Enemy extends Character {
    protected boolean dead = false;
    protected boolean canMoveThroughWall = false;
    public Enemy(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
    }
}
