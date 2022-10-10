package entities.AnimatedEntities.Characters.Enemies;

import entities.AnimatedEntities.AnimatedEntity;
import entities.AnimatedEntities.Characters.Character;
import javafx.scene.image.Image;

import java.util.List;

import static entities.Map.scene;
import static graphics.Sprite.SCALED_SIZE;

public abstract class Enemy extends Character {
    protected boolean dead = false;
    protected boolean canMoveThroughWall = false;
    public Enemy(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
    }

    protected boolean isFree(int nextX, int nextY) {
        int nextX_1 = nextX / SCALED_SIZE;
        int nextY_1 = nextY / SCALED_SIZE;

        int nextX_2 = (nextX + SCALED_SIZE - 1) / SCALED_SIZE;
        int nextY_2 = nextY / SCALED_SIZE;

        int nextX_3 = nextX / SCALED_SIZE;
        int nextY_3 = (nextY + SCALED_SIZE - 1) / SCALED_SIZE;

        int nextX_4 = (nextX + SCALED_SIZE - 1) / SCALED_SIZE;
        int nextY_4 = (nextY + SCALED_SIZE - 1) / SCALED_SIZE;

        return !((scene[nextY_1][nextX_1] == 1 || scene[nextY_1][nextX_1] == 2) ||
                (scene[nextY_2][nextX_2] == 1 || scene[nextY_2][nextX_2] == 2) ||
                (scene[nextY_3][nextX_3] == 1 || scene[nextY_3][nextX_3] == 2) ||
                (scene[nextY_4][nextX_4] == 1 || scene[nextY_4][nextX_4] == 2));
    }

    protected boolean isCollide(List<AnimatedEntity> entities, int newX, int newY) {
        int tempX, tempY;
        for (AnimatedEntity i : entities) {
            if (i.getId() != this.id) {
                tempX = i.getX();
                tempY = i.getY();
                if (Math.abs(tempX - newX) < 32 && Math.abs(tempY - newY) < 32) {
                    return true;
                }
            }
        }
        return false;
    }
}
