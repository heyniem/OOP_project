package entities;

import graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Entity {
    protected int x;
    protected int y;

    protected Image img;

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

    public void setImg(Image image) {
        this.img = image;
    }
}
