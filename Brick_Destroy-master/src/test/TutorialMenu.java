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

public class TutorialMenu extends JComponent implements MouseListener, MouseMotionListener {
	
	private static final String TUTORIAL_TITLE = "How to play";
	private static final String START_TEXT = "Start";
	private static final String FIRST_INSTRUCTION = "1. Press 'SPACE' to launch the ball.";
	private static final String SECOND_INSTRUCTION = "2. Press 'A' to move left.";
	private static final String THIRD_INSTRUCTION = "3. Press 'D' to move right.";
	private static final String FOURTH_INSTRUCTION = "4. Destroy all bricks by reflecting the ball.";
	
	private Rectangle tutorialFace;
    private Rectangle startButton;
    
    private static final Color BG_COLOR = Color.WHITE;
    private static final Color TEXT_COLOR = new Color(255,255,255);
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    private static final Color CLICKED_TEXT = TEXT_COLOR.brighter();

    private Font tutorialTitleFont;
    private Font buttonFont;
    private Font instructionsFont;

    private GameFrame owner;

    private boolean startClicked;

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

    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }

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
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
           owner.enableGameBoard();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p)){
            startClicked = true;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);

        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(startClicked ){
            startClicked = false;
            repaint(startButton.x,startButton.y,startButton.width+1,startButton.height+1);
        }
    }

    /* Declared as it's abstract but not used */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(startButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());
    }
}
