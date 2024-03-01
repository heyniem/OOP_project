import Database.Database;
import entities.AnimatedEntities.AnimatedEntity;
import entities.AnimatedEntities.Characters.Bomberman;
import entities.AnimatedEntities.Characters.Enemies.*;
import entities.AnimatedEntities.Tiles.Items.BombItem;
import entities.AnimatedEntities.Tiles.Items.FlameItem;
import entities.AnimatedEntities.Tiles.Items.Item;
import entities.AnimatedEntities.Tiles.Items.SpeedItem;
import entities.AnimatedEntities.Tiles.Path;
import entities.AnimatedEntities.Tiles.SoftWall;
import entities.AnimatedEntities.Tiles.Wall;
import entities.AnimatedEntities.Weapons.Bomb.Bomb;
import entities.Entity;
import graphics.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

import static Database.Database.*;
import static Input.KeyHandle.click;
import static Input.KeyHandle.placeBomb;
import static entities.AnimatedEntities.Tiles.Portal.checkWin;
import static entities.Map.clearMap;
import static entities.Map.scene;
import static graphics.Sprite.*;

public class Main extends Application {

    private GraphicsContext gc;
    private Canvas canvas;

    private final long fps = 50;

    private long timeCount = 0, preTimeCount = 0, sec = 0, start = System.currentTimeMillis();

    private Text text = new Text();
    private Text timeText = new Text();
    private Text levelText = new Text();


    public static void main(String[] args) {
        Application.launch(Main.class);
    }

    @Override
    public void start(Stage stage) {
        text.setText("Score: ");
        text.setFont(Font.font("SHOWCARD GOTHIC", 24));
        text.setX(16);
        text.setY(24);

        timeText.setText("Time: ");
        timeText.setFont(Font.font("SHOWCARD GOTHIC", 24));
        timeText.setX(250);
        timeText.setY(24);

        levelText.setText("Level: ");
        levelText.setFont(Font.font("SHOWCARD GOTHIC", 24));
        levelText.setX(500);
        levelText.setY(24);


        // Create Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();
        // Create root container
        Group root = new Group(text, timeText, levelText);
        root.getChildren().add(canvas);

        // Create scene
        Scene scene = new Scene(root);
        scene.setFill(Color.GREEN);
        keyHandle.checkMouse(scene);
        keyHandle.checkKey(scene);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                long firstTime = System.currentTimeMillis();
                render();
                update();
                afterUpdate();
                //Check time diff to place cd time.
                long diff = System.currentTimeMillis() - firstTime;
                timeCount++;
                long temp = System.currentTimeMillis() - start;
                if (temp / 1000 == sec) {
                    if (sec != 0) stage.setTitle("Bomberman, fps=" + (timeCount - preTimeCount));
                    sec++;
                    preTimeCount = timeCount;
                    if (click && time > 0) {
                        time--;
                    }
                }
                try {
                    //System.out.println("diff = " + diff);
                    Thread.sleep(1000 / fps - diff - 2);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        };
        timer.start();

        createMap();
        createMonsters();
        sound2.PlayMusic(4);
    }

    public void createMap() {
        Random randomGen = new Random();
        for (int i = 1; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (scene[i][j] == 0) {
                    int ran = new Random().nextInt(15);
                    if (ran <= 2) {
                        scene[i][j] = 2;
                    }
                }
            }
        }

        scene[2][1] = 0;
        scene[3][1] = 0;
        scene[2][2] = 0;
        scene[4][1] = 2;
        scene[4][2] = 2;
        scene[4][3] = 2;
        scene[3][3] = 2;
        scene[2][3] = 2;
        for (int i = 0; i < 5; i++) {
            while (true) {
                int x = randomGen.nextInt(WIDTH - 2) + 1;
                int y = randomGen.nextInt(HEIGHT - 3) + 2;
                boolean check = true;
                for (Item entity : ItemList) {
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
        for (int i = 0; i < 5; i++) {
            while (true) {
                int x = randomGen.nextInt(WIDTH - 2) + 1;
                int y = randomGen.nextInt(HEIGHT - 3) + 2;
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

        for (int i = 0; i < 5; i++) {
            while (true) {
                int x = randomGen.nextInt(WIDTH - 2) + 1;
                int y = randomGen.nextInt(HEIGHT - 3) + 2;
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
        //Create Portal
        while (true) {
            int x = randomGen.nextInt(WIDTH - 2) + 1;
            int y = randomGen.nextInt(HEIGHT - 3) + 2;
            boolean check = true;
            for (Item entity : ItemList) {
                if (entity.getX() == x * SCALED_SIZE && entity.getY() == y * SCALED_SIZE) {
                    check = false;
                    break;
                }
            }
            if (scene[y][x] == 2 && check) {
                gamePortal.setX(x * SCALED_SIZE);
                gamePortal.setY(y * SCALED_SIZE);
                break;
            }
        }
        //Print item list
        for (Item entity : ItemList) {
            System.out.println(entity.getY() / SCALED_SIZE + " - " + entity.getX() / SCALED_SIZE + " - " + entity.getClass());
        }
        System.out.println(gamePortal.getY() / SCALED_SIZE + " - " + gamePortal.getX() / SCALED_SIZE);


        for (int i = 1; i < HEIGHT; i++) {
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

    public void update() {
        if(retry || nextLevel) {
            resetAll();
        }
        else if (bomber.dead) {
            bomber.update();
        } else if (!Iswin) {
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
            text.setText("Score: " + score);
            timeText.setText("Time: " + time);
            levelText.setText("Level: " + level);
            if (time == 0) {
                gameOver = true;
                bomber.dead = true;
            }
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        gamePortal.render(gc);
        entities.forEach(g -> g.render(gc));
        bombList.forEach(g -> g.render(gc));
        ItemList.forEach(g -> g.render(gc));

        if (bomber.dead && gameOver) {
            endObj.render(gc);
        }
        if (checkWin) {
            if (Iswin) {
                winObj.render(gc);
            }
        }
        if (!click) {
            menuObj.render(gc);
        }
    }

    public void createMonsters() {
        Random randomGen = new Random();
        AnimatedEntity object;
        for (int i = 0;i < 6;i++) {
            for (int j = 0; j < monsterList[Math.min(level-1, 10)][i]; j++) {
                while(true) {
                    int temp = entities.size();
                    int x = randomGen.nextInt(WIDTH - 2) + 1;
                    int y = randomGen.nextInt(HEIGHT - 3) + 2;
                    if (scene[y][x] == 0 && !checkMonster(x * SCALED_SIZE, y * SCALED_SIZE) && (y >= 5 || x >= 4)) {
                        System.out.println("Monster " + i + " " + x + " " + y);
                        switch (i) {
                            case 0:
                                object = new Balloon(x, y, Sprite.balloom_left1.getFxImage(), 10 * i + j);
                                break;
                            case 1:
                                object = new Oreal(x, y, Sprite.oneal_left1.getFxImage(), 10 * i + j);
                                break;
                            case 2:
                                object = new Doll(x, y, Sprite.doll_left1.getFxImage(), 10 * i + j);
                                break;
                            case 3:
                                object = new Ghost(x, y, ghost_left1.getFxImage(), 10 * i + j);
                                break;
                            case 4:
                                object = new Kondoria(x, y, Sprite.kondoria_left1.getFxImage(), 10 * i + j);
                                break;
                            case 5:
                                object = new Ball(x, y, minvo_left1.getFxImage(), 10 * i + j);
                                break;
                            default:
                                object = new Balloon(x, y, Sprite.balloom_left1.getFxImage(), 10 * i + j);
                                break;
                        }
                        entities.add(object);
                    }
                    if (entities.size()!= temp) break;
                }
            }
        }
    }

    public void bombSetup() {
        int x1 = (bomber.getX() + 16) / SCALED_SIZE;
        int y1 = (bomber.getY() + 16) / SCALED_SIZE;
        if (placeBomb && bombList.size() < maxBomb && scene[y1][x1] != 3) {
            sound.playSingleEp(6);
            Bomb bomb = new Bomb(x1, y1, Sprite.bomb_0.getFxImage(), bombList.size() * 100 + 30);
            scene[y1][x1] = 3;
            bomb.timer.schedule(bomb.task, 2500);
            bombList.add(bomb);
        } else try {
            for (int i = 0; i < bombList.size(); i++) {
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
        entities.forEach(AnimatedEntity::checkDead);
    }

    public void moveEnermyBack(int bombX, int bombY) {
        for (AnimatedEntity i : entities) {
            if (i.getId() != bomber.getId()) {
                if (i.getX() - bombX < 32 && i.getX() - bombX > 0 && i.getY() == bombY) {
                    i.setX(bombX + 32);
                } else if (bombX - i.getX() < 32 && bombX - i.getX() > 0 && i.getY() == bombY) {
                    i.setX(bombX - 32);
                } else if (i.getY() - bombY < 32 && i.getY() - bombY > 0 && i.getX() == bombX) {
                    i.setY(bombY + 32);
                } else if (bombY - i.getY() < 32 && bombY - i.getY() > 0 && i.getX() == bombX) {
                    i.setY(bombY - 32);
                }
            }
        }
    }

    public void resetAll() {
        if (retry) {
            entities.clear();
            stillObjects.clear();
            ItemList.clear();
            bombList.clear();
            clearMap();
            bomber = new Bomberman(1, 2, player_down.getFxImage(), 100);
            createMap();
            createMonsters();
            time = 300;
            score = 0;
            gameOver = false;
            Iswin = false;
            checkWin = false;
            retry = false;
            level = 1;
        }
        else if (nextLevel){
            entities.clear();
            System.out.println("No of entities now: " + entities.size());
            stillObjects.clear();
            ItemList.clear();
            bombList.clear();
            clearMap();
            bomber = new Bomberman(1, 2, player_down.getFxImage(), 100);
            createMap();
            createMonsters();
            time += 120;
            gameOver = false;
            Iswin = false;
            checkWin = false;
            nextLevel = false;
            level++;
        }
    }

    public boolean checkMonster(int x, int y) {
        for (AnimatedEntity i : entities) {
            if (i.getX() == x && i.getY() == y) {
                return true;
            }
        }
        return false;
    }
}
