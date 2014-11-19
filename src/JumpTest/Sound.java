package JumpTest;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound 
{
    private AudioClip clip;
    
    public Sound(String filename)
    {
        try
        {
            clip = Applet.newAudioClip(Sound.class.getResource(filename));
        } catch (Exception ex){}
    }
    
    public void play()
    {
        try
        {
            new Thread()
            {
                @Override
                public void run()
                {
                    clip.play();
                }
            }.start();
        } catch(Exception ex){}
    }
}