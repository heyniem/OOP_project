package entities.AnimatedEntities.Characters;

import entities.AnimatedEntities.AnimatedEntity;

import entities.AnimatedEntities.Tiles.Path;
import entities.Entity;
import graphics.Sprite;
import javafx.scene.image.Image;

import java.util.List;
import java.util.Timer;

import static Database.Database.*;
import static Database.Database.WIDTH;
import static Input.KeyHandle.*;
import static entities.Map.explodeScene;
import static entities.Map.scene;
import static graphics.Sprite.*;
import static graphics.Sprite.player_down;

public class Bomberman extends Character {
    public int framePlayer = 0, intervalPlayer = 4, indexPlayer = 0;
    public int frameDead = 0, intervalDead = 6, indexDead = 0;
    public boolean moving;
    private final int speed = 2;
    private int tempX, tempY;

    public Bomberman(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
    }

    @Override
    public void update() {
        if (dead) {
            this.setX(32);
            this.setY(32);
            dead = false;
        }
        if (!moving) {
            isFree(x, y);
            if (dead) {
                this.setX(32);
                this.setY(32);
                dead = false;
            }
        }
        moving = false;
        boolean temp1 = false, temp2 = false;
        if (up && down) {
            temp2 = true;
            up = false;
            down = false;
        }
        if (left && right) {
            temp1 = true;
            left = false;
            right = false;
        }
        if (up) {
            moving = true;
            if (x % SCALED_SIZE == 0 && isFree(x, y - speed)) y -= speed;
            else {
                if (x % SCALED_SIZE < 15) {
                    tempX = x - x % SCALED_SIZE;
                    if (isFree(tempX, y - speed)) x -= speed;
                } else if (x % SCALED_SIZE > 17) {
                    tempX = x - x % SCALED_SIZE + 32;
                    if (isFree(tempX, y - speed)) x += speed;
                }
            }
        }
        if (down) {
            moving = true;
            if (x % SCALED_SIZE == 0 && isFree(x, y + speed)) y += speed;
            else {
                if (x % SCALED_SIZE < 15) {
                    tempX = x - x % SCALED_SIZE;
                    if (isFree(tempX, y + speed)) x -= speed;
                } else if (x % SCALED_SIZE > 17) {
                    tempX = x - x % SCALED_SIZE + 32;
                    if (isFree(tempX, y + speed)) x += speed;
                }
            }
        }
        if (right) {
            moving = true;
            if (y % SCALED_SIZE == 0 && isFree(x + speed, y)) x += speed;
            else {
                if (y % SCALED_SIZE < 15) {
                    tempY = y - y % SCALED_SIZE;
                    if (isFree(x + speed, tempY)) y -= speed;
                } else if (y % SCALED_SIZE > 17) {
                    tempY = y - y % SCALED_SIZE + 32;
                    if (isFree(x + speed, tempY)) y += speed;
                }
            }
        }
        if (left) {
            moving = true;
            if (y % SCALED_SIZE == 0 && isFree(x - speed, y)) x -= speed;
            else {
                if (y % SCALED_SIZE < 15) {
                    tempY = y - y % SCALED_SIZE;
                    if (isFree(x - speed, tempY)) y -= speed;
                } else if (y % SCALED_SIZE > 17) {
                    tempY = y - y % SCALED_SIZE + 32;
                    if (isFree(x - speed, tempY)) y += speed;
                }
            }
        }
        if (temp1) {
            right = true;
            left = true;
        }
        if (temp2) {
            up = true;
            down = true;
        }

        /*if (bomber.isDead()) {
            moving = false;
            right = false;
            left = false;
            up = false;
            down = false;
            frameDead++;
            if (frameDead == intervalDead) {
                frameDead = 0;
                indexDead++;
                if (indexDead == 4) {
                    indexDead = 0;
                }
            }
        }*/

        /*if (indexDead == 1) {
            bomber.setImg(player_dead1.getFxImage());
        } else if (indexDead == 2) {
            bomber.setImg(player_dead2.getFxImage());
        } else if (indexDead == 3) {
            bomber.setImg(player_dead3.getFxImage());
        }*/

        if (moving) {
            framePlayer++;
            if (framePlayer > intervalPlayer) {
                framePlayer = 0;
                indexPlayer++;
                if (indexPlayer > 3) {
                    indexPlayer = 0;
                }
            }

            if (right) {
                if (indexPlayer == 1) {
                    bomber.setImg(player_right.getFxImage());
                }
                if (indexPlayer == 2) {
                    bomber.setImg(player_right_1.getFxImage());
                }
                if (indexPlayer == 3) {
                    bomber.setImg(player_right_2.getFxImage());
                }
            } else if (left) {
                if (indexPlayer == 1) {
                    bomber.setImg(player_left.getFxImage());
                }
                if (indexPlayer == 2) {
                    bomber.setImg(player_left_1.getFxImage());
                }
                if (indexPlayer == 3) {
                    bomber.setImg(player_left_2.getFxImage());
                }
            } else if (up) {
                if (indexPlayer == 1) {
                    bomber.setImg(player_up.getFxImage());
                }
                if (indexPlayer == 2) {
                    bomber.setImg(player_up_1.getFxImage());
                }
                if (indexPlayer == 3) {
                    bomber.setImg(player_up_2.getFxImage());
                }
            } else if (down) {
                if (indexPlayer == 1) {
                    bomber.setImg(player_down.getFxImage());
                }
                if (indexPlayer == 2) {
                    bomber.setImg(player_down_1.getFxImage());
                }
                if (indexPlayer == 3) {
                    bomber.setImg(player_down_2.getFxImage());
                }
            }
        } else {
            bomber.setImg(player_down.getFxImage());
        }
    }

    private boolean isFree(int nextX, int nextY) {
        int nextX_1 = nextX / SCALED_SIZE;
        int nextY_1 = nextY / SCALED_SIZE;

        int nextX_2 = (nextX + SCALED_SIZE - 1) / SCALED_SIZE;
        int nextY_2 = nextY / SCALED_SIZE;

        int nextX_3 = nextX / SCALED_SIZE;
        int nextY_3 = (nextY + SCALED_SIZE - 1) / SCALED_SIZE;

        int nextX_4 = (nextX + SCALED_SIZE - 1) / SCALED_SIZE;
        int nextY_4 = (nextY + SCALED_SIZE - 1) / SCALED_SIZE;

        if (explodeScene[nextY_1][nextX_1] >= 1 || explodeScene[nextY_2][nextX_2] >= 1
                || explodeScene[nextY_3][nextX_3] >= 1 || explodeScene[nextY_4][nextX_4] >= 1) {
            dead = true;
            return false;
        }

        if (scene[nextY_1][nextX_1] == 4 || scene[nextY_2][nextX_2] == 4
                || scene[nextY_3][nextX_3] == 4 || scene[nextY_4][nextX_4] == 4) {
            if (scene[nextY_1][nextX_1] == 4) {
                Entity object = new Path(nextX_1, nextY_1, Sprite.grass.getFxImage());
                stillObjects.set(nextY_1 * WIDTH + nextX_1, object);
                scene[nextY_1][nextX_1] = 0;
                maxBombRange++;
            } else if (scene[nextY_2][nextX_2] == 4) {
                Entity object = new Path(nextX_2, nextY_2, Sprite.grass.getFxImage());
                stillObjects.set(nextY_2 * WIDTH + nextX_2, object);
                scene[nextY_2][nextX_2] = 0;
                maxBombRange++;
            } else if (scene[nextY_3][nextX_3] == 4) {
                Entity object = new Path(nextX_3, nextY_3, Sprite.grass.getFxImage());
                stillObjects.set(nextY_3 * WIDTH + nextX_3, object);
                scene[nextY_3][nextX_3] = 0;
                maxBombRange++;
            } else if (scene[nextY_4][nextX_4] == 4) {
                Entity object = new Path(nextX_4, nextY_4, Sprite.grass.getFxImage());
                stillObjects.set(nextY_4 * WIDTH + nextX_4, object);
                scene[nextY_4][nextX_4] = 0;
                maxBombRange++;
            }
        }

        return !((scene[nextY_1][nextX_1] == 1 || scene[nextY_1][nextX_1] == 2) ||
                (scene[nextY_2][nextX_2] == 1 || scene[nextY_2][nextX_2] == 2) ||
                (scene[nextY_3][nextX_3] == 1 || scene[nextY_3][nextX_3] == 2) ||
                (scene[nextY_4][nextX_4] == 1 || scene[nextY_4][nextX_4] == 2));
    }

    @Override
    protected boolean isCollide(List<AnimatedEntity> entities, int newX, int newY) {
        int tempX, tempY;
        for (AnimatedEntity i : entities) {
            if (i.getId() != this.id) {
                tempX = i.getX();
                tempY = i.getY();
                if (Math.abs(tempX - newX) < 32 && Math.abs(tempY - newY) < 32) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDead() {
        for (AnimatedEntity i : entities) {
            if (i.getId() != this.id) {
                if (((i.getX() - this.x) < 24 && (i.getX() - this.x > -32)) && Math.abs(i.getY() - this.y) < 32) {
                    return true;
                }
            }
        }
        return false;
    }

    public void checkDead() {
        for (AnimatedEntity i : entities) {
            if (i instanceof Bomberman && i.isDead()) {
                i.setX(32);
                i.setY(32);
            }
        }
    }
}
