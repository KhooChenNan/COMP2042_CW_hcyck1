package test;

import java.awt.*;
import java.awt.geom.RectangularShape;

public class BallController {

	private Ball ball;
	private Shape ballFace;
	
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	
	public void setBallFace(Shape ballFace) {
		this.ballFace = ballFace;
	}
	
	public void setPoints(double width,double height){
		
        this.ball.ballUpGetter().setLocation(ball.getPosition().getX(),ball.getPosition().getY()-(height / 2)); // edit ballUpGetter and getPosition
        this.ball.ballDownGetter().setLocation(ball.getPosition().getX(),this.ball.getPosition().getY()+(height / 2));
        this.ball.ballLeftGetter().setLocation(ball.getPosition().getX()-(width / 2),ball.getPosition().getY());
        this.ball.ballRightGetter().setLocation(ball.getPosition().getX()+(width / 2),ball.getPosition().getY());
    }
	
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
