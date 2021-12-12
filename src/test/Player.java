package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * A model class for the player which is just a slab (rectangle area) that consists of multiple attributes such as color, speed, etc.
 * @author KhooChenNan
 *
 */
public class Player { //Used by PlayerController.java

	/**
	 * The border color.
	 */
    public static final Color BORDER_COLOR = Color.BLACK.darker().darker();
    /**
     * The inner color.
     */
    public static final Color INNER_COLOR = Color.GRAY;

    /**
     * The amount moved.
     */
    private static final int DEF_MOVE_AMOUNT = 7;

    // Controller shouldn't have any attributes as they should be in Model
    /**
     * The area of the player.
     */
    private Rectangle playerFace;
    /**
     * The coordinates of the ball.
     */
    private Point ballPoint;
    /**
     * The amount moved by the player.
     */
    private int moveAmount;
    /**
     * The minimum value to be passed as a parameter in constructor for calculations.
     */
    private int min;
    /**
     * The maximum value to be passed as a parameter in constructor for calculations.
     */
    private int max;

    /**
     * Constructor for the "Player.Java" class that takes in 4 input parameters: the coordinates of the ball, the width of player,
     * the height of player, and lastly, the area of the container.
     * @param ballPoint The coordinates of the ball
     * @param width The width of the player.
     * @param height The height of the player.
     * @param container The area of container.
     */
    public Player(Point ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        playerFace = makeRectangle(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;
    }

    /* Constructs the rectangle area */
    /**
     * A function that constructs the rectangle area.
     * @param width The width of the rectangle area.
     * @param height The height of the rectangle area.
     * @return Returns the rectangle area which is constructed through the width and height passed through as parameters.
     */
    private Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }

    /**
     * A function that tests if the specified coordinates is inside the boundary of the shape's position.
     * @param b A ball object.
     * @return Returns a boolean value according to the impact on the player.
     */
    public boolean impact(Ball b){
    	/* Tests if the specified coordinates is inside the boundary of the shape's position */
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.down) ;
    }
    
    /* Getter methods */
    /**
     * Getter method for "DEF_MOVE_AMOUNT" due to its "private" access specifier.
     * @return Returns the variable "DEF_MOVE_AMOUNT".
     */
    public int getDEF_MOVE_AMOUNT() {
    	return DEF_MOVE_AMOUNT;
    }
    
    /**
     * Getter method for "playerFace" due to its "private" access specifier.
     * @return Returns the variable "playerFace".
     */
    public Rectangle getPlayerFace(){
        return  playerFace;
    }
    
    /**
     * Getter method for "max" due to its "private" access specifier.
     * @return Returns the variable "max".
     */
    public int getMAX() {
    	return max;
    }
    
    /**
     * Getter method for "min" due to its "private" access specifier.
     * @return Returns the variable "min".
     */
    public int getMIN() {
    	return min;
    }
    
    /**
     * Getter method for "ballPoint" due to its "private" access specifier.
     * @return Returns the variable "ballPoint".
     */
    public Point ballPointGetter() {
    	return ballPoint;
    }
    
    /**
     * Getter method for "moveAmount" due to its "private" access specifier.
     * @return Returns the variable "moveAmount".
     */
    public int moveAmountGetter() {
    	return moveAmount;
    }
    
    /**
     * Setter method for "moveAmount" due to its "private" access specifier.
     * @param moveAmountInput The new value of moveAmount to be set.
     */
    public void moveAmountSetter(int moveAmountInput) {
    	this.moveAmount = moveAmountInput;
    }
}
