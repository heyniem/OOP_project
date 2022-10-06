package entities.AnimatedEntities.Characters;

import javafx.scene.image.Image;

import static Input.KeyHandle.*;
import static graphics.Sprite.SCALED_SIZE;
import static entities.Map.scene;

public class Bomberman extends Character{
    private int speed = 2;
    //private int status = -1;
    public Bomberman(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (up && isCollided(x,y-speed)) y -= speed;
        if (down && isCollided(x,y+speed)) y += speed;
        if (right && isCollided(x+speed,y)) x += speed;
        if (left && isCollided(x-speed,y)) x -= speed;
    }

    private boolean isCollided(int nextX, int nextY) {
//        int xCur = x/SCALED_SIZE;
//        int yCur = y/SCALED_SIZE;
//        boolean result = false;
//        if (right) {
//            if (y%SCALED_SIZE == 0) {
//                if (scene[yCur][xCur+1] == 0) {
//                }
//                else result = true;
//            }
//            else result = true;
//        }
//        if (up) {
//            if (x%SCALED_SIZE == 0) {
//                if (y%SCALED_SIZE == 0) {
//                    if (scene[yCur - 1][xCur] == 0) {
//                    }
//                    else result = true;
//                } else {
//                    if (scene[yCur][xCur] == 0) {
//                    }
//                    else result = true;
//                }
//            }
//            else result = true;
//        }
//        if (down) {
//            if (x%SCALED_SIZE == 0) {
//                if (scene[yCur+1][xCur] == 0) {
//                }
//                else result = true;
//            }
//            else result = true;
//        }
//        if (left) {
//            if (y%SCALED_SIZE == 0) {
//                if (x%SCALED_SIZE == 0) {
//                    if (scene[yCur][xCur-1] == 0){}
//                    else result = true;
//                } else {
//                    if (scene[yCur][xCur] == 0){}
//                    else result = true;
//                }
//            }
//            else result = true;
//        }
//        return result;
//    }
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
}
