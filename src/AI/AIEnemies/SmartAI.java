package AI.AIEnemies;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class SmartAI extends AIEnemy{


    public static void main(String args[])
    {
        char[][] matrix = {
                {'S', '0', 'D', '1'},
                {'1', '1', '0', '1'},
                {'0', '0', '1', '1'},
                {'1', '0', '1', '1'}
        };

        int path = pathExists(matrix);

        System.out.println(path);
    }

    private static int pathExists(char[][] matrix) {

        Node source = new Node(0, 0, 0);
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

    private static List<Node> addNeighbours(Node poped, char[][] matrix, final int numOfRows, final int numOfColumns) {

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
