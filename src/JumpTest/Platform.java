package JumpTest;

import java.awt.*;
import javax.swing.*;

public class Platform extends JPanel
{
    final private String name;
    private Rectangle rekt;
    
    //Platform generation via mouse input
    public Platform(Point p) {
        int x = p.x;
        int y = p.y;
        setBackground(Color.blue);
        setBounds(x, y, 75, 15);
        name = "UserGenerated";
    }
    
    //Hardcoded platform creation
    public Platform(int x, int y, String n) {
        setBackground(Color.black);
        setBounds(x, y, 75, 15);
        name = n;
    }
    
    //Gets the location of the platform relative to the window
    public Point getRelativeLocation() {
        int x, pX, y, pY, rX, rY;
        x = getX();
        pX = getParent().getX();
        y = getY();
        pY = getParent().getY();
        rX = x + pX;
        rY = y + pY;
        Point relativePoint = new Point(rX, rY);
        return relativePoint;
    }
    
    //Returns the minimum x-coordinate of the platform
    public int getMinX() {
        return getX();
    }
    
    //Returns the maximum x-coordinate of the platform
    public int getMaxX() {
        return getX() + getWidth();
    }
    
    //Returns the minimum y-coordinate of the platform
    public int getMinY() {
        return getY();
    }
    
    //Returns the maxmimum y-coordinate of the platform
    public int getMaxY() {
        return getY() + getHeight();
    }
    
    @Override
    //Returns the name of the platform (p1, p2, etc)
    public String getName() {
        return name;
    }
    
    //Sets the rectangle used for colision
    public Rectangle getRelativeRectangle() {
        rekt = new Rectangle();
        int x = getRelativeLocation().x, 
                y = getRelativeLocation().y;
        rekt.setBounds(x, y, 75, 15);
        return rekt;
    }
}    
