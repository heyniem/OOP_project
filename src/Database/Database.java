package Database;

import Input.KeyHandle;
import entities.AnimatedEntities.AnimatedEntity;
import entities.AnimatedEntities.Characters.Bomberman;
import entities.Entity;
import graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Database {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;

    public static int numOfBombItem = 0;

    // animate bomb
    public static int frameBomb = 0;
    public static int intervalBomb = 5;
    public static int indexBomb = 0;

    // animate bomb explode

    public static int intervalExplode = 11;
    public static int frameExplode = 0;
    public static int indexBombExplode = 0;


    // animate bomb Flame
    public static int intervalFlameExpansion = 8;
    public static int indexFlameExpansion = 0;

    public static AnimatedEntity bomb_flame_horizontal_left = null;
    public static AnimatedEntity bomb_flame_horizontal_right = null;
    public static AnimatedEntity bomb_flame_vertical_up = null;
    public static AnimatedEntity bomb_flame_vertical_down = null;
    public static AnimatedEntity getBomb_flame_center = null;
    public static AnimatedEntity bomb_explode = null;


    public static AnimatedEntity bomber = new Bomberman(1, 1, Sprite.player_down.getFxImage(), 100);


    public static List<AnimatedEntity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();

    public static List<AnimatedEntity> bombSetup = new ArrayList<>();
    public static List<AnimatedEntity> bombExplode = new ArrayList<>();
    public static List<AnimatedEntity> bombFlame = new ArrayList<>();

    public static AnimatedEntity bomb = null;

    public static KeyHandle keyHandle = new KeyHandle();


}
