package entities.AnimatedEntities.Characters.Enemies;

import AI.AIEnemies.SuperDumbAI;
import entities.AnimatedEntities.AnimatedEntity;
import entities.AnimatedEntities.Characters.Bomberman;
import javafx.scene.image.Image;

import java.util.List;

import static Database.Database.entities;
import static graphics.Sprite.*;
import static entities.Map.*;

public class Ghost extends Enemy{
    public int frameGhost = 0, intervalGhost = 6, indexGhost = 0;
    private int direction = 1;
    private int temp;
    SuperDumbAI ghostAI = new SuperDumbAI();

    public Ghost(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
        canMoveThroughWall = true;
    }

    @Override
    public void update() {
        if (x % SCALED_SIZE == 0 && y % SCALED_SIZE == 0) {
            direction = ghostAI.chooseDirection(direction, 76);
        }
        temp = direction;
        while (true) {
            frameGhost++;
            if (frameGhost > intervalGhost) {
                frameGhost = 0;
                indexGhost++;
                if (indexGhost > 2) {
                    indexGhost = 0;
                }
            }
            if (direction == 2 || direction == 1) {
                if (indexGhost == 0) {
                    this.img = ghost_right1.getFxImage();
                }
                if (indexGhost == 1) {
                    this.img = ghost_right2.getFxImage();
                }
                if (indexGhost == 2) {
                    this.img = ghost_right3.getFxImage();
                }
            } else if (direction == 3 || direction == 0) {
                if (indexGhost == 0) {
                    this.img = ghost_left1.getFxImage();
                }
                if (indexGhost == 1) {
                    this.img = ghost_left2.getFxImage();
                }
                if (indexGhost == 2) {
                    this.img = ghost_left3.getFxImage();
                }
            }
            if (direction == 1) {
                if (isFree(x + 1, y) && !isCollide(entities, x + 1, y)) {
                    this.x++;
                    break;
                } else {
                    if (isCollide(entities, x + 1, y)) direction = 3;
                    else direction = 2;
                }
            } else if (direction == 2) {
                if (isFree(x, y + 1) && !isCollide(entities, x, y + 1)) {
                    this.y++;
                    break;
                } else {
                    if (isCollide(entities, x, y + 1)) direction = 0;
                    else direction = 3;
                }
            } else if (direction == 3) {
                if (isFree(x - 1, y) && !isCollide(entities, x - 1, y)) {
                    this.x--;
                    break;
                } else {
                    if (isCollide(entities, x - 1, y)) direction = 1;
                    else direction = 0;
                }
            } else if (direction == 0) {
                if (isFree(x, y - 1) && !isCollide(entities, x, y - 1)) {
                    this.y--;
                    break;
                } else {
                    if (isCollide(entities, x, y - 1)) direction = 2;
                    else direction = 1;
                }
            }
            if (direction == temp) break;
        }
    }

    @Override
    protected boolean isFree(int nextX, int nextY) {
        return ((nextX >= 32 && nextX <= 32 * 18 && nextY >=32 && nextY <= 32 * 13) && scene[nextY/SCALED_SIZE][nextX/SCALED_SIZE] != 3);
    }

}
