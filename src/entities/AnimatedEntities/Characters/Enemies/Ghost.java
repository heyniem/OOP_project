package entities.AnimatedEntities.Characters.Enemies;

import AI.AIEnemies.SuperDumbAI;
import javafx.scene.image.Image;

import static Database.Database.*;
import static entities.Map.*;
import static graphics.Sprite.*;

public class Ghost extends Enemy {
    public int frameGhost = 0, intervalGhost = 5, indexGhost = 0;
    private int direction = 1;
    private int intervalDead = 0;
    private int temp;
    SuperDumbAI ghostAI = new SuperDumbAI();

    public Ghost(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
        canMoveThroughWall = true;
    }

    @Override
    public void update() {
        if (dead) {
            intervalDead++;
            if (intervalDead <= 11) setImg(ghost_dead.getFxImage());
            else entities.remove(this);
        } else {
            if (x % SCALED_SIZE == 0 && y % SCALED_SIZE == 0) {
                direction = ghostAI.chooseDirection(direction, 76);
            }
            temp = direction;
            while (true) {
                frameGhost++;
                if (frameGhost > intervalGhost) {
                    frameGhost = 0;
                    indexGhost++;
                    if (indexGhost > 3) {
                        indexGhost = 0;
                    }
                }
                if (direction == 2 || direction == 1) {
                    if (indexGhost == 1) {
                        this.img = ghost_right1.getFxImage();
                    }
                    if (indexGhost == 2) {
                        this.img = ghost_right2.getFxImage();
                    }
                    if (indexGhost == 3) {
                        this.img = ghost_right3.getFxImage();
                    }
                } else if (direction == 3 || direction == 0) {
                    if (indexGhost == 1) {
                        this.img = ghost_left1.getFxImage();
                    }
                    if (indexGhost == 2) {
                        this.img = ghost_left2.getFxImage();
                    }
                    if (indexGhost == 3) {
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
