package ScrollingPanel;

import java.awt.Container;
import java.awt.event.*;

public class ScrollingMouseWatcher implements MouseListener {
    
    protected Container container;
    protected ScrollFrame frame;
    
    public ScrollingMouseWatcher(Container c, ScrollFrame sf) {
        container = c; //New container
        frame = sf; //New ScrollFrame
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Calls the addPlatform() method of ScrollFrame and passes the mouse's
        //location relative to the ScrollFrame object
        frame.addPlatform(e.getPoint());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
