package ScrollingPanel;

import JumpTest.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ScrollFrame extends JFrame {
    protected Container cp;
        //The container that will be moving
    protected Timer engine;
        //The "engine"
    EngineHandler eh;
        //The handler for the "engine"
    protected Boolean left, right, moving, atEnd;
        //Boolean variables for movement control
    protected int rightmostPanelMinX;
        //keeps track of the x location of most recently added panel
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
        setIgnoreRepaint(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    /*
     *  Initializes the moving container, relevant variables, and objects
     */
    private void initComponents() {
        //STREAMLINE: PASS IN AN ARRAY OF OBJECTS AND USE A FOR LOOP TO ADD THEM//
        
        cp = new Container();
        add(cp);
        cp.setLayout(null);
        cp.setBackground(Color.white);
        
        platformArray = new ArrayList<>();
        
        Platform p1 = new Platform(250, 400, "P1");
        cp.add(p1);
        cp.setComponentZOrder(p1, 0);
        platformArray.add(p1);
        
        Platform p2 = new Platform(400, 350, "P2");
        cp.add(p2);
        cp.setComponentZOrder(p2, 0);
        platformArray.add(p2);
        
        Platform p3 = new Platform(500, 300, "P3");
        cp.add(p3);
        cp.setComponentZOrder(p3, 0);
        platformArray.add(p3);
        
        Platform p4 = new Platform(650, 250, "P4");
        cp.add(p4);
        cp.setComponentZOrder(p4, 0);
        platformArray.add(p4);
        
        Platform p5 = new Platform(800, 300, "P5");
        cp.add(p5);
        cp.setComponentZOrder(p5, 0);
        platformArray.add(p5);
        
        Platform p6 = new Platform(940, 380, "P6");
        cp.add(p6);
        cp.setComponentZOrder(p6, 0);
        platformArray.add(p6);
        
        Platform p7 = new Platform(1030, 475, "P7");
        cp.add(p7);
        cp.setComponentZOrder(p7, 0);
        platformArray.add(p7);
        
        smw = new ScrollingMouseWatcher(cp, this);
        cp.addMouseListener(smw);
        
        left = false;
        right = false;
        moving = false;
        atEnd = true;
        rightmostPanelMinX = 0;
        
        eh = new EngineHandler();
        engine = new Timer(1000 / 60, eh);
        engine.start();
        
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
    
    /*
     * method that adds new Panels to the moving container
     */
    protected void addPanel() {
        Panel panel;
        panel = new Panel(Color.gray);
        BufferedImage img;
        try {
            img = ImageIO.read(this.getClass().getResource("mario.png"));
            JLabel imgLbl = new JLabel(new ImageIcon(img));
            imgLbl.setSize(600, 600);
            //panel.add(imgLbl);
        } catch (IOException ex) {
            System.out.println("Error adding image to panel or adding panel to frame");
        }
        
        try {
            cp.add(panel);
            panel.setBounds(rightmostPanelMinX, 0, 600, 600);
            rightmostPanelMinX += panel.getWidth();
            cp.setSize(rightmostPanelMinX, 600);
        } catch (Exception ex) {
            System.out.println("Error adding image to panel or adding panel to frame");
        }
    }
    
    /*
     * Method that adds a platform to the frame based on where the user clicks
     */
    protected void addPlatform(Point p) {
        try {
            Platform platform = new Platform(p);
            cp.add(platform);
            cp.setComponentZOrder(platform, 0);
            platformArray.add(platform);
            
        } catch(Exception ex) {
            System.out.println("Something went wrong, no platform created");
        }
    }
    
    protected void removeAllPlatforms() {
        try {
            for(int i = platformArray.size() - 1; i >= 0; i--) {
                platformArray.get(i).setVisible(false);
                platformArray.remove(i);
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
    public ArrayList getPlatforms() {
        return platformArray;
    }
    
    /*
     * Handler for timer that constantly runs in background, the "engine"
     */
    private class EngineHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // The variable atEnd is true if the moving panel is at either
            // end of the specified boundaries and false otherwise
            atEnd = cp.getX() == 0 || cp.getX() == -cp.getWidth() + getWidth();
            
            //Gets the left movement variable from the jumper object
            movingLeft = jumper.getMoveLeft();
            
            //Gets the right movement variable from the jumper object
            movingRight = jumper.getMoveRight();

            //Net value of the combined movement variables
            netMovement = Math.abs(movingLeft - movingRight);
            
            // Moves the moving container right when the Jumper object is moving
            // left and at the required boundary
            if((movingLeft - movingRight > 0)
                    && jumper.getLocation().x <= 150) {
                cp.setLocation(cp.getX() + netMovement, cp.getY());
                moving = true;
            }
            
            // Moves the moving container to the left when the Jumper object is
            // moving right and at the required boundary
            if((movingRight - movingLeft > 0)
                    && jumper.getLocation().x >= getWidth() - 200) {
                cp.setLocation(cp.getX() - netMovement, cp.getY());
                moving = true;
            }
            
            // Prevents the moving container (cp) from moving off of the parent
            // container
            if(cp.getX() > 0) {
                cp.setLocation(0, cp.getY());
            } else if(cp.getX() < -cp.getWidth() + getWidth()) {
                cp.setLocation(-cp.getWidth() + getWidth(), cp.getY());
            }
            
            for(int i = 0; i < platformArray.size(); i++) { 
                //System.out.println(platformArray.get(i).getRelativeLocation());
            }
            
            cp.repaint();
        }
    }
}
