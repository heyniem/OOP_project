import Input.KeyHandle;
import entities.AnimatedEntities.Characters.Bomberman;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import entities.Entity;
import entities.AnimatedEntities.Tiles.Path;
import entities.AnimatedEntities.Tiles.Wall;
import graphics.Sprite;
import entities.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static entities.Map.scene;

public class Main extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;

    public boolean up, down, right, left;

    public int Xunit = 1;
    public int Yunit = 1;


    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();

    public static KeyHandle keyHandle = new KeyHandle();

    Entity bomber = new Bomberman(Xunit, Yunit, Sprite.player_down.getFxImage());



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
//        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                KeyCode code = keyEvent.getCode();
//                if (code == KeyCode.W) up = true;
//                if (code == KeyCode.S) down = true;
//                if (code == KeyCode.A) left = true;
//                if (code == KeyCode.D) right = true;
//            }
//        });
//        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                KeyCode code = keyEvent.getCode();
//                if (code == KeyCode.W) up = false;
//                if (code == KeyCode.S) down = false;
//                if (code == KeyCode.A) left = false;
//                if (code == KeyCode.D) right = false;
//            }
//        });



        // Add scene to stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        createMap();
    }

    public void createMap() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (scene[i][j] == 0) {
                    if (new Random().nextInt(10) < 1) {
                        scene[i][j] = 2;
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
                } else {
                    object = new Path(j, i, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }
        entities.add(bomber);
    }

    public void update() {
        entities.forEach(Entity::update);
        //bomber.update();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        //bomber.render(gc);
    }
}