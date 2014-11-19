/* TO DO LIST:
  - Replace the vertical movement control within movement block with seperate
    methods
  - Implement movement of object using the mouse
 */

package JumpTest;

import ScrollingPanel.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Jumper extends JPanel {
    public int colorCounter = 0, parentWidth, parentHeight,
            moveLeft = 0, moveRight = 0, moveSpeed = 0, count = 0, moveUp = 0, moveDown = 0,
            minX, minY, maxX, maxY;
    protected Timer engine, jumpTimer;
    public boolean left = false, right = false, up = false, 
            jumpingLeft = false, jumpingRight = false,
            falling = false, rising = false;
    public ScrollFrame frame;
    public boolean movementMode;
    private JLabel imgLbl;
    private final EngineHandler eh;
    private final Sound jumpSound;
    private Color c;
    private TrackerPanel trackerPanel;
    private ArrayList<Platform> platforms;
    private boolean intersecting, resting;
    private Platform intersectedPlatform;
    
    public Jumper()
    {
        c = Color.red; //Sets c to red
        
        setBackground(c); //Sets the background to c
        setBounds(400, 470, 50, 50); //Sets the bounds of the Jumper
        
        minX = getX(); //minX initialized to the starting xPosition
        minY = getY(); //minY initialized to the starting yPosition
        maxX = minX + 50; //maxX = minX plus 50
        maxY = minY + 50; //maxY = minY plus 50
        
        intersecting = false;
        resting = false;
        
        intersectedPlatform = null; //intersectedPlatform initialized
        
        eh = new EngineHandler(); 
        engine = new Timer(1000 / 60, eh);
        engine.start();
        
        jumpSound = new Sound("Randomize.wav");
        
        movementMode = true; //Defeault movement mode - controlled by keyboard
    }
    
    //Checks to see if the jumper object is intersecting with a platform
    public boolean platformIntersected(Platform p) {
            if(getBounds().intersects(p.getRelativeRectangle())) {
                intersectedPlatform = p;
                return true;
            } else {
                return false;
            }
    }
    
    //Sets an instance of the ScrollFrame object to f
    public void setFrame(ScrollFrame f) {
        frame = f;
    }
    
    //Sets an instance of the TrackerPanel object to tp
    public void setTrackerPanel(TrackerPanel tp) {
        trackerPanel = tp;
    }
    
    //Sets parentWidth (variable, used for the actual method) to pw
    public void setParentWidth(int pw) {
        //System.out.println(pw);
        parentWidth = pw;
    }
    
    //Sets parentWidth(variable) to parent container's width
    public void setParentWidth() {
        parentWidth = getParent().getWidth();
    }
    
    //Sets parentHeight(variable, used for the actual method) to ph
    public void setParentHeight(int ph) {
        parentHeight = ph;
    }
    
    //Sets parentHeight(variable) to parent container's height
    public void setParentHeight() {
        parentHeight = getParent().getHeight();
    }
    
    //Returns the value of moveLeft
    public int getMoveLeft() {
        return moveLeft;
    }
    
    //Returns the value of moveRight
    public int getMoveRight() {
        return moveRight;
    }
    
    //Returns the value of intersecting
    public Boolean isIntersecting() {
        return intersecting;
    }
    
    //Returns the value of resting
    public Boolean isResting() {
        return resting;
    }
    
    //Sets the value of the intersecting variable
    public void setIntersecting(Boolean b) {
        intersecting = b;
    }
    
    //Sets the value of the resting variable
    public void setResting(Boolean b) {
        resting = b;
    }
    
    //Makes the Jumper object fall by setting falling to true
    public void fall() {
        falling = true;
        rising = false;
    }
    
    //Makes the Jumper object rise by setting the moveUp variable to a non-zero
    //positive integer, setting the rising variable to true, and the resting
    //variable to false
    public void rise() {
        if(moveUp == 0 && !falling) {
            rising = true;
            resting = false;
            moveUp = 12;
        }
    }
    
    //Stops all vertical movement
    public void stopJumping() {
        if(rising || falling) {
            rising = false;
            falling = false;
            moveDown = 0;
            minY = getY();
        }
    }
    
    //Resets the jumper to a default location and resets all motion
    public void reset() {
        setLocation(400, 470);
        moveLeft = 0;
        moveRight = 0;
        jumpTimer.stop();
        minX = getX();
        minY = getY();
    }
    
    //Increments the moveLeft variable if passed true and increments the
        //moveRight variable if passed false
    public void accelerate(Boolean b) {
        if(b) {
            moveLeft++;
        } else {
            moveRight++;
        }
    }
    
    //Decrements the moveLeft variable if passed true and decrements the 
        //moveLeft variable if passed false
    public void decelerate(Boolean b) {
        if(b) {
            moveLeft--;
        } else {
            moveRight--;
        }
    }
    
    //Handler of the "engine" for the Jumper object
    private class EngineHandler implements ActionListener {
        @Override
        @SuppressWarnings({"BroadCatchBlock", "TooBroadCatch"})
        public void actionPerformed(ActionEvent e) {
            try {
                Thread.sleep(1);
                //Sets the array of platforms
                if(platforms == null) {
                    platforms = frame.getPlatforms();
                }
                
                //Calls the platform intersecting function, sets the intersecting
                //variable, and the intersectedPlatform variable
                for(int i = 0; i < platforms.size(); i++) {
                    if(platformIntersected(platforms.get(i)) == true) {
                        System.out.println(platforms.get(i).getName());
                        intersecting = true;
                        intersectedPlatform = platforms.get(i);
                        break;
                    } else {
                        intersecting = false;
                        if(intersectedPlatform != null) {
                            intersectedPlatform = null;
                        }
                    }
                }
                
                //Updates the trackerPanel
                try {
                    trackerPanel.setLabels(moveLeft, moveRight, count, left, 
                            right, minX, minY, intersectedPlatform.getName(), 
                            resting);
                } catch (Exception ex) {
                    trackerPanel.setLabels(moveLeft, moveRight, count, left, 
                            right, minX, minY, null, resting);
                }

            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
            
            setBackground(c);
            
            /*
             * This block does multiple checks to determine the movement of the
             * Jumper object. All movement is controlled via integer variables
             * which are incremented by serparate methods.
             * 
             * If both a left and right command are issued by the
             * player, no acceleration will take place and the object will
             * instead steadily slow to a stop.
             *
             * If the variable left or jumpingLeft are true, the object will 
             * move left and likewise for the opposite direction.
             * 
             * There is also a check to determine if the ScrollingFrame is at
             * one end or the other of the parent container. If not, the object
             * may only move within a set distance from one end of the parent 
             * container. If the frame IS at an end, the object is permitted to
             * move to the bounds of the parent container.
             * 
             * If the check for the moving container returns an error, the
             * jumper object defaults to being able to move to the edge of the
             * parent container.
             */
            
            count++; //Used for the acceleration/deceleration of the Jumper's
                     //movement
            
            minX = getX(); //Sets the minX variable to the Jumper's x position
            minY = getY(); //Sets the minY variable to the Jumper's y position
            maxX = minX + 50; //maxX = minX plus 50
            maxY = minY + 50; //maxY = minY plus 50
            
            //The next four blocks control the ramp up of the Jumper
            //object's movement speed.
            
            //If user is attempting to move block left and not right and 
            //moveLeft is less than 10, call the accelerate method (true = left)
            if(left && !right && moveLeft < 10) {
                if(rising || falling) {
                    if(count % 6 == 0) {
                        accelerate(true);
                    }
                } else {
                    if(count % 3 == 0) {
                        accelerate(true);
                    }
                }
            }
            
            //If user is attempting to move block right and not left and 
                //moveRight is less than 10, call the accelerate method (false = right)
            if(right && !left && moveRight < 10) {
                if(rising || falling) {
                    if(count % 6 == 0) {
                       accelerate(false);
                    }
                } else {
                    if(count % 3 == 0) {
                        accelerate(false);
                    }
                }
            }
            
            //If both left and right variables are true and the Jumper object
                //is not moving vertically, 
            if(left && right && !rising && !falling && moveRight > 0) {
                if(count % 4 == 0) {
                    decelerate(false);
                }
            } else if(!right && !rising && !falling && moveRight > 0) {
                if(count % 2  == 0) {
                    decelerate(false);
                }
            }
            
            if(left && right && !rising && !falling && moveLeft > 0) {
                if(count % 4 == 0) {
                    decelerate(true);
                }
            } else if(!left && !rising && !falling && moveLeft > 0) {
                if(count % 2 == 0) {
                    decelerate(true);
                }
            }
            
            //Controls the object's movement while airborne
            if((rising || falling) && moveLeft > 0 && moveRight > 0) {
                if(moveLeft > moveRight) {
                    moveLeft = moveLeft - moveRight;
                    moveRight = 0;
                } else if(moveRight > moveLeft) {
                    moveRight = moveRight - moveLeft;
                    moveLeft = 0;
                } 
            }
            
            //Controls the Jumper's falling speed
            if(falling) {
                moveDown++;
                rising = false;
                moveUp = 0;
            }
            
            //Calls the rise method and plays the jump sound effect if spacebar
            //is pressed while the object has no vertical velocity
            if(up && !rising && !falling) {
                rise();
                jumpSound.play();
            }
            
            //Left movement control
            if(left || jumpingLeft || moveLeft > 0) {
                setLocation(getX() - moveLeft, getY());
                try{
                    if(getX() <= 125 && !frame.isAtEnd()) {
                        setLocation(125, getY());
                    } else if(getX() <= 0 && frame.isAtEnd()) {
                        setLocation(0, getY());
                    }
                } catch(Exception ex) {
                    if(getX() <= 0) {
                        setLocation(0, getY());
                    }
                }
            }
            
            //Right movement control
            if(right || jumpingRight || moveRight > 0) {
                setLocation(getX() + moveRight, getY());
                try{
                    if(getX() > parentWidth - 200 && !frame.isAtEnd()) {
                        setLocation(parentWidth - 200, getY());
                    } else if(getX() >= (parentWidth - getWidth()) && frame.isAtEnd()) {
                        setLocation(parentWidth - getWidth(), getY());
                    }
                } catch(Exception ex) {
                    if(getX() > parentWidth - getWidth()) {
                        setLocation(parentWidth - getWidth(), getY());
                    }
                }
            }
            
            //Falling control
            if(moveDown > 0) {
                setLocation(getX(), getY() + moveDown);
                if(getY() > 470) {
                    setLocation(getX(), 470);
                    falling = false;
                    moveDown = 0;
                    jumpingLeft = false;
                    jumpingRight = false;
                }
                if(intersecting && maxY - moveDown <= intersectedPlatform.getMinY()) {
                    setLocation(getX(), intersectedPlatform.getMinY() - 49);
                    falling = false;
                    moveDown = 0;
                    resting = true;
                    jumpingLeft = false;
                    jumpingRight = false;
                } 
            }
            
            //If the Jumper moves off of a platform and isn't jumping, fall
            if(resting && !intersecting) {
                resting = false;
                fall();
            } 
            
            //Rising control
            if(moveUp > 0) {
                rising = true;
                if(count % 2 == 0) {
                    moveUp--;
                }
                setLocation(getX(), getY() - moveUp);
            } 
            if(moveUp == 0 && rising) {
                rising = false;
                fall();
            }
            
            //Jump movement control. If spacebar is pressed while left or right
            //variable is true, the Jumper will continue to move in that direction.
            if(rising && left) {
                jumpingLeft = true;
            }
            if(rising && right) {
                jumpingRight = true;
            }

            //Causes jumper to flash colors while moving and/or jumping
            if((left || right || jumpingLeft || jumpingRight || rising || falling) && !intersecting) {
                colorCounter++;
                
                if(colorCounter == 7) {
                    c = Color.red;
                } else if(colorCounter == 14) {
                    c = Color.darkGray;
                    colorCounter = 0;
                }
            } else if(intersecting) {
                c = Color.black;
            } else {
                c = Color.red;
            }
            
            //Resets count to 0 when it hits 100
            if(count > 100) {
                count = 0;
            }
            //Prevents moveLeft from becoming negative
            if(moveLeft < 0) {
                moveLeft = 0;
            } 
            //Prevents moveRight from becoming negative
            if(moveRight < 0) {
                moveRight = 0;
            }
            
        } 
    }        
}
    

