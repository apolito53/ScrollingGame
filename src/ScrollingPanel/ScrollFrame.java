package ScrollingPanel;

import JumpTest.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class ScrollFrame extends JFrame {
    protected JPanel cp;
        //The container that will be moving
    protected Timer engine;
        //The "engine"
    EngineHandler eh;
        //The handler for the "engine"
    protected Boolean left, right, moving, atEnd;
        //Boolean variables for movement control
    protected int rightmostPanelMinX;
        //keeps track of the x location of most recently added panel
    protected int worldOffsetX;
        //Current horizontal position of the world relative to the viewport
    protected Jumper jumper;
        //Points to the jumper object
    protected final int floorYPos = 470;
        //The "floor"
    protected int netMovement, movingLeft = 0, movingRight = 0;
        //Movement variables
    protected ArrayList<Platform> platformArray;
        //Holds the platforms in an arrayList
    protected ScrollingMouseWatcher smw;
        //Mouselistener for platform creation

    /*
     * Constructor, only sets Jumper object
     */
    public ScrollFrame(Jumper j) {
        jumper = j;
        
        defaultWindow();
        initComponents();
    }
    
    /*
     * User defined constructor for frame
     */
    public ScrollFrame(int x, int y, int width, int height, Jumper j) {
        jumper = j;
        initComponents();
        
        setBounds(x, y, width, height);
        setLayout(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    /*
     * Initialzes the frame to "default" settings
     */
    private void defaultWindow() {
        setBounds(550, 200, 1200, 600);
        setLayout(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    /*
     *  Initializes the moving container, relevant variables, and objects
     */
    private void initComponents() {
        //STREAMLINE: PASS IN AN ARRAY OF OBJECTS AND USE A FOR LOOP TO ADD THEM//
        
        cp = new JPanel();
        add(cp);
        cp.setLayout(null);
        cp.setBackground(Color.gray);
        cp.setBounds(0, 0, getWidth(), getHeight());
        
        platformArray = new ArrayList<>();
        worldOffsetX = 0;
        
        Platform p1 = new Platform(250, 400, "P1");
        addPlatformToWorld(p1);
        
        Platform p2 = new Platform(400, 350, "P2");
        addPlatformToWorld(p2);
        
        Platform p3 = new Platform(500, 300, "P3");
        addPlatformToWorld(p3);
        
        Platform p4 = new Platform(650, 250, "P4");
        addPlatformToWorld(p4);
        
        Platform p5 = new Platform(800, 300, "P5");
        addPlatformToWorld(p5);
        
        Platform p6 = new Platform(940, 380, "P6");
        addPlatformToWorld(p6);
        
        Platform p7 = new Platform(1030, 475, "P7");
        addPlatformToWorld(p7);
        
        smw = new ScrollingMouseWatcher(cp, this);
        cp.addMouseListener(smw);
        
        left = false;
        right = false;
        moving = false;
        atEnd = true;
        rightmostPanelMinX = 0;
        
        eh = new EngineHandler();
        engine = new Timer(1000 / 60, eh);
        engine.setCoalesce(true);
        
        /* This passes the instance of this frame to the Jumper object so it
         * can be used by the object */
        jumper.setFrame(this);
        jumper.setParentWidth(getWidth());
        jumper.setParentHeight(getHeight());
    }
    
    /*
     * Returns the variable "moving"
     */
    public boolean isMoving() {
        return moving;
    }

    public void startEngine() {
        if(!engine.isRunning()) {
            engine.start();
        }
    }

    private void addPlatformToWorld(Platform platform) {
        platform.updateScreenLocation(worldOffsetX);
        cp.add(platform);
        cp.setComponentZOrder(platform, 0);
        platformArray.add(platform);
    }

    private int getMinWorldOffset() {
        return Math.min(0, getWidth() - rightmostPanelMinX);
    }

    private void updatePlatformLocations() {
        for(int i = 0; i < platformArray.size(); i++) {
            platformArray.get(i).updateScreenLocation(worldOffsetX);
        }
    }
    
    /*
     * method that adds new Panels to the moving container
     */
    protected void addPanel() {
        try {
            int panelX = rightmostPanelMinX;
            rightmostPanelMinX += 600;
            cp.repaint(panelX, 0, 600, 600);
        } catch (Exception ex) {
            System.out.println("Error adding image to panel or adding panel to frame");
        }
    }
    
    /*
     * Method that adds a platform to the frame based on where the user clicks
     */
    protected void addPlatform(Point p) {
        try {
            Platform platform = new Platform(new Point(p.x - worldOffsetX, p.y));
            addPlatformToWorld(platform);
            cp.repaint(platform.getBounds());

        } catch(Exception ex) {
            System.out.println("Something went wrong, no platform created");
        }
    }
    
    protected void removeAllPlatforms() {
        try {
            for(int i = platformArray.size() - 1; i >= 0; i--) {
                Platform platform = platformArray.remove(i);
                Rectangle bounds = platform.getBounds();
                cp.remove(platform);
                cp.repaint(bounds);
            }
            
            //If the jumper object is on a platform when they are deleted, this
            //allows the object to fall like normal
            if(jumper.isIntersecting()) {
                jumper.setIntersecting(false);
            }
            if(jumper.isResting()) {
                jumper.setResting(false);
                jumper.fall();
            }
        } catch(Exception ex){
            System.out.println("Nope.");
        }
            
    }
    
    /*
     * Returns variable "atEnd"
     */
    public boolean isAtEnd() {
        return atEnd;
    }
    
    /*
     * Returns the platformArray ArrayList
     */
    public ArrayList<Platform> getPlatforms() {
        return platformArray;
    }
    
    /*
     * Handler for timer that constantly runs in background, the "engine"
     */
    private class EngineHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int startingX = worldOffsetX;
            int nextX = startingX;

            // The variable atEnd is true if the moving panel is at either
            // end of the specified boundaries and false otherwise
            atEnd = worldOffsetX == 0 || worldOffsetX == getMinWorldOffset();
            moving = false;

            jumper.tick();
            
            //Gets the left movement variable from the jumper object
            movingLeft = jumper.getMoveLeft();
            
            //Gets the right movement variable from the jumper object
            movingRight = jumper.getMoveRight();

            //Net value of the combined movement variables
            netMovement = Math.abs(movingLeft - movingRight);
            
            // Moves the moving container right when the Jumper object is moving
            // left and at the required boundary
            if((movingLeft - movingRight > 0)
                    && jumper.getX() <= 150) {
                nextX += netMovement;
                moving = true;
            }
            
            // Moves the moving container to the left when the Jumper object is
            // moving right and at the required boundary
            if((movingRight - movingLeft > 0)
                    && jumper.getX() >= getWidth() - 200) {
                nextX -= netMovement;
                moving = true;
            }
            
            // Prevents the moving container (cp) from moving off of the parent
            // container
            if(nextX > 0) {
                nextX = 0;
            } else if(nextX < getMinWorldOffset()) {
                nextX = getMinWorldOffset();
            }

            if(nextX != startingX) {
                worldOffsetX = nextX;
                updatePlatformLocations();
            }
            
            atEnd = worldOffsetX == 0 || worldOffsetX == getMinWorldOffset();
        }
    }
}
