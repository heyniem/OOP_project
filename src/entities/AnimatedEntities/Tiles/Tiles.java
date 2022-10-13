package entities.AnimatedEntities.Tiles;

import entities.AnimatedEntities.Tiles.Items.BombItem;
import entities.AnimatedEntities.Tiles.Items.FlameItem;
import entities.AnimatedEntities.Tiles.Items.SpeedItem;
import entities.Entity;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static Database.Database.*;
import static entities.Map.scene;
import static graphics.Sprite.SCALED_SIZE;

public class Tiles extends Entity {
    public Tiles(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }
}
