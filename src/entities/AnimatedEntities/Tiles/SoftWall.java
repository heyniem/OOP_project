package entities.AnimatedEntities.Tiles;

import entities.Entity;
import javafx.scene.image.Image;

import static entities.Map.scene;
import static graphics.Sprite.*;
import static Database.Database.*;

public class SoftWall extends Tiles{
    int indexDestroy = 0;
    public SoftWall(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        //System.out.println("Tiles updated.");
        if (isDestroyed) {
            System.out.println("This happened");
            indexDestroy++;
        } else {
            setImg(brick.getFxImage());
        }
        if (indexDestroy !=0) {
            if (indexDestroy / 4 == 0) {
                this.setImg(brick_destroyed_1.getFxImage());
            }
            else if (indexDestroy /4 == 1) {
                this.setImg(brick_destroyed_2.getFxImage());
            }
            else if (indexDestroy / 4 == 2) {
                this.setImg(brick_destroyed_3.getFxImage());
            }
            else if (indexDestroy == 12) {
                for (Entity i : stillObjects) {
                    if (i.getX() == this.x && i.getY() == this.y) {
                        scene[this.y/SCALED_SIZE][this.x/SCALED_SIZE] = 0;
                        stillObjects.remove(this);
                        break;
                    }
                }
            }
        }
    }
}
