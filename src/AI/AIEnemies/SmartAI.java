package AI.AIEnemies;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static Database.Database.*;
import static entities.Map.mapToChar;
import static entities.Map.scene;
import static graphics.Sprite.SCALED_SIZE;

public class SmartAI extends AIEnemy{

    SuperDumbAI superDumbAI = new SuperDumbAI();
    public int chooseDirection(int monsterX, int monsterY, int currentDirection, int chance) {
        Character[][] matrix = mapToChar();
        //print matrix
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        int check = pathExists(matrix, monsterX, monsterY);
        System.out.println(check);
        if (check != -1) {
            int tempX = monsterX/SCALED_SIZE;
            int tempY = monsterY/SCALED_SIZE;
            if (matrix[tempY - 1][tempX] != '0' && pathExists(matrix, monsterX, monsterY-SCALED_SIZE) == check - 1) return 0;
            else if (matrix[tempY + 1][tempX] != '0' && pathExists(matrix, monsterX, monsterY+SCALED_SIZE) == check - 1) return 2;
            else if (matrix[tempY][tempX - 1] != '0' && pathExists(matrix, monsterX-SCALED_SIZE, monsterY) == check - 1) return 3;
            else if (matrix[tempY][tempX + 1] != '0' && pathExists(matrix, monsterX+SCALED_SIZE, monsterY) == check - 1) return 1;
            else return currentDirection;
        }
        else return superDumbAI.chooseDirection(currentDirection, chance);
    }

    private static int pathExists(Character[][] matrixx, int monX, int monY) {
        Character[][] matrix = new Character[HEIGHT][WIDTH]; //copy matrix
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = matrixx[i][j];
            }
        }
        matrix[monY/SCALED_SIZE][monX/SCALED_SIZE] = 'P';



        Node source = new Node(monY/SCALED_SIZE, monX/SCALED_SIZE, 0);
        Queue<Node> queue = new LinkedList<Node>();

        int numOfRows = matrix.length;
        int numOfColumns = matrix[0].length;

        queue.add(source);

        while(!queue.isEmpty()) {
            Node poped = queue.poll();

            if(matrix[poped.x][poped.y] == 'D' ) {
                return poped.distanceFromSource;
            }
            else {
                matrix[poped.x][poped.y]='0';

                List<Node> neighbourList = addNeighbours(poped, matrix, numOfRows, numOfColumns);
                queue.addAll(neighbourList);
            }
        }
        return -1;
    }

    private static List<Node> addNeighbours(Node poped, Character[][] matrix, final int numOfRows, final int numOfColumns) {

        List<Node> list = new LinkedList<Node>();

        if((poped.x-1 >= 0 && poped.x-1 < numOfRows) && matrix[poped.x-1][poped.y] != '0') {
            list.add(new Node(poped.x-1, poped.y, poped.distanceFromSource+1));
        }
        if((poped.x+1 >= 0 && poped.x+1 < numOfRows) && matrix[poped.x+1][poped.y] != '0') {
            list.add(new Node(poped.x+1, poped.y, poped.distanceFromSource+1));
        }
        if((poped.y-1 >= 0 && poped.y-1 < numOfColumns) && matrix[poped.x][poped.y-1] != '0') {
            list.add(new Node(poped.x, poped.y-1, poped.distanceFromSource+1));
        }
        if((poped.y+1 >= 0 && poped.y+1 < numOfColumns) && matrix[poped.x][poped.y+1] != '0') {
            list.add(new Node(poped.x, poped.y+1, poped.distanceFromSource+1));
        }
        return list;
    }
}
class Node {
    int x;
    int y;
    int distanceFromSource;

    Node(int x, int y, int dis) {
        this.x = x;
        this.y = y;
        this.distanceFromSource = dis;
    }
}
