import entities.AnimatedEntities.AnimatedEntity;
import entities.AnimatedEntities.Characters.Enemies.Oreal;
import entities.AnimatedEntities.Tiles.Items.*;
import entities.AnimatedEntities.Tiles.Portal;
import entities.AnimatedEntities.Tiles.SoftWall;
import entities.AnimatedEntities.Weapons.Bomb.Bomb;
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

import java.util.Random;

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
        Random randomGen = new Random();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (scene[i][j] == 0) {
                    int ran = new Random().nextInt(15);
                    if (ran <= 7) {
                        scene[i][j] = 2;
                    }
                }
            }
        }

        scene[1][1] = 0;
        scene[2][1] = 0;
        scene[1][2] = 0;
        for (int i = 0;i < 2;i++) {
            while(true) {
                int x = randomGen.nextInt(WIDTH - 2) + 1;
                int y = randomGen.nextInt(HEIGHT - 2) + 1;
                boolean check = true;
                for (Item entity  : ItemList) {
                    if (entity.getX() == x * SCALED_SIZE && entity.getY() == y * SCALED_SIZE) {
                        check = false;
                        break;
                    }
                }
                if (scene[y][x] == 2 && check) {
                    Item bombItem = new BombItem(x, y, powerup_bombs.getFxImage());
                    ItemList.add(bombItem);
                    break;
                }
            }
        }
        //System.out.println("Step 1 done");
        for (int i = 0;i < 5;i++) {
            while(true) {
                int x = randomGen.nextInt(WIDTH - 2) + 1;
                int y = randomGen.nextInt(HEIGHT - 2) + 1;
                boolean check = true;
                for (Item entity : ItemList) {
                    if (entity.getX() == x * SCALED_SIZE && entity.getY() == y * SCALED_SIZE) {
                        check = false;
                        break;
                    }
                }
                if (scene[y][x] == 2 && check) {
                    Item flameItem = new FlameItem(x, y, powerup_flames.getFxImage());
                    ItemList.add(flameItem);
                    break;
                }
            }
        }

        for (int i = 0;i < 2;i++) {
            while(true) {
                int x = randomGen.nextInt(WIDTH - 2) + 1;
                int y = randomGen.nextInt(HEIGHT - 2) + 1;
                boolean check = true;
                for (Entity entity : ItemList) {
                    if (entity.getX() == x * SCALED_SIZE && entity.getY() == y * SCALED_SIZE) {
                        check = false;
                        break;
                    }
                }
                if (scene[y][x] == 2 && check) {
                    Item speedItem = new SpeedItem(x, y, powerup_speed.getFxImage());
                    ItemList.add(speedItem);
                    break;
                }
            }
        }
//        //Create Portal
//        while(true) {
//            int x = randomGen.nextInt(WIDTH - 2) + 1;
//            int y = randomGen.nextInt(HEIGHT - 2) + 1;
//            boolean check = true;
//            for (Item entity  : ItemList) {
//                if (entity.getX() == x * SCALED_SIZE && entity.getY() == y * SCALED_SIZE) {
//                    check = false;
//                    break;
//                }
//            }
//            if (scene[y][x] == 2 && check) {
//                gamePortal.setX(x * SCALED_SIZE);
//                gamePortal.setY(y * SCALED_SIZE);
//                break;
//            }
//        }
        //Print item list
        for (Item entity : ItemList) {
            System.out.println(entity.getY()/SCALED_SIZE + " - " +  entity.getX()/SCALED_SIZE + " - " + entity.getClass());
        }
        System.out.println(gamePortal.getY()/SCALED_SIZE + " - " +  gamePortal.getX()/SCALED_SIZE);


        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object;
                if (scene[i][j] == 1) {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                } else if (scene[i][j] == 2) {
                    object = new SoftWall(j, i, Sprite.brick.getFxImage());
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
        else if (!win) {
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
            for (int i = 0; i < ItemList.size(); i++) {
                Entity temp = ItemList.get(i);
                temp.update();
                if (!ItemList.contains(temp)) {
                    i--;
                }
            }
            gamePortal.update();
            bombSetup();
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g->g.render(gc));
        gamePortal.render(gc);
        entities.forEach(g -> g.render(gc));
        bombList.forEach(g -> g.render(gc));
        ItemList.forEach(g -> g.render(gc));

        if (!click) menuObj.render(gc);
        if (bomber.dead && gameOver) {
            endObj.render(gc);
        }
        if (win) {
            winObj.render(gc);
        }
    }

    public void createMonsters() {
        Random randomGen = new Random();
        AnimatedEntity object;
        for (int i = 0; i < 0 ; i++) {
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
