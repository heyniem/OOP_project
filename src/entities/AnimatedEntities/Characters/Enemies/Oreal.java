package entities.AnimatedEntities.Characters.Enemies;

import AI.AIEnemies.AIEnemy;
import AI.AIEnemies.SmartAI;
import AI.AIEnemies.SuperDumbAI;
import entities.AnimatedEntities.AnimatedEntity;
import javafx.scene.image.Image;

import java.util.List;
import java.util.Random;

import static Database.Database.entities;
import static Database.Database.score;
import static graphics.Sprite.*;
import static graphics.Sprite.balloom_left3;

public class Oreal extends Enemy {
    private int direction = 1;
    private int temp;
    private int frameOreal = 0, intervalOreal = 6, indexOreal = 0;

    private int intervalDead = 0;
    Random random = new Random();

    SuperDumbAI OrealAI = new SuperDumbAI();
    SmartAI OrealAI2 = new SmartAI();
    public Oreal(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
    }

    @Override
    public void update() {
        if (dead) {
            intervalDead++;
            if (intervalDead <= 11) setImg(oneal_dead.getFxImage());
            else {
                score += 100;
                entities.remove(this);
            }
        }
        else {
            if (x % SCALED_SIZE == 0 && y % SCALED_SIZE == 0) {
                if (random.nextInt(10) <=2) {
                    direction = OrealAI.chooseDirection(direction, 64);
                }
                else {
                    direction = OrealAI2.chooseDirection(this.x, this.y, direction, 64);
                }
            }
            temp = direction;
            while (true) {
                frameOreal++;
                if (frameOreal > intervalOreal) {
                    frameOreal = 0;
                    indexOreal++;
                    if (indexOreal > 2) {
                        indexOreal = 0;
                    }
                }
                if (direction == 2 || direction == 1) {
                    if (indexOreal == 0) {
                        this.img = oneal_right1.getFxImage();
                    }
                    if (indexOreal == 1) {
                        this.img = oneal_right2.getFxImage();
                    }
                    if (indexOreal == 2) {
                        this.img = oneal_right3.getFxImage();
                    }
                } else if (direction == 3 || direction == 0) {
                    if (indexOreal == 0) {
                        this.img = oneal_left1.getFxImage();
                    }
                    if (indexOreal == 1) {
                        this.img = oneal_left2.getFxImage();
                    }
                    if (indexOreal == 2) {
                        this.img = oneal_left3.getFxImage();
                    }
                }
                if (direction == 1) {
                    if (isFree(x + 2, y) && !isCollide(entities, x + 2, y)) {
                        this.x+=2;
                        break;
                    } else {
                        direction = 2;
                    }
                } else if (direction == 2) {
                    if (isFree(x, y + 2) && !isCollide(entities, x, y + 2)) {
                        this.y+=2;
                        break;
                    } else {
                        direction = 3;
                    }
                } else if (direction == 3) {
                    if (isFree(x - 2, y) && !isCollide(entities, x - 2, y)) {
                        this.x-=2;
                        break;
                    } else {
                        direction = 0;
                    }
                } else if (direction == 0) {
                    if (isFree(x, y - 2) && !isCollide(entities, x, y - 2)) {
                        this.y-=2;
                        break;
                    } else {
                        direction = 1;
                    }
                }
                if (direction == temp) break;
            }
        }
    }

}
