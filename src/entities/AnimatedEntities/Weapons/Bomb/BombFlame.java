package entities.AnimatedEntities.Weapons.Bomb;

import javafx.scene.image.Image;

import static graphics.Sprite.*;
import static graphics.Sprite.bomb_exploded;

public class BombFlame extends Bomb {
    private int countFrameExplode = 0;
    public BombFlame(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
    }

    @Override
    public void update() {
        if (this.id == 1 || this.id == 3) {
            if (this.countFrameExplode <=3)
                this.setImg(explosion_horizontal.getFxImage());
            else if (this.countFrameExplode <= 7)
                this.setImg(explosion_horizontal1.getFxImage());
            else this.setImg(explosion_horizontal2.getFxImage());
        }
        else if (this.id == 11) {
            if (this.countFrameExplode <= 3)
                this.setImg(explosion_horizontal_right_last.getFxImage());
            else if (this.countFrameExplode <= 7)
                this.setImg(explosion_horizontal_right_last1.getFxImage());
            else this.setImg(explosion_horizontal_right_last2.getFxImage());
        }
        else if (this.id == 13) {
            if (this.countFrameExplode <= 3)
                this.setImg(explosion_horizontal_left_last.getFxImage());
            else if (this.countFrameExplode <= 7)
                this.setImg(explosion_horizontal_left_last1.getFxImage());
            else this.setImg(explosion_horizontal_left_last2.getFxImage());
        }
        else if (this.id == 0 || this.id == 2) {
            if (this.countFrameExplode <= 3)
                this.setImg(explosion_vertical.getFxImage());
            else if (this.countFrameExplode <= 7)
                this.setImg(explosion_vertical1.getFxImage());
            else this.setImg(explosion_vertical2.getFxImage());
        }
        else if (this.id == 10) {
            if (this.countFrameExplode <= 3)
                this.setImg(explosion_vertical_top_last.getFxImage());
            else if (this.countFrameExplode <= 7)
                this.setImg(explosion_vertical_top_last1.getFxImage());
            else this.setImg(explosion_vertical_top_last2.getFxImage());
        }
        else if (this.id == 12) {
            if (this.countFrameExplode <= 3)
                this.setImg(explosion_vertical_down_last.getFxImage());
            else if (this.countFrameExplode <= 7)
                this.setImg(explosion_vertical_down_last1.getFxImage());
            else this.setImg(explosion_vertical_down_last2.getFxImage());
        }
        else if (this.id == -1) {
            if (this.countFrameExplode <= 3)
                this.setImg(bomb_exploded.getFxImage());
            else if (this.countFrameExplode <= 7) this.setImg(bomb_exploded1.getFxImage());
            else this.setImg(bomb_exploded2.getFxImage());
        }
        countFrameExplode++;
    }
}
