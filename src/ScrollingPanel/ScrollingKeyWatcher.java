package ScrollingPanel;

import JumpTest.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ScrollingKeyWatcher implements KeyListener{
    
    protected ScrollFrame frame;
    public Jumper jumper;
    
    public ScrollingKeyWatcher(ScrollFrame f){
        frame = f;
    }
    
    public ScrollingKeyWatcher(ScrollFrame f, Jumper j){
        frame = f;
        jumper = j;
    }

    @Override
    public void keyPressed(KeyEvent e){
        char n = e.getKeyChar();
        
        if(n == 'a' || e.getKeyCode() == 37){
            frame.left = true;
        }
        
        if(n == 'd' || e.getKeyCode() == 39){
            frame.right = true;
        }
        
        if(n == 'a' || e.getKeyCode() == 37){
            jumper.left = true;
        }
        
        if(n == 'd' || e.getKeyCode() == 39){
            jumper.right = true;
        }
        
        if(n == ' ' || e.getKeyCode() == 38){
            jumper.up = true;
        }
        
        if(n == 'v') {
            jumper.stopJumping();
        }
        
        if(n == 'r') {
            frame.removeAllPlatforms();
        }
        
        if(n == 't'){
            jumper.fall();
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        char n = e.getKeyChar();
        
        if(n == 'a' || e.getKeyCode() == 37){
            frame.left = false;
        }
        if(n == 'd' || e.getKeyCode() == 39){
            frame.right = false;
        }
        if(n == 'a' || e.getKeyCode() == 37){
            jumper.left = false;
        }
        if(n == 'd' || e.getKeyCode() == 39){
            jumper.right = false;
        }
        if(n == ' ' || e.getKeyCode() == 38){
            jumper.up = false;
        }
    }
    
    //----------UNUSED----------------
    @Override
    public void keyTyped(KeyEvent e) {}
}
