/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
 * A class that is responsible for the display of Home Menu.
 * @author KhooChenNan
 *
 */
public class HomeMenu extends JComponent implements MouseListener, MouseMotionListener {

	/**
	 * The game title string "Breaker".
	 */
    private static final String GAME_TITLE = "Breaker";
    /**
	 * The game title string "Brick".
	 */
    private static final String GAME_TITLE2 = "Brick";
    /**
	 * The credits/author title string "By Khoo Chen Nan".
	 */
    private static final String CREDITS = "By Khoo Chen Nan";
    /**
	 * The Start title string "Start".
	 */
    private static final String START_TEXT = "Start";
    /**
	 * The Menu title string "Exit".
	 */
    private static final String MENU_TEXT = "Exit";
    /**
	 * The tutorial title string "Tutorial".
	 */
    private static final String TUTORIAL_TEXT = "Tutorial";

    /**
     * The background color attribute.
     */
    private static final Color BG_COLOR = Color.GREEN.darker();
    /**
     * The border color attribute.
     */
    private static final Color BORDER_COLOR = new Color(200,8,21); //Venetian Red
    /**
     * The dash border color attribute.
     */
    private static final Color DASH_BORDER_COLOR = new  Color(255, 216, 0); //School bus yellow
    /**
     * The text color attribute.
     */
    private static final Color TEXT_COLOR = new Color(255,255,255);
    /**
     * The clicked button color attribute.
     */
    private static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    /**
     * The clicked text color attribute.
     */
    private static final Color CLICKED_TEXT = Color.WHITE;
    /**
     * The border size attribute.
     */
    private static final int BORDER_SIZE = 5;
    /**
     * The dashes attribute.
     */
    private static final float[] DASHES = {12,6};

    /**
	 * A rectangular area object for the menu.
	 */
    private Rectangle menuFace; // Rectangle data type means it's an area within the specified coordinates
    /**
	 * A rectangular area object for the start button.
	 */
    private Rectangle startButton;
    /**
	 * A rectangular area object for the menu button.
	 */
    private Rectangle menuButton;
    /**
	 * A rectangular area object for the tutorial button.
	 */
    private Rectangle tutorialButton;

    /**
     * The border stroke with dashes.
     */
    private BasicStroke borderStoke;
    /**
     * The border stroke with no dashes.
     */
    private BasicStroke borderStoke_noDashes;

    /**
     * The game title's font.
     */
    private Font gameTitleFont;
    /**
     * The credits' font.
     */
    private Font creditsFont;
    /**
     * The button's font.
     */
    private Font buttonFont;

    /**
     * A GameFrame object that act as the owner.
     */
    private GameFrame owner;

    /**
     * A flag that checks whether "start" is clicked or not.
     */
    private boolean startClicked;
    /**
     * A flag that checks whether "menu" is clicked or not.
     */
    private boolean menuClicked;
    /**
     * A flag that checks whether "tutorial" is clicked or not.
     */
    private boolean tutorialClicked;

    /**
     * Constructor for the "HomeMenu.Java" class.  
     * @param owner A GameFrame object that acts as the owner.
     * @param area The area of the Home Menu.
     */
    public HomeMenu(GameFrame owner,Dimension area){

        this.setFocusable(true); // Indicates that the component "Home Menu" is set to gain the focus
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        /* Owner of "GameFrame" data type will be the Game Frame parameter we set */
        this.owner = owner;

        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        startButton = new Rectangle(btnDim);
        menuButton = new Rectangle(btnDim);
        tutorialButton = new Rectangle(btnDim);

        borderStoke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0);
        borderStoke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        
        gameTitleFont = new Font("Monospaced",Font.BOLD,40);
        creditsFont = new Font("Monospaced",Font.BOLD,20);
        buttonFont = new Font("Monospaced",Font.BOLD,startButton.height-2);
    }

    /**
     * A function that draws the button of the Tutorial Menu.
     */
    public void paint(Graphics g){
        drawMenu((Graphics2D)g);
    }

    /**
     * A function that paints the Home Menu.
     * @param g2d A Graphics2D object to be passed on.
     */
    public void drawMenu(Graphics2D g2d){

        drawContainer(g2d);

        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x,y);

        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    /**
     * A function take draws the container of Home Menu.
     * @param g2d A Graphics2D object to be passed on.
     */
    private void drawContainer(Graphics2D g2d){
        Color prev = g2d.getColor();
        
        g2d.setColor(BG_COLOR);
        g2d.fill(menuFace);
        
        Stroke tmp = g2d.getStroke();

        g2d.setStroke(borderStoke_noDashes);
        g2d.setColor(DASH_BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(borderStoke);
        g2d.setColor(BORDER_COLOR);
        g2d.draw(menuFace);

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
     * A function that draws the text of the Home Menu.
     * @param g2d A Graphics2D object to be passed on.
     */
    private void drawText(Graphics2D g2d){

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);

        int sX,sY;

        sY = (int)(menuFace.getHeight() / 4);

        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,sX,sY); // Prints out the string "Brick"
        g2d.drawString(GAME_TITLE2, sX+22, sY-30); // Prints out the string "Breaker"

        sX = (int)(menuFace.getWidth()/2) + 30;
        sY = 295;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,sX,sY);
    }

    /**
     * A function that draws the button of the Home Menu.
     * @param g2d A Graphics2D object to be passed on.
     */
    private void drawButton(Graphics2D g2d){

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(START_TEXT,frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(MENU_TEXT,frc);
        Rectangle2D testTxtRect = buttonFont.getStringBounds(TUTORIAL_TEXT,frc);

        g2d.setFont(buttonFont);

        /* "Start" Button */
        int x = (menuFace.width - startButton.width) / 2;
        int y =(int) ((menuFace.height - startButton.height) * 0.65) - 25;

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

        /* "Exit" Button */
        x = startButton.x;
        y = startButton.y;

        /* "x" has no modification, we're only moving the buttons up and down with start button as reference */
        y *= 1.4;

        menuButton.setLocation(x,y);

        x = (int)(menuButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int)(menuButton.getHeight() - mTxtRect.getHeight()) / 2;

        x += menuButton.x;
        y += menuButton.y + (startButton.height * 0.9);

        if(menuClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(menuButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(MENU_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(menuButton);
            g2d.drawString(MENU_TEXT,x,y);
        }

        /* Test Button */
        x = startButton.x;
        y = startButton.y;
        
        y *= 1.2;
        
        tutorialButton.setLocation(x,y);

        x = (int)(tutorialButton.getWidth() - testTxtRect.getWidth()) / 2;
        y = (int)(tutorialButton.getHeight() - testTxtRect.getHeight()) / 2;

        x += tutorialButton.x;
        y += tutorialButton.y + (menuButton.height * 0.9);

        if(tutorialClicked){
            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(tutorialButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(TUTORIAL_TEXT,x,y);
            g2d.setColor(tmp);
        }
        else{
            g2d.draw(tutorialButton);
            g2d.drawString(TUTORIAL_TEXT,x,y);
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
        else if(menuButton.contains(p)){
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        }
        else if(tutorialButton.contains(p)){
            owner.enableTutorialMenu();
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
        else if(menuButton.contains(p)){
            menuClicked = true;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        }
        else if (tutorialButton.contains(p)) {
        	tutorialClicked = true;
        	repaint(tutorialButton.x,tutorialButton.y,tutorialButton.width+1,tutorialButton.height+1);
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
        else if(menuClicked){
            menuClicked = false;
            repaint(menuButton.x,menuButton.y,menuButton.width+1,menuButton.height+1);
        }
        else if (tutorialClicked) {
        	tutorialClicked = false;
        	repaint(tutorialButton.x,tutorialButton.y,tutorialButton.width+1,tutorialButton.height+1);
        }
    }

    /* Declared as it's abstract but not used */
    @Override
    /**
     * An event handler that is responsible when the mouse is entered.  It is empty due to the concept of Abstraction.
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
        if(startButton.contains(p) || menuButton.contains(p) || tutorialButton.contains(p) )
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}
