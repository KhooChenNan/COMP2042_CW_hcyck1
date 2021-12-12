package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * An abstract class which will then be inherited/extended by "Rubberball.Java".  It consists of multiple attributes which 
 * exists commonly in a ball and an abstract method which must be implemented later on if it were to be inherited.
 * @author KhooChenNan
 */
abstract public class Ball {

	/**
	 * Literally the face of the ball.  Declared in "Shape" data type.
	 */
    private Shape ballFace;
    /**
     * The center coordinate to be used later for construction of the ball.
     */
    private Point2D center;

    /* Declaration for the attributes */
    /**
     * Coordinates for the ball's "up" location.
     */
    public Point2D up;
    /**
     * Coordinates for the ball's "down" location.
     */
    public Point2D down;
    /**
     * Coordinates for the ball's "left" location.
     */
    public Point2D left;
    /**
     * Coordinates for the ball's "right" location.
     */
    public Point2D right;

    /**
     * The border's color to be passed later on.
     */
    private Color border;
    /**
     * The inner color to be passed later on.
     */
    private Color inner;

    /**
     * The horizontal speed of the ball.
     */
    private int speedX;
    /**
     * The vertical speed of the ball.
     */
    private int speedY;

    /**
     * Constructor for the class "Ball.Java".  
     * @param center Coordinate for the center value of the ball
     * @param radiusA First radius for the rubber ball.  To be passed in when constructing the rubber ball.
     * @param radiusB Second radius for the rubber ball.  To be passed in when constructing the rubber ball.
     * @param inner The inner color to be set.
     * @param border The border color to be set.
     */
    public Ball(Point2D center,int radiusA,int radiusB,Color inner,Color border){
        this.center = center;

        /* Initialization for the attributes */
        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(center.getX(),center.getY()-(radiusB / 2));
        down.setLocation(center.getX(),center.getY()+(radiusB / 2));

        left.setLocation(center.getX()-(radiusA /2),center.getY());
        right.setLocation(center.getX()+(radiusA /2),center.getY());


        ballFace = makeBall(center,radiusA,radiusB);
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;
    }

    /**
     * An abstract method which is then later implemented by "Rubberball.Java" as it is extended/inherits "Ball.Java".
     * @param center The center coordinates for the ball.
     * @param radiusA The primary radius of the ball.  This will then be modified later on.
     * @param radiusB The secondary radius of the ball.
     * @return Returns a specific Shape (To be decided on "Rubberball.Java")
     */
    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    /**
     * Sets the location of the ball.  As seen from the variable "center", it uses its method "setLocation" then it uses
     * the variable tmp's "setFrame".
     */
    public void move(){
        RectangularShape tmp = (RectangularShape) ballFace;
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        setPoints(w,h);

        ballFace = tmp;
    }

    /**
     * Sets the speed of the ball, regardless of horizontal or vertical speed.
     * @param x Horizontal speed of the ball.
     * @param y Vertical speed of the ball.
     */
    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }

    /**
     * Sets the horizontal speed of the ball.
     * @param s Horizontal speed of the ball.
     */
    public void setXSpeed(int s){
        speedX = s;
    }

    /**
     * Sets the vertical speed of the ball.
     * @param s Vertical speed of the ball.
     */
    public void setYSpeed(int s){
        speedY = s;
    }

    /**
     * Reverses the horizontal speed of the ball.  Mainly used when it comes into contact with something horizontally.
     */
    public void reverseX(){
        speedX *= -1;
    }

    /**
     * Reverses the vertical speed of the ball.  Mainly used when it comes into contact with something vertically.
     */
    public void reverseY(){
        speedY *= -1;
    }

    /**
     * Getter method for the color of the border.
     * @return Color of the border is returned.
     */
    public Color getBorderColor(){
        return border;
    }

    /**
     * Getter method for the inner color.
     * @return Inner color is returned.
     */
    public Color getInnerColor(){
        return inner;
    }

    /**
     * Getter method a specific position of coordinates. To be used on constructions.
     * @return Coordinates "center" is returned.
     */
    public Point2D getPosition(){
        return center;
    }

    /**
     * Getter method for the variable "ballFace" due to its "private" access specifier.
     * @return A shape "ballFace" is returned.
     */
    public Shape getBallFace(){
        return ballFace;
    }

    /**
     * Moves the ball to a specific location.  It takes in a "Point" type parameter "p" and then it sets the center location
     * with that variable.
     * @param p The location to be moved to.
     */
    public void moveTo(Point p){
        center.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((center.getX() -(w / 2)),(center.getY() - (h / 2)),w,h);
        ballFace = tmp;
    }

    /**
     * Sets the location to a specific coordinates according to the input parameters.
     * @param width Used for setting the "left" and "right" coordinates.
     * @param height Used for setting the "up" and "down" coordinates.
     */
    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }

    /**
     * Getter method for the horizontal speed.
     * @return Horizontal speed "speedX" is returned.
     */
    public int getSpeedX(){
        return speedX;
    }

    /**
     * Getter method for the vertical speed.
     * @return Vertical speed "speedY" is returned.
     */
    public int getSpeedY(){
        return speedY;
    }

    /**
     * Getter method for the variable "up" due to its "private" access specifiers.
     * @return Coordinates "up" is returned.
     */
    public Point2D ballUpGetter() {
    	return up;
    }
    
    /**
     * Getter method for the variable "down" due to its "private" access specifiers.
     * @return Coordinates "down" is returned.
     */
    public Point2D ballDownGetter() {
    	return down;
    }

    /**
     * Getter method for the variable "left" due to its "private" access specifiers.
     * @return Coordinates "left" is returned.
     */
    public Point2D ballLeftGetter() {
    	return left;
    }
    
    /**
     * Getter method for the variable "right" due to its "private" access specifiers.
     * @return Coordinates "right" is returned.
     */
    public Point2D ballRightGetter() {
    	return right;
    }
}
