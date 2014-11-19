package JumpTest;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame
{
    public MainFrame(int width, int height)
    {
        setSize(width, height);
        setVisible(true);
        getContentPane().setBackground(Color.gray);
        setLayout(null);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    public MainFrame()
    {
        setVisible(true);
        getContentPane().setBackground(Color.gray);
        setLayout(null);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
