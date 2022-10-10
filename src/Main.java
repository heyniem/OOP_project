import entities.AnimatedEntities.AnimatedEntity;
import entities.AnimatedEntities.Characters.Enemies.Balloon;
import entities.AnimatedEntities.Characters.Enemies.Ghost;
import entities.AnimatedEntities.Tiles.Items.BombItem;
import entities.AnimatedEntities.Weapons.Bomb.Bomb;
import entities.AnimatedEntities.Weapons.Bomb.BombFlame;
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

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static Database.Database.*;
import static Input.KeyHandle.placeBomb;
import static entities.Map.scene;
import static graphics.Sprite.*;

public class Main extends Application {

    private GraphicsContext gc;
    private Canvas canvas;

    public boolean checkBombExplode;
    public boolean checkBombFlame;

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
        bombSetup();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g->g.render(gc));
        entities.forEach(g -> g.render(gc));
        bombSetup.forEach(g -> g.render(gc));
        bombExplode.forEach(g -> g.render(gc));
        bombFlame.forEach(g -> g.render(gc));
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

    public void bombSetup() {
        int x1 = (bomber.getX() + 16) / SCALED_SIZE;
        int y1 = (bomber.getY() + 16) / SCALED_SIZE;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                bombSetup.clear();
                checkBombExplode = true;
            }
        };

        if (placeBomb && bombSetup.size() < 1) {
            bomb = new Bomb(x1, y1, Sprite.bomb_0.getFxImage(), 1);
            bombSetup.add(bomb);
            timer.schedule(task, 4000);
        }

        if (bomb != null) {

            frameBomb++;
            if (frameBomb > intervalBomb) {
                frameBomb = 0;
                indexBomb++;
                if (indexBomb > intervalBomb) {
                    indexBomb = 0;
                }
            }
            if (indexBomb == 0) {
                bomb.setImg(Sprite.bomb_0.getFxImage());
            }
            if (indexBomb == 1) {
                bomb.setImg(bomb_1.getFxImage());
            }
            if (indexBomb == 2) {
                bomb.setImg(bomb_2.getFxImage());
            }
            if (indexBomb == 3) {
                bomb.setImg(Sprite.bomb_0.getFxImage());
            }
            if (indexBomb == 4) {
                bomb.setImg(bomb_1.getFxImage());
            }
            if (indexBomb == 5) {
                bomb.setImg(bomb_2.getFxImage());
            }

        }
        if (checkBombExplode) {
            bombExplode();
            checkBombCollide();
        }
        FlameSegments();
    }

    public void bombExplode() {
        if (checkBombExplode) {
            bomb_explode = new BombFlame(bomb.getX() / SCALED_SIZE,bomb.getY() / SCALED_SIZE, Sprite.bomb_exploded.getFxImage(), 1);
            bombExplode.add(bomb_explode);
            if (bomb_explode != null) {
                indexBombExplode++;
                if (indexBombExplode == 0) bomb_explode.setImg(bomb_exploded.getFxImage());

                if (indexBombExplode == 1) bomb_explode.setImg(bomb_exploded.getFxImage());

                if (indexBombExplode == 2) bomb_explode.setImg(bomb_exploded.getFxImage());

                if (indexBombExplode == 3) bomb_explode.setImg(bomb_exploded.getFxImage());

                if (indexBombExplode == 4) bomb_explode.setImg(bomb_exploded1.getFxImage());

                if (indexBombExplode == 5) bomb_explode.setImg(bomb_exploded1.getFxImage());

                if (indexBombExplode == 6) bomb_explode.setImg(bomb_exploded1.getFxImage());

                if (indexBombExplode == 7) bomb_explode.setImg(bomb_exploded1.getFxImage());

                if (indexBombExplode == 8) bomb_explode.setImg(bomb_exploded2.getFxImage());

                if(indexBombExplode == 9) bomb_explode.setImg(bomb_exploded2.getFxImage());

                if(indexBombExplode == 10) bomb_explode.setImg(bomb_exploded2.getFxImage());

                if(indexBombExplode == 11) bomb_explode.setImg(bomb_exploded2.getFxImage());

                if (indexBombExplode > intervalExplode) {
                    checkBombExplode = false;
                    bombExplode.clear();
                    bomb_explode = null;
                    indexBombExplode = 0;
                    checkBombFlame = true;
                }
                BrickDetection();
            }
        }
    }

    public void BrickDetection() {
        int tempX = bomb.getX() / SCALED_SIZE, tempY = bomb.getY() / SCALED_SIZE;
        if (scene[tempY][tempX + 1] == 2) {
            brick_detection_right = true;
            System.out.println("a brick to the right");
        } else brick_detection_right = false;

        if (scene[tempY][tempX - 1] == 2) {
            brick_detection_left = true;
            System.out.println("a brick to the left");
        } else brick_detection_left = false;

        if (scene[tempY - 1][tempX] == 2) {
            brick_detection_up = true;
            System.out.println("a brick to the up");
        } else brick_detection_up = false;

        if (scene[tempY + 1][tempX] == 2) {
            brick_detection_down = true;
            System.out.println("a brick to the down");
        } else brick_detection_down = false;
    }


    public void FlameSegments() {
        if (checkBombFlame) {
            bomb_flame_horizontal_left = new BombFlame((bomb.getX() / SCALED_SIZE) - 1,(bomb.getY() / SCALED_SIZE),explosion_horizontal_left_last2.getFxImage(),1 );
            bomb_flame_horizontal_right = new BombFlame(bomb.getX() / SCALED_SIZE + 1, bomb.getY() / SCALED_SIZE, explosion_horizontal_right_last2.getFxImage(),2);
            bomb_flame_vertical_up = new BombFlame(bomb.getX() / SCALED_SIZE, bomb.getY() / SCALED_SIZE - 1, explosion_vertical_top_last2.getFxImage(), 3);
            bomb_flame_vertical_down = new BombFlame(bomb.getX() / SCALED_SIZE, bomb.getY() / SCALED_SIZE + 1, explosion_vertical_down_last2.getFxImage(), 4);
            getBomb_flame_center = new BombFlame((bomb.getX() / SCALED_SIZE),(bomb.getY() / SCALED_SIZE), bomb_exploded2.getFxImage(),5);

            bombFlame.add(bomb_flame_horizontal_left);
            bombFlame.add(bomb_flame_horizontal_right);
            bombFlame.add(bomb_flame_vertical_up);
            bombFlame.add(bomb_flame_vertical_down);
            bombFlame.add(getBomb_flame_center);



            if (indexFlameExpansion == 0) {
                bomb_flame_horizontal_left.setImg(explosion_horizontal_left_last2.getFxImage());
                bomb_flame_horizontal_right.setImg(explosion_horizontal_right_last2.getFxImage());
                bomb_flame_vertical_up.setImg(explosion_vertical_top_last2.getFxImage());
                bomb_flame_vertical_down.setImg(explosion_vertical_down_last2.getFxImage());
                getBomb_flame_center.setImg(bomb_exploded2.getFxImage());
            }

            indexFlameExpansion++;

            if (indexFlameExpansion == 1) {
                bomb_flame_horizontal_left.setImg(explosion_horizontal_left_last2.getFxImage());
                bomb_flame_horizontal_right.setImg(explosion_horizontal_right_last2.getFxImage());
                bomb_flame_vertical_up.setImg(explosion_vertical_top_last2.getFxImage());
                bomb_flame_vertical_down.setImg(explosion_vertical_down_last2.getFxImage());
                getBomb_flame_center.setImg(bomb_exploded2.getFxImage());
            }
            if (indexFlameExpansion == 2) {
                bomb_flame_horizontal_left.setImg(explosion_horizontal_left_last2.getFxImage());
                bomb_flame_horizontal_right.setImg(explosion_horizontal_right_last2.getFxImage());
                bomb_flame_vertical_up.setImg(explosion_vertical_top_last2.getFxImage());
                bomb_flame_vertical_down.setImg(explosion_vertical_down_last2.getFxImage());
                getBomb_flame_center.setImg(bomb_exploded2.getFxImage());
            }

            if (indexFlameExpansion == 3) {
                bomb_flame_horizontal_left.setImg(explosion_horizontal_left_last1.getFxImage());
                bomb_flame_horizontal_right.setImg(explosion_horizontal_right_last1.getFxImage());
                bomb_flame_vertical_up.setImg(explosion_vertical_top_last1.getFxImage());
                bomb_flame_vertical_down.setImg(explosion_vertical_down_last1.getFxImage());
                getBomb_flame_center.setImg(bomb_exploded1.getFxImage());
            }
            if (indexFlameExpansion == 4) {
                bomb_flame_horizontal_left.setImg(explosion_horizontal_left_last1.getFxImage());
                bomb_flame_horizontal_right.setImg(explosion_horizontal_right_last1.getFxImage());
                bomb_flame_vertical_up.setImg(explosion_vertical_top_last1.getFxImage());
                bomb_flame_vertical_down.setImg(explosion_vertical_down_last1.getFxImage());
                getBomb_flame_center.setImg(bomb_exploded1.getFxImage());
            }
            if (indexFlameExpansion == 5) {
                bomb_flame_horizontal_left.setImg(explosion_horizontal_left_last1.getFxImage());
                bomb_flame_horizontal_right.setImg(explosion_horizontal_right_last1.getFxImage());
                bomb_flame_vertical_up.setImg(explosion_vertical_top_last1.getFxImage());
                bomb_flame_vertical_down.setImg(explosion_vertical_down_last1.getFxImage());
                getBomb_flame_center.setImg(bomb_exploded1.getFxImage());
            }
            if (indexFlameExpansion == 6) {
                bomb_flame_horizontal_left.setImg(explosion_horizontal_left_last.getFxImage());
                bomb_flame_horizontal_right.setImg(explosion_horizontal_right_last.getFxImage());
                bomb_flame_vertical_up.setImg(explosion_vertical_top_last.getFxImage());
                bomb_flame_vertical_down.setImg(explosion_vertical_down_last.getFxImage());
                getBomb_flame_center.setImg(bomb_exploded.getFxImage());
            }
            if (indexFlameExpansion == 7) {
                bomb_flame_horizontal_left.setImg(explosion_horizontal_left_last.getFxImage());
                bomb_flame_horizontal_right.setImg(explosion_horizontal_right_last.getFxImage());
                bomb_flame_vertical_up.setImg(explosion_vertical_top_last.getFxImage());
                bomb_flame_vertical_down.setImg(explosion_vertical_down_last.getFxImage());
                getBomb_flame_center.setImg(bomb_exploded.getFxImage());
            }
            if (indexFlameExpansion == 8) {
                bomb_flame_horizontal_left.setImg(explosion_horizontal_left_last.getFxImage());
                bomb_flame_horizontal_right.setImg(explosion_horizontal_right_last.getFxImage());
                bomb_flame_vertical_up.setImg(explosion_vertical_top_last.getFxImage());
                bomb_flame_vertical_down.setImg(explosion_vertical_down_last.getFxImage());
                getBomb_flame_center.setImg(bomb_exploded.getFxImage());
            }
            if (indexFlameExpansion > intervalFlameExpansion) {
                checkBombFlame = false;
                bombFlame.clear();
                indexFlameExpansion = 0;
            }
        }
    }

    public boolean brick_detection_right;
    public boolean brick_detection_left;
    public boolean brick_detection_up;
    public boolean brick_detection_down;

    public void checkBombCollide() {
        int tempX = bomb.getX() / SCALED_SIZE, tempY = bomb.getY() / SCALED_SIZE;
        if (brick_detection_right) {
            scene[tempY][tempX + 1] = 0;
            Entity object;
            object = new Path(tempX + 1, tempY, grass.getFxImage());
            stillObjects.add(object);
        }
        if (brick_detection_left) {
            scene[tempY][tempX - 1] = 0;
            Entity object;
            object = new Path(tempX - 1, tempY, grass.getFxImage());
            stillObjects.add(object);

        }
        if (brick_detection_down) {
            scene[tempY + 1][tempX] = 0;
            Entity object;
            object = new Path(tempX, tempY + 1, grass.getFxImage());
            stillObjects.add(object);
        }
        if (brick_detection_up) {
            scene[tempY - 1][tempX] = 0;
            Entity object;
            object = new Path(tempX, tempY - 1, grass.getFxImage());
            stillObjects.add(object);
        }
    }
}
