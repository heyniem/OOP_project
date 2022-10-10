package entities.AnimatedEntities.Weapons.Bomb;

import entities.AnimatedEntities.Weapons.Weapon;
import graphics.Sprite;
import javafx.scene.image.Image;
import Database.Database;

import static Database.Database.*;
import static Input.KeyHandle.placeBomb;

public class Bomb extends Weapon {

    private int x1 = (bomber.getX() + 16) / 32;
    private int y1 = (bomber.getY() + 16) / 32;

    private int bombFrame = 0;
    private int bombInterval = 0;
    private boolean explode;

    public Bomb(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
    }

    @Override
    public void update() {
    }
}