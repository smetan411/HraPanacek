package game.core;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public final class Sound {
    private final Clip clip;

    public Sound(String pathToSound) {
        URL soundURL = getClass().getResource(pathToSound);
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL))  {
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            throw new RuntimeException("Can not play sound : " + soundURL);
        }
    }

    public void play() {
        clip.start();
    }

    public void playInLoop() {
        play();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }
}
