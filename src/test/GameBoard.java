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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;

/**
 * A class that is responsible for the brick breaker game itself.
 * @author KhooChenNan
 *
 */
public class GameBoard extends JComponent implements KeyListener,MouseListener,MouseMotionListener {

	/**
	 * A text string for resuming the game.
	 */
    private static final String CONTINUE = "Resume";
    /**
     * A text string for restarting the game.
     */
    private static final String RESTART = "Restart";
    /**
     * A text string for exiting the game.
     */
    private static final String EXIT = "Exit";
    /**
     * A text string for pausing the game.
     */
    private static final String PAUSE = "Pause Menu";
    /**
     * The text size.
     */
    private static final int TEXT_SIZE = 30;
    /**
     * The menu color.
     */
    private static final Color MENU_COLOR = new Color(51,153,255);

    /**
     * The width of the wall.
     */
    private static final int DEF_WIDTH = 600;
    /**
     * The height of the wall.
     */
    private static final int DEF_HEIGHT = 450;
    
    /**
     * The background color.
     */
    private static final Color BG_COLOR = Color.WHITE;

    /**
     * A Timer object for the game.
     */
    private Timer gameTimer;

    /**
     * A wall object.
     */
    private Wall wall;
    /**
     * A wall controller object.
     */
    private WallController wallController;
    /**
     * A ball object.
     */
    private Ball ball;

    /**
     * A text string to display a message.
     */
    private String message;

    /**
     * A boolean value that acts as a flag to check whether the Pause Menu is shown or not.
     */
    private boolean showPauseMenu;

    /**
     * The menu's font.
     */
    private Font menuFont;

    /**
     * The area for the rectangular button "continue".
     */
    private Rectangle continueButtonRect;
    /**
     * The area for the rectangular button "exit".
     */
    private Rectangle exitButtonRect;
    /**
     * The area for the rectangular button "restart".
     */
    private Rectangle restartButtonRect;
    /**
     * The length of a string.
     */
    private int strLen;

    /**
     * A DebugConsole object.
     */
    private DebugConsole debugConsole;

    /**
     * Constructor for the "GameBoard.Java" class.
     * @param owner A JFrame object that acts as the owner.
     */
    public GameBoard(JFrame owner){
        super();

        strLen = 0;
        showPauseMenu = false;

        menuFont = new Font("SanSerif",Font.PLAIN,TEXT_SIZE);

        this.initialize();
        message = "";
        wall = new Wall(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2,new Point(300,430));
        wallController = new WallController(wall, ball);
        wallController.makeLevels(new Rectangle(0,0,DEF_WIDTH,DEF_HEIGHT),30,3,6/2);

        debugConsole = new DebugConsole(owner,wall,this, wallController);
        //initialize the first level
        wall.nextLevel();

        gameTimer = new Timer(10,e ->{
            wallController.move();
            wallController.findImpacts();
            message = String.format("Bricks Destroyed: %d Balls: %d Score: %d",31 - wall.brickCountGetter(),wall.ballCountGetter(),scoreObtained());
            if(wall.ballLostGetter()){
                if(wallController.ballEnd()){
                    wallController.wallReset();
                    message = "GAME OVER";
                }
                wallController.ballReset();
                gameTimer.stop();
            }
            else if(wallController.isDone()){
                if(wall.hasLevel()){
                    message = "Go to Next Level";
                    gameTimer.stop();
                    wallController.ballReset();
                    wallController.wallReset();
                    wall.nextLevel();
                }
                else{
                    message = "ALL WALLS DESTROYED";
                    gameTimer.stop();
                }
            }

            repaint();
        });
    }
    
    /*
     * Number of bricks destroyed = 2 points
     * Number of lives left = 10 points
     */
    /**
     * A function that calculates the score obtained.
     * @return The current score obtained.
     */
    private int scoreObtained() {
    	int finalScore = 0;
    	
    	finalScore = (31*2) - (wall.brickCountGetter()*2) + (wall.ballCountGetter()*10);
    	
    	return finalScore;
    }

    /**
     * A function that initializes the GameBoard.
     */
    private void initialize(){
        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    /**
     * A function that paints the GameBoard.
     */
    public void paint(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.BLUE);
        g2d.drawString(message,200,225);

        drawBall(wall.ball,g2d);

        for(Brick b : wall.bricks)
            if(!b.isBroken())
                drawBrick(b,g2d);

        drawPlayer(wall.player,g2d);

        if(showPauseMenu)
            drawMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * A function that clears the GameBoard.
     * @param g2d A Graphics2D object to be passed on.
     */
    private void clear(Graphics2D g2d){
        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(tmp);
    }

    /**
     * A function that draws the brick's visual.
     * @param brick The brick type.
     * @param g2d A Graphics2D object to be passed on.
     */
    private void drawBrick(Brick brick,Graphics2D g2d){
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());


        g2d.setColor(tmp);
    }

    /**
     * A function that draws the ball's visual.
     * @param ball The ball to be drawn.
     * @param g2d A Graphics2D object to be passed on.
     */
    private void drawBall(Ball ball,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * A function that draws the player's visual.
     * @param p The player object to be drawn.
     * @param g2d A Graphics2D object to be passed on.
     */
    private void drawPlayer(Player p,Graphics2D g2d){
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    /**
     * A function that draws the Menu.
     * @param g2d A Graphics2D object to be passed on.
     */
    private void drawMenu(Graphics2D g2d){
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    /**
     * A function that obscures the GameBoard.
     * @param g2d A Graphics2D object to be passed on.
     */
    private void obscureGameBoard(Graphics2D g2d){

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,DEF_WIDTH,DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    /**
     * A function that draws the Pause Menu's visual.
     * @param g2d A Graphics2D object to be passed on.
     */
    private void drawPauseMenu(Graphics2D g2d){
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE,frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE,x,y);

        x = this.getWidth() / 8;
        y = this.getHeight() / 4;


        if(continueButtonRect == null){
            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE,frc).getBounds();
            continueButtonRect.setLocation(x,y-continueButtonRect.height);
        }

        g2d.drawString(CONTINUE,x,y);

        y *= 2;

        if(restartButtonRect == null){
            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x,y-restartButtonRect.height);
        }

        g2d.drawString(RESTART,x,y);

        y *= 3.0/2;

        if(exitButtonRect == null){
            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x,y-exitButtonRect.height);
        }

        g2d.drawString(EXIT,x,y);



        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    @Override
    /**
     * An event handler that is responsible when a key is typed.  It is to be implemented but empty due to the concept of Abstraction.
     */
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    /**
     * An event handler that is responsible when a key is pressed.
     */
    public void keyPressed(KeyEvent keyEvent) {
        switch(keyEvent.getKeyCode()){
            case KeyEvent.VK_A:
                wall.playerControllerGetter().moveLeft();
                break;
            case KeyEvent.VK_D:
                wall.playerControllerGetter().moveRight();
                break;
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu;
                repaint();
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if(!showPauseMenu)
                    if(gameTimer.isRunning())
                        gameTimer.stop();
                    else
                        gameTimer.start();
                break;
            case KeyEvent.VK_F1:
                if(keyEvent.isAltDown() && keyEvent.isShiftDown())
                    debugConsole.setVisible(true);
            default:
                wall.playerControllerGetter().stop();
        }
    }

    @Override
    /**
     * An event handler that is responsible when a key is released.
     */
    public void keyReleased(KeyEvent keyEvent) {
        wall.playerControllerGetter().stop();
    }

    @Override
    /**
     * An event handler that is responsible when a mouse is clicked.
     */
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(!showPauseMenu)
            return;
        if(continueButtonRect.contains(p)){
            showPauseMenu = false;
            repaint();
        }
        else if(restartButtonRect.contains(p)){
            message = "Restarting Game...";
            wallController.ballReset();
            wallController.wallReset();
            showPauseMenu = false;
            repaint();
        }
        else if(exitButtonRect.contains(p)){
            System.exit(0);
        }
    }

    @Override
    /**
     * An event handler that is responsible when a mouse is pressed.  It is to be implemented but empty due to the concept of Abstraction.
     */
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    /**
     * An event handler that is responsible when a mouse is released.  It is to be implemented but empty due to the concept of Abstraction.
     */
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    /**
     * An event handler that is responsible when a mouse is entered.  It is to be implemented but empty due to the concept of Abstraction.
     */
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    /**
     * An event handler that is responsible when a mouse has exited.  It is to be implemented but empty due to the concept of Abstraction.
     */
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    /**
     * An event handler that is responsible when a mouse is dragged.  It is to be implemented but empty due to the concept of Abstraction.
     */
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    /**
     * An event handler that is responsible when a mouse is moved.
     */
    public void mouseMoved(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if(exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        }
        else{
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    /**
     * A function that prints out the string "Focus Lost" when the window has lost focus.
     */
    public void onLostFocus(){
        gameTimer.stop();
        message = "Focus Lost";
        repaint();
    }

}
