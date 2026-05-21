package ScrollingPanel;

import JumpTest.*;
import java.awt.Color;
import javax.swing.SwingUtilities;

public class Runner {

    public static void main(String[] args){
        SwingUtilities.invokeLater(Runner::start);
    }

    private static void start() {
        Jumper jumper = new Jumper();
        ScrollFrame mainFrame = new ScrollFrame(jumper);
        TrackerPanel tp = new TrackerPanel();
        ScrollingKeyWatcher skw = new ScrollingKeyWatcher(mainFrame, jumper);

        mainFrame.addKeyListener(skw);

        mainFrame.getContentPane().setBackground(Color.white);

        for(int i = 0; i < 4; i++) {
            mainFrame.addPanel();
        }

        mainFrame.getContentPane().add(jumper);
        mainFrame.getContentPane().add(tp);

        jumper.setTrackerPanel(tp);

        mainFrame.getContentPane().setComponentZOrder(jumper, 0);
        mainFrame.getContentPane().setComponentZOrder(tp, 1);

        mainFrame.requestFocusInWindow();
        mainFrame.startEngine();
    }
}
