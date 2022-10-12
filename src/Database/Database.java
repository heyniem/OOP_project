package Database;

import Input.KeyHandle;
import entities.AnimatedEntities.AnimatedEntity;
import entities.AnimatedEntities.Characters.Bomberman;
import entities.AnimatedEntities.Tiles.Portal;
import entities.AnimatedEntities.Weapons.Bomb.Bomb;
import entities.Entity;
import graphics.Sprite;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Database {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;

    public static int numOfBombItem = 0;

    public static int maxBomb = 2;
    public static int maxBombRange = 5;

    // animate bomb
    public static final int intervalBomb = 5;

    // animate bomb explode

    public static int intervalExplode = 11;



    public static Image menu = new Image("menu.png");
    public static Entity menuObj = new Portal(0, 0, menu);
    public static AnimatedEntity bomber = new Bomberman(1, 1, Sprite.player_down.getFxImage(), 100);


    public static List<AnimatedEntity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();

    public static List<Bomb> bombList = new ArrayList<>();

    public static KeyHandle keyHandle = new KeyHandle();


}
