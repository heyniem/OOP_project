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

    public static AnimatedEntity bomber = new Bomberman(1, 1, Sprite.player_down.getFxImage(), 100);


    public static List<AnimatedEntity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();

    public static KeyHandle keyHandle = new KeyHandle();


}
