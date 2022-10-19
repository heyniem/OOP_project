package entities.AnimatedEntities.Characters.Enemies;

import AI.AIEnemies.DumbAI;
import AI.AIEnemies.SmartAI;
import javafx.scene.image.Image;

import static Database.Database.*;
import static entities.Map.scene;
import static graphics.Sprite.*;
import static graphics.Sprite.SCALED_SIZE;

public class Ball extends Enemy {
    public int frameBalloon = 0, intervalBalloon = 6, indexBalloon = 0;
    private int direction = 3;
    private int intervalDead = 0;
    private int temp;
    SmartAI ballAI = new SmartAI(true);
    //DumbAI KondoriaAI = new DumbAI();

    public Ball(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
    }

    public void update() {
        if (dead) {
            intervalDead++;
            if (intervalDead <= 11) setImg(kondoria_dead.getFxImage());
            else entities.remove(this);
        } else {
            if (x % SCALED_SIZE == 0 && y % SCALED_SIZE == 0) {
                direction = ballAI.chooseDirection(this.x, this.y, direction, 76);
                System.out.println(direction);
                //direction = KondoriaAI.chooseDirection(direction, 64);
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
                        this.img = minvo_right1.getFxImage();
                    }
                    if (indexBalloon == 1) {
                        this.img = minvo_right2.getFxImage();
                    }
                    if (indexBalloon == 2) {
                        this.img = minvo_right3.getFxImage();
                    }
                } else if (direction == 3 || direction == 0) {
                    if (indexBalloon == 0) {
                        this.img = minvo_left1.getFxImage();
                    }
                    if (indexBalloon == 1) {
                        this.img = minvo_left2.getFxImage();
                    }
                    if (indexBalloon == 2) {
                        this.img = minvo_left3.getFxImage();
                    }
                }
                if (direction == 1) {
                    if (isFree(x + 2, y)) {
                        this.x++;
                        break;
                    } else {
                        direction = 2;
                    }
                } else if (direction == 2) {
                    if (isFree(x, y + 2)) {
                        this.y++;
                        break;
                    } else {
                        direction = 3;
                    }
                } else if (direction == 3) {
                    if (isFree(x - 2, y)) {
                        this.x--;
                        break;
                    } else {
                        direction = 0;
                    }
                } else if (direction == 0) {
                    if (isFree(x, y - 2)) {
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
    @Override
    protected boolean isFree(int nextX, int nextY) {
        if (nextX >= SCALED_SIZE && nextX <= SCALED_SIZE * (WIDTH-2) && nextY >= SCALED_SIZE && nextY < SCALED_SIZE * (HEIGHT - 2)) {
            //System.out.println("Condition 1");
            if (scene[nextY / SCALED_SIZE][nextX / SCALED_SIZE] == 3) {
                return false;
            }
            else return true;
        }
        else return false;
    }
}
