package AI.AIEnemies;

import java.util.Random;

public class SuperDumbAI extends AIEnemy{
    public int chooseDirection(int currentDirection) {
        //return (currentDirection+1)%4;

        Random random = new Random();
        int temp = random.nextInt(100);
        if(temp<64) return currentDirection;
        else if (temp<76) return (currentDirection+1)%4;
        else if (temp<88) return (currentDirection+2)%4;
        else return (currentDirection+3)%4;

    }
}
