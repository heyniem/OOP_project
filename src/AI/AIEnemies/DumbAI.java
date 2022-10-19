package AI.AIEnemies;

import static Database.Database.bomber;
import static graphics.Sprite.SCALED_SIZE;
import static entities.Map.scene;

public class DumbAI extends AIEnemy{
    public int chooseDirection(int monsterX,int monsterY, int currentDirection, int chance) {
        int tempX = monsterX/SCALED_SIZE;
        int tempY = monsterY/SCALED_SIZE;
        int bombX = bomber.getX()/SCALED_SIZE;
        int bombY = bomber.getY()/SCALED_SIZE;

        if (Math.abs(tempX - bombX) > Math.abs(tempY - bombY)) {
            if (scene[tempY - 1][tempX] == 0 && Math.abs(tempY - 1 - bombY) < Math.abs(tempY - bombY)) {
                System.out.println("up");
                return 0;
            }
            else if (scene[tempY + 1][tempX] == 0 && Math.abs(tempY + 1 - bombY) < Math.abs(tempY - bombY)) {
                System.out.println("down");
                return 2;
            }
            else if (scene[tempY][tempX - 1] == 0 && Math.abs(tempX - 1 - bombX) < Math.abs(tempX - bombX)) {
                System.out.println("left");
                return 3;
            }
            else if (scene[tempY][tempX + 1] == 0 && Math.abs(tempX + 1 - bombX) < Math.abs(tempX - bombX)) {
                System.out.println("right");
                return 1;
            }
            else return currentDirection;
        }
        else {
            if (scene[tempY][tempX - 1] == 0 && Math.abs(tempX - 1 - bombX) < Math.abs(tempX - bombX)) {
                System.out.println("left 2");
                return 3;
            }
            else if (scene[tempY][tempX + 1] == 0 && Math.abs(tempX + 1 - bombX) < Math.abs(tempX - bombX)) {
                System.out.println("right 2");
                return 1;
            }
            else if (scene[tempY - 1][tempX] == 0 && Math.abs(tempY - 1 - bombY) < Math.abs(tempY - bombY)) {
                System.out.println("up 2");
                return 0;
            }
            else if (scene[tempY + 1][tempX] == 0 && Math.abs(tempY + 1 - bombY) < Math.abs(tempY - bombY)) {
                System.out.println("down 2");
                return 2;
            }
            else return currentDirection;
        }
    }
}
