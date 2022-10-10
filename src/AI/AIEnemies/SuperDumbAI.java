package AI.AIEnemies;

import java.util.Random;

public class SuperDumbAI extends AIEnemy {
    public int chooseDirection(int currentDirection, int chance) {

        Random random = new Random();
        int temp = random.nextInt(100);
        if (temp < chance) return currentDirection;
        else if (temp < chance + (100 - chance) / 3) return (currentDirection + 1) % 4;
        else if (temp < chance + (100 - chance) / 3 * 2) return (currentDirection + 2) % 4;
        else return (currentDirection + 3) % 4;
    }
}
