package entities.AnimatedEntities.Weapons.Bomb;

import entities.AnimatedEntities.Weapons.Weapon;
import entities.Entity;
import graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static Database.Database.*;
import static entities.Map.explodeScene;
import static entities.Map.scene;
import static graphics.Sprite.*;

public class Bomb extends Weapon {

    public Timer timer = new Timer();
    public TimerTask task = new TimerTask() {
        @Override
        public void run() {
            checkBombExplode = true;
            sound.playSingleEp(0);
        }
    };
    List<BombFlame> bombFlameList = new ArrayList<>();

    public int frameBomb = 0;

    public int indexBomb = 2;

    public int countFrameExplode = 0;

    public boolean checkBombExplode = false;
    public boolean bomb_explode = false;

    public Bomb(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
    }

    @Override
    public void update() {
        if (!checkBombExplode) {
            frameBomb++;
            if (frameBomb > intervalBomb) {
                frameBomb = 0;
                indexBomb++;
                if (indexBomb > intervalBomb) {
                    indexBomb = 0;
                }
            }
            if (indexBomb % 3 == 0) {
                this.setImg(Sprite.bomb_0.getFxImage());
            } else if (indexBomb % 3 == 1) {
                this.setImg(bomb_1.getFxImage());
            } else if (indexBomb % 3 == 2) {
                this.setImg(bomb_2.getFxImage());
            }
        } else {
            bombExplode();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        //System.out.println(bombFlameList.size());
        bombFlameList.forEach(g -> g.render(gc));
    }

    public void bombExplode() {
        int tempX = this.x / SCALED_SIZE, tempY = this.y / SCALED_SIZE;
        if (!bomb_explode) {
            bombFlameList.add(new BombFlame(tempX, tempY, bomb_exploded.getFxImage(), -1));
            explodeScene[tempY][tempX]++;
            detectSurround(tempX, tempY);
            bomb_explode = true;
            //print explodeScene
//            for (int i = 0; i < explodeScene.length; i++) {
//                for (int j = 0; j < explodeScene[0].length; j++) {
//                    System.out.print(explodeScene[i][j] + " ");
//                }
//                System.out.println();
//            }
//            System.out.println();
        } else {
            for (BombFlame i : bombFlameList) i.update();
        }
        countFrameExplode++;
        if (countFrameExplode > intervalExplode) {
            scene[tempY][tempX] = 0;
            for (BombFlame i : bombFlameList) {
                explodeScene[i.y / SCALED_SIZE][i.x / SCALED_SIZE]--;
            }
            bombList.remove(this);
        }
    }

    public void detectSurround(int bombX, int bombY) {
        //Right
        for (int i = 1; i <= maxBombRange; i++) {
            if (scene[bombY][bombX + i] == 1) break;
            else if (scene[bombY][bombX + i] == 2) {
                for (Entity j : stillObjects) {
                    if (j.getX() / SCALED_SIZE == bombX + i && j.getY() / SCALED_SIZE == bombY) {
                        j.isDestroyed = true;
                    }
                }
                //bombFlameList.add(new BombFlame(bombX + i, bombY, explosion_horizontal_right_last.getFxImage(), 11));
                break;
            } else if (scene[bombY][bombX + i] == 3) {
                for (Bomb j : bombList) {
                    if (j.getX() / SCALED_SIZE == bombX + i && j.getY() / SCALED_SIZE == bombY && !j.checkBombExplode) {
                        j.checkBombExplode = true;
                        j.timer.cancel();
                    }
                }
                break;
            } else {
                if (i != maxBombRange)
                    bombFlameList.add(new BombFlame(bombX + i, bombY, explosion_horizontal.getFxImage(), 1));
                else
                    bombFlameList.add(new BombFlame(bombX + i, bombY, explosion_horizontal_right_last.getFxImage(), 11));
                explodeScene[bombY][bombX + i]++;
            }
        }
        //Left
        for (int i = 1; i <= maxBombRange; i++) {
            if (scene[bombY][bombX - i] == 1) break;
            else if (scene[bombY][bombX - i] == 2) {
                for (Entity j : stillObjects) {
                    if (j.getX() / SCALED_SIZE == bombX - i && j.getY() / SCALED_SIZE == bombY) {
                        j.isDestroyed = true;
                    }
                }
                //bombFlameList.add(new BombFlame(bombX - i, bombY, explosion_horizontal_left_last.getFxImage(), 13));
                break;
            } else if (scene[bombY][bombX - i] == 3) {
                for (Bomb j : bombList) {
                    if (j.getX() / SCALED_SIZE == bombX - i && j.getY() / SCALED_SIZE == bombY && !j.checkBombExplode) {
                        j.checkBombExplode = true;
                        j.timer.cancel();
                    }
                }
                break;
            } else {
                if (i != maxBombRange)
                    bombFlameList.add(new BombFlame(bombX - i, bombY, explosion_horizontal.getFxImage(), 3));
                else
                    bombFlameList.add(new BombFlame(bombX - i, bombY, explosion_horizontal_left_last.getFxImage(), 13));
                explodeScene[bombY][bombX - i]++;
            }
        }
        //Down
        for (int i = 1; i <= maxBombRange; i++) {
            if (scene[bombY + i][bombX] == 1) break;
            else if (scene[bombY + i][bombX] == 2) {
                for (Entity j : stillObjects) {
                    if (j.getX() / SCALED_SIZE == bombX && j.getY() / SCALED_SIZE == bombY + i) {
                        j.isDestroyed = true;
                    }
                }
                //bombFlameList.add(new BombFlame(bombX, bombY + i, explosion_vertical_down_last.getFxImage(), 12));
                break;
            } else if (scene[bombY + i][bombX] == 3) {
                for (Bomb j : bombList) {
                    if (j.getX() / SCALED_SIZE == bombX && j.getY() / SCALED_SIZE == bombY + i && !j.checkBombExplode) {
                        j.checkBombExplode = true;
                        j.timer.cancel();
                    }
                }
                break;
            } else {
                if (i != maxBombRange)
                    bombFlameList.add(new BombFlame(bombX, bombY + i, explosion_vertical.getFxImage(), 2));
                else bombFlameList.add(new BombFlame(bombX, bombY + i, explosion_vertical_down_last.getFxImage(), 12));
                explodeScene[bombY + i][bombX]++;
            }
        }
        //Up
        for (int i = 1; i <= maxBombRange; i++) {
            if (scene[bombY - i][bombX] == 1) break;
            else if (scene[bombY - i][bombX] == 2) {
                for (Entity j : stillObjects) {
                    if (j.getX() / SCALED_SIZE == bombX && j.getY() / SCALED_SIZE == bombY - i) {
                        //System.out.println("This happened OMG");
                        j.isDestroyed = true;
                        break;
                    }
                }
                //bombFlameList.add(new BombFlame(bombX, bombY - i, explosion_vertical_top_last.getFxImage(), 10));
                break;
            } else if (scene[bombY - i][bombX] == 3) {
                for (Bomb j : bombList) {
                    if (j.getX() / SCALED_SIZE == bombX && j.getY() / SCALED_SIZE == bombY - i && !j.checkBombExplode) {
                        j.checkBombExplode = true;
                        j.timer.cancel();
                    }
                }
                break;
            } else {
                if (i != maxBombRange)
                    bombFlameList.add(new BombFlame(bombX, bombY - i, explosion_vertical.getFxImage(), 0));
                else bombFlameList.add(new BombFlame(bombX, bombY - i, explosion_vertical_top_last.getFxImage(), 10));
                explodeScene[bombY - i][bombX]++;
            }
        }
    }
}