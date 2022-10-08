package entities.AnimatedEntities.Characters.Enemies;

import AI.AIEnemies.SuperDumbAI;
import entities.AnimatedEntities.AnimatedEntity;
import entities.Entity;
import javafx.scene.image.Image;

import java.util.List;

import static entities.Map.scene;
import static graphics.Sprite.*;
import static Database.Database.entities;

public class Balloon extends Enemy {
    public int frameBalloon = 0, intervalBalloon = 6, indexBalloon = 0;
    private int direction = 1;
    private int temp;
    SuperDumbAI balloonAI = new SuperDumbAI();

    public Balloon(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
    }

    @Override
    public void update() {
        if (x % SCALED_SIZE == 0 && y % SCALED_SIZE == 0 && ((x / SCALED_SIZE) % 2 == 1 && (y / SCALED_SIZE) % 2 == 1)) {
            direction = balloonAI.chooseDirection(direction);
        }
        temp = direction;
        while (true) {
            frameBalloon++;
            if (frameBalloon > intervalBalloon) {
                frameBalloon = 0;
                indexBalloon++;
                if (indexBalloon > 2) {
                    indexBalloon = 0;
                }
            }
            if (direction == 2 || direction == 1) {
                if (indexBalloon == 0) {
                    this.img = balloom_right1.getFxImage();
                }
                if (indexBalloon == 1) {
                    this.img = balloom_right2.getFxImage();
                }
                if (indexBalloon == 2) {
                    this.img = balloom_right3.getFxImage();
                }
            } else if (direction == 3 || direction == 0) {
                if (indexBalloon == 0) {
                    this.img = balloom_left1.getFxImage();
                }
                if (indexBalloon == 1) {
                    this.img = balloom_left2.getFxImage();
                }
                if (indexBalloon == 2) {
                    this.img = balloom_left3.getFxImage();
                }
            }
            if (direction == 1) {
                if (isFree(x + 1, y) && !isCollide(entities, x + 1, y)) {
                    this.x++;
                    break;
                } else {
                    direction = 2;
                }
            } else if (direction == 2) {
                if (isFree(x, y + 1) && !isCollide(entities, x, y + 1)) {
                    this.y++;
                    break;
                } else {
                    direction = 3;
                }
            } else if (direction == 3) {
                if (isFree(x - 1, y) && !isCollide(entities, x - 1, y)) {
                    this.x--;
                    break;
                } else {
                    direction = 0;
                }
            } else if (direction == 0) {
                if (isFree(x, y - 1) && !isCollide(entities, x, y - 1)) {
                    this.y--;
                    break;
                } else {
                    direction = 1;
                }
            }
            if (direction == temp) break;
        }
    }

    private boolean isFree(int nextX, int nextY) {
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

    @Override
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