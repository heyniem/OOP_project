package Input;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import static Database.Database.*;

public class KeyHandle {
    public static boolean up = false, right = false, left = false, down = false,
            placeBomb = false, click = false;
    public void keyHandle(){};

    public void checkMouse(Scene scene) {
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();
                if (x >= 265 && x <= 375 && y >= 205 && y <= 256) {
                    click = true;
                } else if (x >= 265 && x <= 375 && y >= 256 && y <= 307) {
                    System.exit(0);
                }
            }
        });

        scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                double x = mouseEvent.getX();
                double y = mouseEvent.getY();
                if (x >= 265 && x <= 375 && y >= 205 && y <= 256) {
                    click = false;
                }
            }
        });
    }
    public void checkKey(Scene scene) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.W) up = true;
                if (code == KeyCode.S) down = true;
                if (code == KeyCode.A) left = true;
                if (code == KeyCode.D) right = true;
                if (code == KeyCode.X) placeBomb = true;
                if (code == KeyCode.R) retry = true;
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                KeyCode code = keyEvent.getCode();
                if (code == KeyCode.W) up = false;
                if (code == KeyCode.S) down = false;
                if (code == KeyCode.A) left = false;
                if (code == KeyCode.D) right = false;
                if (code == KeyCode.X) placeBomb = false;
                if (code == KeyCode.R) retry =false;
            }
        });
    }
}
