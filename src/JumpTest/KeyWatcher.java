package JumpTest;

import java.awt.event.*;

public class KeyWatcher implements KeyListener
{
    private final Jumper jumper;
    
    public KeyWatcher(Jumper j)
    {
        jumper = j;
    }
    
    @Override
    public void keyPressed(KeyEvent e) 
    {
        char n = e.getKeyChar();
        
        if(n == 'a'){
            jumper.left = true;
        }
        
        if(n == 'd'){
            jumper.right = true;
        }
        
        if(n == ' '){
            jumper.up = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        char n = e.getKeyChar();
        
        if(n == 'a'){
            jumper.left = false;
        }
        
        if(n == 'd'){
            jumper.right = false;
        }
        
        if(n == ' '){
            jumper.up = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e){}
}
