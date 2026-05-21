package JumpTest;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound
{
    private Clip clip;

    public Sound(String filename)
    {
        URL resource = Sound.class.getResource(filename);
        if (resource == null) {
            System.err.println("Missing sound resource: " + filename);
            return;
        }

        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(resource)) {
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (Exception ex) {
            System.err.println("Unable to load sound resource " + filename + ": " + ex.getMessage());
        }
    }

    public void play()
    {
        if (clip == null) {
            return;
        }

        if (clip.isRunning()) {
            clip.stop();
        }

        clip.setFramePosition(0);
        clip.start();
    }
}
