package test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * A class that is essentially just a page that shows the instructions/controls of how to play the game.
 * @author KhooChenNan
 *
 */
public class TutorialMenu extends JComponent implements MouseListener, MouseMotionListener {
	
	/**
	 * The tutorial title string "How to play".
	 */
	private static final String TUTORIAL_TITLE = "How to play";
	/**
	 * The start text string "start".
	 */
	private static final String START_TEXT = "Start";
	/**
	 * The first instruction text.
	 */
	private static final String FIRST_INSTRUCTION = "1. Press 'SPACE' to launch the ball.";
	/**
	 * The second instruction text.
	 */
	private static final String SECOND_INSTRUCTION = "2. Press 'A' to move left.";
	/**
	 * The third instruction text.
	 */
	private static final String THIRD_INSTRUCTION = "3. Press 'D' to move right.";
	/**
	 * The fourth instruction text.
	 */
	private static final String FOURTH_INSTRUCTION = "4. Destroy all bricks by reflecting the ball.";
	
	/**
	 * A rectangular area object for the tutorial.
	 */
	private Rectangle tutorialFace;
	/**
	 * A rectangular area object for the start button.
	 */
    private Rectangle startButton;
    
    /**
     * The background color.
     */
    private static final Color BG_COLOR = Color.WHITE;
    /**
     * The text color.
     */
    private static final Color TEXT_COLOR = new Color(255,255,255);
    /**
     * The color of the button when the button is clicked.
     */
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    /**
     * The color of the text when the button is clicked.
     */
    private static final Color CLICKED_TEXT = TEXT_COLOR.brighter();

    /**
     * The font for the tutorial title.
     */
    private Font tutorialTitleFont;
    /**
     * The font for the button.
     */
    private Font buttonFont;
    /**
     * The font for the instructions.
     */
    private Font instructionsFont;

    /**
     * A GameFrame object.
     */
    private GameFrame owner;

    /**
     * A flag that checks whether the start button is clicked.
     */
    private boolean startClicked;

    /**
     * Constructor for the "TutorialMenu.Java" class.
     * @param owner The GameFrame object to be constructed on.
     * @param area The area of the Tutorial Menu.
     */
    public TutorialMenu(GameFrame owner,Dimension area){

        this.setFocusable(true); // Set focus
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        /* Owner of "GameFrame" data type will be the Game Frame parameter we set */
        this.owner = owner;

        tutorialFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        startButton = new Rectangle(btnDim);

        tutorialTitleFont = new Font ("Monospaced", Font.BOLD, 30);
        buttonFont = new Font("Monospaced",Font.BOLD,startButton.height-2);
        instructionsFont = new Font ("Monospace", Font.BOLD, 15);
    }

    /**
     * A function that paints the TutorialMenu.
     * @param g A Graphics object to be passed on.
     */
    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }

    /**
     * A function that draws the Tutorial Menu.
     * @param g2d A Graphics2D object to be passed on when constructing the container.
     */
    public void drawMenu(Graphics2D g2d){

        drawContainer(g2d);

        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = tutorialFace.getX();
        double y = tutorialFace.getY();

        g2d.translate(x,y);
        
        drawText(g2d);
        drawButton(g2d);

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    /**
     * A function that draws the container of the Tutorial Menu.
     */
    private void drawContainer(Graphics2D g2d){
        Color prev = g2d.getColor();
        
        g2d.setColor(BG_COLOR);
        g2d.fill(tutorialFace);
        
        Stroke tmp = g2d.getStroke();

        g2d.draw(tutorialFace);

        g2d.setStroke(tmp);
        try{
        	BufferedImage image = ImageIO.read(getClass().getResource("MainMenu2.jfif"));
        	g2d.drawImage(image, 0,0, this);
        }
        catch (IOException ex){
        	ex.printStackTrace();
        }
        g2d.setColor(prev);
    }

    /**
     * A function that draws the text of the Tutorial Menu.
     * @param g2d A Graphics2D object to be passed on.
     */
    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();
        
        Rectangle2D tutorialTitleRect = tutorialTitleFont.getStringBounds(TUTORIAL_TITLE,frc);

        int sX,sY;
        
        sX = (int)(tutorialFace.getWidth() - tutorialTitleRect.getWidth()) / 2;
        sY = 30;

        g2d.setFont(tutorialTitleFont);
        g2d.drawString(TUTORIAL_TITLE,sX,sY); // Prints out the string "How to play"
        
        sX -= sX - 20;
        sY = 70;
        
        g2d.setFont(instructionsFont);
        g2d.drawString(FIRST_INSTRUCTION,sX,sY);
        
        sY += 20;
        
        g2d.drawString(SECOND_INSTRUCTION,sX,sY);
        
        sY += 20;
        
        g2d.drawString(THIRD_INSTRUCTION,sX,sY);
        
        sY += 20;
        
        g2d.drawString(FOURTH_INSTRUCTION,sX,sY);
    }

    /**
     * A function that draws the button of the Tutorial Menu.
     * @param g2d A Graphics2D object to be passed on.
     */
    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);

        g2d.setFont(buttonFont);

        /* "Start" Button */
        int x = 150;
        int y = 200;

        startButton.setLocation(x,y);

        x = (int)(startButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(startButton.getHeight() - txtRect.getHeight()) / 2;

        x += startButton.x;
        y += startButton.y + (startButton.height * 0.9);

        if(startClicked){
            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(startButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(START_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(startButton);
            g2d.drawString(START_TEXT,x,y);
        }
    }

    @Override
    /**
     * An event handler that is responsible when the mouse is clicked.
     */
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
           owner.enableGameBoard();
        }
    }

    @Override
    /**
     * An event handler that is responsible when the mouse is pressed.
     */
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
    }

    @Override
    /**
     * An event handler that is responsible when the mouse is released.
     */
    public void mouseReleased(MouseEvent mouseEvent) {
        if(startClicked ){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
    }

    /* Declared as it's abstract but not used */
    @Override
    /**
     * An event handler that is responsible when the mouse is Entered.  It is empty due to the concept of Abstraction.
     */
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    /**
     * An event handler that is responsible when the mouse has exited.  It is empty due to the concept of Abstraction.
     */
    public void mouseExited(MouseEvent mouseEvent) {

    }


    @Override
    /**
     * An event handler that is responsible when the mouse is dragged.  It is empty due to the concept of Abstraction.
     */
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    /**
     * An event handler that is responsible when the mouse is moved.
     */
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());
    }
}
