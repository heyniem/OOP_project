package Input;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyHandle {
    public static boolean up = false, right = false, left = false, down = false, placeBomb;

    public void keyHandle(){};
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
            }

        });
    }
}
