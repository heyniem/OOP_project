package Database;

import Input.KeyHandle;

import Sound.Sound;
import entities.AnimatedEntities.AnimatedEntity;
import entities.AnimatedEntities.Characters.Bomberman;
import entities.AnimatedEntities.Tiles.Items.Item;
import entities.AnimatedEntities.Tiles.Portal;
import entities.AnimatedEntities.Weapons.Bomb.Bomb;
import entities.Entity;
import graphics.Sprite;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Database {

    public static Sound sound = new Sound();
    public static Sound sound2 = new Sound();

    public static final int WIDTH = 20;
    public static final int HEIGHT = 16;
    public static int level = 1;
    public static int[][] monsterList = {
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0}
    };

    public static int maxBomb = 1;
    public static int maxBombRange = 1;
    public static int noOfSpeedItem = 0;
    public static boolean nextLevel = false;
    public static boolean retry = false;
    public static boolean reset = true;

    // animate bomb
    public static final int intervalBomb = 5;

    // animate bomb explode

    public static int score = 0;
    public static int time = 300;
    public static boolean gameOver = false;
    public static boolean Iswin = false;

    public static int intervalExplode = 11;

    public static Image menu = new Image("menu.png");
    public static Image end = new Image("gameover.png");

    public static Image win = new Image("win.png");
    public static Image winScene = new Image("classic.png");
    public static Entity gamePortal = new Portal(1, 3, Sprite.portal.getFxImage(), false);
    public static Entity menuObj = new Portal(0, 0, menu, true);

    public static Entity endObj = new Portal(0, 0, end, true);
    public static Entity winObj = new Portal(0, 0, win, true);


    public static AnimatedEntity bomber = new Bomberman(1, 2, Sprite.player_down.getFxImage(), 100);

    public static List<AnimatedEntity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();

    public static List<Bomb> bombList = new ArrayList<>();
    public static List<Item> ItemList = new ArrayList<>();

    public static KeyHandle keyHandle = new KeyHandle();
}
