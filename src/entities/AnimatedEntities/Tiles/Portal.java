package entities.AnimatedEntities.Tiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static Database.Database.*;
import static entities.Map.scene;
import static graphics.Sprite.SCALED_SIZE;

public class Portal extends Tiles{
    protected boolean displayed = false;
    public static boolean checkWin = false;
    public Portal(int xUnit, int yUnit, Image img, boolean displayed) {
        super(xUnit, yUnit, img);
        this.displayed = displayed;
    }

    @Override
    public void update() {
        if (scene[y/ SCALED_SIZE][x/SCALED_SIZE] == 0) {
            //System.out.println("portal shown");
            displayed = true;
        }
        if (displayed && bomber.getX() == x && bomber.getY() == y) {
            if (entities.size() == 1) {
                checkWin = true;
                Iswin = true;
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
