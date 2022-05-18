package audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Sound {
    Clip clip;
    private Map<String, String> sounds;
    public Sound() {
        sounds = new HashMap<>();
        sounds.put("idling", "sounds/idling.wav");
        sounds.put("moving", "sounds/moving.wav");
        sounds.put("dead", "sounds/dead.wav");
    }

    public void setFile(String sound) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(sounds.get(sound)));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
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
}
