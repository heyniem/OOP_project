package entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import graphics.Sprite;

public abstract class Entity {
    protected int x;
    protected int y;

    public int posX;
    public int posY;

    protected Image img;
    public boolean checkItem;


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
    public abstract void update();

    public int returnPosX() {
        return posX;
    }

    public int returnPosY() {
        return posY;
    }
    public void setImg(Image image) {
        this.img = image;
    }
}
