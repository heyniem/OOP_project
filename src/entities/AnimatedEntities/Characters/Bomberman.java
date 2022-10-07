package entities.AnimatedEntities.Characters;

import javafx.scene.image.Image;

import static Input.KeyHandle.*;
import static graphics.Sprite.SCALED_SIZE;
import static entities.Map.scene;

public class Bomberman extends Character{
    private final int speed = 2;
    private int tempX, tempY;
    public Bomberman(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (up) {
            if (x%SCALED_SIZE == 0 && isFree(x,y-speed)) y-=speed;
            else {
                if (x%SCALED_SIZE <15) {
                    tempX = x-x%SCALED_SIZE;
                    //System.out.println(tempX + " " + y);
                    if (isFree(tempX,y-speed)) x-=speed;
                }
                else if (x%SCALED_SIZE > 17) {
                    tempX = x-x%SCALED_SIZE + 32;
                    //System.out.println(tempX + " " + y + "100");
                    if (isFree(tempX, y-speed)) x+=speed;
                }
            }
        }
        if (down) {
            if (x%SCALED_SIZE == 0 && isFree(x,y+speed)) y+=speed;
            else {
                if (x%SCALED_SIZE <15) {
                    tempX = x-x%SCALED_SIZE;
                    //System.out.println(tempX + " " + y);
                    if (isFree(tempX,y+speed)) x-=speed;
                }
                else if (x%SCALED_SIZE > 17) {
                    tempX = x-x%SCALED_SIZE + 32;
                    //System.out.println(tempX + " " + y + "100");
                    if (isFree(tempX, y+speed)) x+=speed;
                }
            }
        }
        if (right) {
            if (y%SCALED_SIZE == 0 && isFree(x+speed,y)) x+=speed;
            else {
                if (y%SCALED_SIZE <15) {
                    tempY = y-y%SCALED_SIZE;
                    if (isFree(x+speed,tempY)) y-=speed;
                }
                else if (y%SCALED_SIZE > 17) {
                    tempY = y-y%SCALED_SIZE + 32;
                    if (isFree(x+speed, tempY)) y+=speed;
                }
            }
        }
        if (left) {
            if (y%SCALED_SIZE == 0 && isFree(x-speed,y)) x-=speed;
            else {
                if (y%SCALED_SIZE <15) {
                    tempY = y-y%SCALED_SIZE;
                    if (isFree(x-speed,tempY)) y-=speed;
                }
                else if (y%SCALED_SIZE > 17) {
                    tempY = y-y%SCALED_SIZE + 32;
                    if (isFree(x-speed, tempY)) y+=speed;
                }
            }
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
}
