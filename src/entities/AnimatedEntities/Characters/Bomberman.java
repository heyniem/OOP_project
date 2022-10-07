package entities.AnimatedEntities.Characters;

import entities.AnimatedEntities.AnimatedEntity;
import static Database.Database.entities;
import javafx.scene.image.Image;

import java.util.List;

import static Input.KeyHandle.*;
import static graphics.Sprite.SCALED_SIZE;
import static entities.Map.scene;

public class Bomberman extends Character{
    private final int speed = 2;
    private int tempX, tempY;
    public Bomberman(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
    }
    @Override
    public void update() {
        boolean temp1 = false, temp2 = false;
        if (up && down) {
            temp2 = true;
            up = false;
            down = false;
        }
        if (left && right) {
            temp1 = true;
            left = false;
            right = false;
        }
        if (up) {
            if (x%SCALED_SIZE == 0 && isFree(x,y-speed) && !isCollide(entities, x, y-speed)) y-=speed;
            else {
                if (x%SCALED_SIZE <15) {
                    tempX = x-x%SCALED_SIZE;
                    //System.out.println(tempX + " " + y);
                    if (isFree(tempX,y-speed) && !isCollide(entities, x-speed, y)) x-=speed;
                }
                else if (x%SCALED_SIZE > 17) {
                    tempX = x-x%SCALED_SIZE + 32;
                    //System.out.println(tempX + " " + y + "100");
                    if (isFree(tempX, y-speed) && !isCollide(entities, x+speed, y)) x+=speed;
                }
            }
        }
        if (down) {
            if (x%SCALED_SIZE == 0 && isFree(x,y+speed) && !isCollide(entities, x, y+speed)) y+=speed;
            else {
                if (x%SCALED_SIZE <15) {
                    tempX = x-x%SCALED_SIZE;
                    //System.out.println(tempX + " " + y);
                    if (isFree(tempX,y+speed) && !isCollide(entities, x-speed, y)) x-=speed;
                }
                else if (x%SCALED_SIZE > 17) {
                    tempX = x-x%SCALED_SIZE + 32;
                    //System.out.println(tempX + " " + y + "100");
                    if (isFree(tempX, y+speed) && !isCollide(entities, x+speed, y)) x+=speed;
                }
            }
        }
        if (right) {
            if (y%SCALED_SIZE == 0 && isFree(x+speed,y) && !isCollide(entities, x+speed, y)) x+=speed;
            else {
                if (y%SCALED_SIZE <15) {
                    tempY = y-y%SCALED_SIZE;
                    if (isFree(x+speed,tempY) && !isCollide(entities, x, y-speed)) y-=speed;
                }
                else if (y%SCALED_SIZE > 17) {
                    tempY = y-y%SCALED_SIZE + 32;
                    if (isFree(x+speed, tempY) && !isCollide(entities, x, y+speed)) y+=speed;
                }
            }
        }
        if (left) {
            if (y%SCALED_SIZE == 0 && isFree(x-speed,y) && !isCollide(entities, x-speed, y)) x-=speed;
            else {
                if (y%SCALED_SIZE <15) {
                    tempY = y-y%SCALED_SIZE;
                    if (isFree(x-speed,tempY) && !isCollide(entities, x, y-speed)) y-=speed;
                }
                else if (y%SCALED_SIZE > 17) {
                    tempY = y-y%SCALED_SIZE + 32;
                    if (isFree(x-speed, tempY) && !isCollide(entities, x, y-speed)) y+=speed;
                }
            }
        }
        if (temp1) {
            right = true;
            left = true;
        }
        if (temp2) {
            up = true;
            down = true;
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
            if (i.getId() != this.id){
                tempX = i.getX();
                tempY = i.getY();
                if (Math.abs(tempX - newX) < 32 && Math.abs(tempY - newY) < 32) {
                    System.out.println("True");
                    return true;
                }
            }
        }
        return false;
    }


}
