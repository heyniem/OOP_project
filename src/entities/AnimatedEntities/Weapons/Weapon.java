package entities.AnimatedEntities.Weapons;

import entities.AnimatedEntities.AnimatedEntity;
import static entities.Map.*;
import static Database.Database.*;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.security.auth.login.CredentialNotFoundException;

import static graphics.Sprite.SCALED_SIZE;

public abstract class Weapon extends AnimatedEntity {
    public Weapon(int xUnit, int yUnit, Image img, int id) {
        super(xUnit, yUnit, img, id);
    }

}
