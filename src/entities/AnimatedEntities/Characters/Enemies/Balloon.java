package entities.AnimatedEntities.Characters.Enemies;

import AI.AIEnemies.SuperDumbAI;
import javafx.scene.image.Image;
import static entities.Map.scene;
import static graphics.Sprite.*;

public class Balloon extends Enemy{
    private int direction = 1;
    private int temp;
    SuperDumbAI balloonAI = new SuperDumbAI();

    public Balloon(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        if (x%SCALED_SIZE == 0 && y%SCALED_SIZE == 0 && ((x/SCALED_SIZE)%2 == 1 && (y/SCALED_SIZE)%2 == 1 )) {
            direction = balloonAI.chooseDirection(direction);
        }
        temp = direction;
        while (true) {
            if (direction == 1) {
                if (isFree(x+1,y)) {
                    this.x++;
                    break;
                } else {
                    direction = 2;
                }

                //this.x++;
            } else if (direction == 2) {

                if (isFree(x,y+1)) {
                    this.y++;
                    break;
                } else {
                    direction = 3;
                }

                //this.y++;
            } else if (direction == 3) {

                if (isFree(x-1,y)) {
                    this.x--;
                    break;
                } else {
                    direction = 0;
                }

                //this.x--;
            } else if (direction == 0){
                if (isFree(x,y-1)) {
                    this.y--;
                    break;
                } else {
                    direction = 1;
                }

                //this.y--;
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

}