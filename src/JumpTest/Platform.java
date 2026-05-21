package JumpTest;

import java.awt.*;
import javax.swing.*;

public class Platform extends JPanel
{
    final private String name;
    private final int worldX;
    private final int worldY;
    private Rectangle rekt;
    
    //Platform generation via mouse input
    public Platform(Point p) {
        int x = p.x;
        int y = p.y;
        worldX = x;
        worldY = y;
        setBackground(Color.blue);
        setBounds(x, y, 75, 15);
        rekt = new Rectangle();
        name = "UserGenerated";
    }
    
    //Hardcoded platform creation
    public Platform(int x, int y, String n) {
        worldX = x;
        worldY = y;
        setBackground(Color.black);
        setBounds(x, y, 75, 15);
        rekt = new Rectangle();
        name = n;
    }

    public void updateScreenLocation(int worldOffsetX) {
        setLocation(worldX + worldOffsetX, worldY);
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
        int x = getX() + getParent().getX();
        int y = getY() + getParent().getY();
        rekt.setBounds(x, y, getWidth(), getHeight());
        return rekt;
    }
}    
