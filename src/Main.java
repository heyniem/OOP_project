import entities.AnimatedEntities.AnimatedEntity;
import entities.AnimatedEntities.Characters.Enemies.Balloon;
import entities.AnimatedEntities.Characters.Enemies.Ghost;
import entities.AnimatedEntities.Tiles.Items.BombItem;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import entities.Entity;
import entities.AnimatedEntities.Tiles.Path;
import entities.AnimatedEntities.Tiles.Wall;
import graphics.Sprite;
import static Database.Database.*;

import java.util.Random;

import static entities.Map.scene;

public class Main extends Application {

    private GraphicsContext gc;
    private Canvas canvas;

    private final long fps = 60;

    private long timeCount = 0,preTimeCount = 0, sec = 0, start = System.currentTimeMillis();


    public static void main(String[] args) {
        Application.launch(Main.class);
    }

    @Override
    public void start(Stage stage) {
        // Create Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Create root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Create scene
        Scene scene = new Scene(root);
        keyHandle.checkKey(scene);
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update(stage);
                checkDead();
            }
        };
        timer.start();

        createMap();
        createMonsters();
    }

    public void createMap() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (scene[i][j] == 0) {
                    int ran = new Random().nextInt(15);
                    if (ran <= 1) {
                        scene[i][j] = 2;
                    } else if (ran == 2){
                        scene[i][j] = 3;
                    }
                }
            }
        }
        scene[1][1] = 0;
        scene[2][1] = 2;
        scene[1][2] = 0;

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object;
                if (scene[i][j] == 1) {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                } else if (scene[i][j] == 2) {
                    object = new Wall(j, i, Sprite.brick.getFxImage());
                } else if (scene[i][j] == 3 && numOfBombItem <=3) {
                    numOfBombItem++;
                    object = new BombItem(j, i, Sprite.powerup_bombs.getFxImage());
                } else {
                    object = new Path(j, i, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
        entities.add(bomber);
    }

    public void update(Stage stage) {
        long firstTime = System.currentTimeMillis();
        for (Entity i : entities) {
            i.update();
        }
        long diff = System.currentTimeMillis() - firstTime;
        timeCount++;
        long temp = System.currentTimeMillis() - start;
        if (temp/1000 == sec) {
            if(sec!=0) stage.setTitle("Bomberman, fps=" + (timeCount-preTimeCount));
            sec++;
            preTimeCount = timeCount;
        }
        try {
            Thread.sleep(1000/fps - diff-1);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g->g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public void createMonsters() {
        Random randomGen = new Random();
        AnimatedEntity object;
        for (int i = 0; i < 5; i++) {
            while(true) {
                int ranx = randomGen.nextInt(18);
                int rany = randomGen.nextInt(13);
                if ((ranx >=2 || rany >=2) && scene[rany+1][ranx+1] == 0) {
                    object = new Ghost(ranx+1, rany+1, Sprite.ghost_left1.getFxImage(), i+1);
                    entities.add(object);
                    break;
                }
            }
        }
    }

    public void checkDead() {

    }
}
