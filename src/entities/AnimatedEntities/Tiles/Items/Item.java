package entities.AnimatedEntities.Tiles.Items;

import entities.AnimatedEntities.Tiles.Tiles;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static Database.Database.*;
import static entities.Map.scene;
import static graphics.Sprite.SCALED_SIZE;

public class Item extends Tiles {
    protected boolean displayed = false;


    public Item(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void update() {
        if (scene[y/ SCALED_SIZE][x/SCALED_SIZE] == 0) {
            displayed = true;
        }
        if (displayed && (bomber.getX() - x < 32 && bomber.getX() - x > -24) && (bomber.getY() - y < 32 && bomber.getY() - y > -32)) {
            if (this instanceof BombItem) {
                maxBomb++;
                ItemList.remove(this);
            } else if (this instanceof FlameItem) {
                maxBombRange++;
                ItemList.remove(this);
            } else if (this instanceof SpeedItem) {
                noOfSpeedItem++;
                ItemList.remove(this);
            }
        }
    }
    @Override
    public void render(GraphicsContext gc) {
        if (displayed) {
            super.render(gc);
        }
    }
}
