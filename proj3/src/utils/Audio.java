package utils;

import javax.sound.sampled.*;
import java.io.File;

public class Audio {

    public static void playAudio(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);

            // Get a Clip instance
            Clip clip = AudioSystem.getClip();

            // Open the audioInputStream to the clip
            clip.open(audioInputStream);

            // Start playing the audio clip
            clip.start();

            // Wait for the clip to finish playing
            while (!clip.isRunning()) {
                Thread.sleep(20);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
