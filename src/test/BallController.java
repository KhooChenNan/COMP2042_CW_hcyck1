package test;

import java.awt.*;
import java.awt.geom.RectangularShape;

/**
 * The Controller class for "Ball.Java" to adhere according the the MVC (Model-View-Controller) design pattern.
 * @author KhooChenNan
 *
 */
public class BallController {

	/**
	 * A Ball type data which decides that the ball controller belongs to this ball.
	 */
	private Ball ball;
	/**
	 * A shape type data which describes the ball in visual terms.
	 */
	private Shape ballFace;
	
	/**
	 * Since there is no constructor for this class, this function sets the ball which is passed in to be the ball of the ball controller class.
	 * So whenever there's a ball created, there will also be a ball controller class created with reference to that ball.
	 * @param ball The input ball to be used as a reference.
	 */
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	
	/**
	 * In terms of concept wise, it is the same as "setBall" function.  The ballFace will be referenced to the one passed to this
	 * function's parameter.
	 * @param ballFace The input ballFace to be used as a reference.
	 */
	public void setBallFace(Shape ballFace) {
		this.ballFace = ballFace;
	}
	
	/**
	 * Set the points of one specific ball by calling the "setLocation" function from "Ball.Java" class.  It takes in 2 parameters
	 * namely "width" and "height" to be used as a reference for calculation.
	 * @param width To be used for setting the location for left and right.
	 * @param height To be used for setting the location for up and down.
	 */
	public void setPoints(double width,double height){
        this.ball.ballUpGetter().setLocation(ball.getPosition().getX(),ball.getPosition().getY()-(height / 2));
        this.ball.ballDownGetter().setLocation(ball.getPosition().getX(),this.ball.getPosition().getY()+(height / 2));
        this.ball.ballLeftGetter().setLocation(ball.getPosition().getX()-(width / 2),ball.getPosition().getY());
        this.ball.ballRightGetter().setLocation(ball.getPosition().getX()+(width / 2),ball.getPosition().getY());
    }
	
	/**
	 * The logical operation of the ball when it comes to movement.  It gets the position and then sets the location.
	 */
	public void move(){
        RectangularShape tmp = (RectangularShape) ballFace;
        ball.getPosition().setLocation((this.ball.getPosition().getX() + this.ball.getSpeedX()),(this.ball.getPosition().getY() + this.ball.getSpeedY()));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((ball.getPosition().getX() -(w / 2)),(this.ball.getPosition().getY() - (h / 2)),w,h);
        setPoints(w,h);

        ballFace = tmp;
    }
}
