import entities.AnimatedEntities.AnimatedEntity;
import entities.AnimatedEntities.Characters.Bomberman;
import entities.AnimatedEntities.Characters.Enemies.Balloon;
import entities.AnimatedEntities.Characters.Enemies.Ghost;
import entities.AnimatedEntities.Characters.Enemies.Oreal;
import entities.AnimatedEntities.Tiles.Items.BombItem;
import entities.AnimatedEntities.Tiles.SoftWall;
import entities.AnimatedEntities.Weapons.Bomb.Bomb;
import entities.AnimatedEntities.Weapons.Bomb.BombFlame;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import entities.Entity;
import entities.AnimatedEntities.Tiles.Path;
import entities.AnimatedEntities.Tiles.Wall;
import graphics.Sprite;
import Database.Database.*;
import entities.AnimatedEntities.Weapons.Bomb.Bomb;

import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import static Database.Database.*;
import static Input.KeyHandle.click;
import static Input.KeyHandle.placeBomb;
import static entities.Map.scene;
import static graphics.Sprite.*;

public class Main extends Application {

    private GraphicsContext gc;
    private Canvas canvas;

    public boolean checkBombFlame;

    public int deadCount = 0;

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
        scene.setFill(Color.LIME);
        keyHandle.checkKey(scene);
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                long firstTime = System.currentTimeMillis();
                render();
                update(stage);
                afterUpdate();
                //Check time diff to place cd time.
                long diff = System.currentTimeMillis() - firstTime;
                timeCount++;
                long temp = System.currentTimeMillis() - start;
                if (temp/1000 == sec) {
                    if(sec!=0) stage.setTitle("Bomberman, fps=" + (timeCount-preTimeCount));
                    sec++;
                    preTimeCount = timeCount;
                }
                try {
                    Thread.sleep(1000/fps - diff-2);
                } catch (Exception e) {
                    System.out.println(e);
                }
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
                        scene[i][j] = 4;
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
                    object = new SoftWall(j, i, Sprite.brick.getFxImage());
                } else if (scene[i][j] == 4 && numOfBombItem <=3) {
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
        if (bomber.dead) {
            bomber.update();
        }
        else {
            for (int i = 0; i < entities.size(); i++) {
                Entity temp = entities.get(i);
                temp.update();
                if (!entities.contains(temp)) i--;
            }
            for (int i = 0; i < stillObjects.size(); i++) {
                Entity temp = stillObjects.get(i);
                temp.update();
                if (!stillObjects.contains(temp)) {
                    i--;
                }
            }
            bombSetup();
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g->g.render(gc));
        entities.forEach(g -> g.render(gc));
        bombList.forEach(g -> g.render(gc));
        if (!click) menuObj.render(gc);
        if (bomber.dead && gameOver) {
            endObj.render(gc);
        }
    }

    public void createMonsters() {
        Random randomGen = new Random();
        AnimatedEntity object;
        for (int i = 0; i < 3 ; i++) {
            while(true) {
                int ranx = randomGen.nextInt(18);
                int rany = randomGen.nextInt(13);
                if ((ranx >=2 || rany >=2) && scene[rany+1][ranx+1] == 0) {
                    object = new Oreal(ranx+1, rany+1, oneal_left1.getFxImage(), i+1);
                    entities.add(object);
                    break;
                }
            }
        }
    }


    public void bombSetup() {
        int x1 = (bomber.getX() + 16) / SCALED_SIZE;
        int y1 = (bomber.getY() + 16) / SCALED_SIZE;
        if (placeBomb && bombList.size() < maxBomb && scene[y1][x1] != 3) {
            Bomb bomb = new Bomb(x1, y1, Sprite.bomb_0.getFxImage(), bombList.size() * 100 + 30);
            scene[y1][x1] = 3;
            //moveEnermyBack(x1 * SCALED_SIZE, y1 * SCALED_SIZE);
            bomb.timer.schedule(bomb.task, 4000);
            bombList.add(bomb);
        }
        else try {
            for (int i = 0;i<bombList.size();i++) {
                Bomb temp = bombList.get(i);
                temp.update();
                if (!bombList.contains(temp)) {
                    i--;
                }
            }
        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }


    public void afterUpdate() {
        entities.forEach(g -> g.checkDead());
    }

    public void moveEnermyBack(int bombX, int bombY) {
        for (AnimatedEntity i : entities) {
            if (i.getId() != bomber.getId()) {
                if (i.getX() - bombX < 32 && i.getX() - bombX > 0 && i.getY() == bombY ) {
                    i.setX(bombX + 32);
                }
                else if (bombX - i.getX() < 32 && bombX - i.getX() > 0 && i.getY() == bombY) {
                    i.setX(bombX - 32);
                }
                else if (i.getY() - bombY < 32 && i.getY() - bombY > 0 && i.getX() == bombX) {
                    i.setY(bombY + 32);
                }
                else if (bombY - i.getY() < 32 && bombY - i.getY() > 0 && i.getX() == bombX) {
                    i.setY(bombY - 32);
                }
            }
        }
    }
}
