package ScrollingPanel;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TrackerPanel extends JPanel {
    
    protected JLabel moveLeftLbl, moveRightLbl, countLbl, leftLbl, rightLbl,
            xLbl, yLbl, intersectingLbl, restingLbl;

    public TrackerPanel() {
        setBounds(0, 0, 115, 200);
        
        moveLeftLbl = new JLabel();
        add(moveLeftLbl);

        moveRightLbl = new JLabel();
        add(moveRightLbl);
                
        countLbl = new JLabel();
        add(countLbl);
        
        leftLbl = new JLabel();
        add(leftLbl);
        
        rightLbl = new JLabel();
        add(rightLbl);
        
        xLbl = new JLabel();
        add(xLbl);
        
        yLbl = new JLabel();
        add(yLbl);
        
        intersectingLbl = new JLabel();
        add(intersectingLbl);
        
        restingLbl = new JLabel();
        add(restingLbl);
    }
    
    public void setLabels(int moveLeft, int moveRight, int count, boolean left, boolean right, int xPos, int yPos, String intersecting, boolean resting) {
        moveLeftLbl.setText("moveLeft = " + moveLeft);
        moveRightLbl.setText("moveRight = " + moveRight);
        countLbl.setText("count = " + count);
        leftLbl.setText("left = " + left);
        rightLbl.setText("right = " + right);
        xLbl.setText("xPos = " + xPos);
        yLbl.setText("yPos = " + yPos);
        
        if(intersecting != null) {
            intersectingLbl.setText("Intersecting: " + intersecting);
        } else {
            intersectingLbl.setText("Not intersecting");
        }
        
        restingLbl.setText("Resting = " + resting);
    }
}
