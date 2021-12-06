package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;


public class Player { //Used by PlayerController.java

    public static final Color BORDER_COLOR = Color.BLACK.darker().darker();
    public static final Color INNER_COLOR = Color.GRAY;

    private static final int DEF_MOVE_AMOUNT = 5;

    // Controller shouldn't have any attributes as they should be in Model
    private Rectangle playerFace;
    private Point ballPoint;
    private int moveAmount;
    private int min;
    private int max;

    public Player(Point ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        playerFace = makeRectangle(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;
    }

    /* Constructs the rectangle area */
    private Rectangle makeRectangle(int width,int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }

    public boolean impact(Ball b){
    	/* Tests if the specified coordinates is inside the boundary of the shape's position */
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.down) ;
    }
    
    /* Getter methods */
    public int getDEF_MOVE_AMOUNT() {
    	return DEF_MOVE_AMOUNT;
    }
    
    public Rectangle getPlayerFace(){
        return  playerFace;
    }
    
    public int getMAX() {
    	return max;
    }
    
    public int getMIN() {
    	return min;
    }
    
    public Point ballPointGetter() {
    	return ballPoint;
    }
    
    public int moveAmountGetter() {
    	return moveAmount;
    }
    
    /* Setter methods */
    public void moveAmountSetter(int moveAmountInput) {
    	this.moveAmount = moveAmountInput;
    }
}
