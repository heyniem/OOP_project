package entities.AnimatedEntities.Characters.Enemies;

import AI.AIEnemies.SmartAI;
import AI.AIEnemies.SuperDumbAI;
import entities.AnimatedEntities.AnimatedEntity;
import javafx.scene.image.Image;

import java.util.List;

import static Database.Database.entities;
import static entities.Map.scene;
import static graphics.Sprite.*;

public class Balloon extends Enemy {
    public int frameBalloon = 0, intervalBalloon = 6, indexBalloon = 0;
    private int direction = 3;
    private int intervalDead = 0;
    private int temp;
    //SmartAI balloonAI = new SmartAI();
    SuperDumbAI balloonAI = new SuperDumbAI();

    public Balloon(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
    }

    @Override
    public void update() {
        if (dead) {
            intervalDead++;
            if (intervalDead <= 11) setImg(balloom_dead.getFxImage());
            else entities.remove(this);
        }
        else {
            if (x % SCALED_SIZE == 0 && y % SCALED_SIZE == 0) {
                //direction = balloonAI.chooseDirection(this.x, this.y, direction, 76);
                direction = balloonAI.chooseDirection(direction, 64);
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
            //System.out.println(direction);
        }
    }


}