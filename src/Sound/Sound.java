package Sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

import static Database.Database.sound;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/audio/boom.wav");
        soundURL[1] = getClass().getResource("/audio/clear.wav");
        soundURL[2] = getClass().getResource("/audio/dead1.wav");
        soundURL[3] = getClass().getResource("/audio/dead2.wav");
        soundURL[4] = getClass().getResource("/audio/gameaudio.wav");
        soundURL[5] = getClass().getResource("/audio/intro.wav");
        soundURL[6] = getClass().getResource("/audio/putbomb.wav");
        soundURL[7] = getClass().getResource("/audio/win.wav");
        soundURL[8] = getClass().getResource("/audio/getitem.wav");

    }

    public void setFile(int i) {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(inputStream);

        } catch (Exception e) {

        }
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void PlayMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void StopMusic() {

        sound.stop();
    }

    public void playSingleEp(int i) {
        sound.setFile(i);
        sound.play();
    }
}
