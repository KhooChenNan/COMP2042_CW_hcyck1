package test;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

/**
 * A controller class for the "Player.Java" class to adhere according to the MVC (Model-View-Controller) design pattern.
 * @author KhooChenNan
 *
 */
public class PlayerController {

	/**
	 * A player object.
	 */
	private Player player;
	
	/**
	 * Constructor for the "playerController" class.
	 * @param player A "player" object.
	 */
	public PlayerController(Player player) {
		this.player = player;
	}
	
	/**
	 * Just moves the player/slab.
	 */
    public void move(){
    	int tempMIN = getMIN();
    	int tempMAX = getMAX();
        double x = player.ballPointGetter().getX() + player.moveAmountGetter();
        if(x < tempMIN || x > tempMAX)
            return;
        player.ballPointGetter().setLocation(x,player.ballPointGetter().getY());
        player.getPlayerFace().setLocation(player.ballPointGetter().x - (int)player.getPlayerFace().getWidth()/2,player.ballPointGetter().y);
    }
    
    /* Movement of the player are like coordinates in graphs.  Up & right = +ve while down & left = -ve */
    /**
     * Moves the player to the left.
     */
    public void moveLeft(){ // Causes player to move LEFT
    	player.moveAmountSetter(-getDEF_MOVE_AMOUNT());
    }

    /**
     * Moves the player to the right.
     */
    public void moveRight(){ // Causes player to move RIGHT
    	player.moveAmountSetter(getDEF_MOVE_AMOUNT());
    }

    /**
     * Stops the movement of the player
     */
    public void stop(){
    	player.moveAmountSetter(0);
    }
    
    /**
     * Moves the ball to a specific point.
     * @param p The point where the ball is being moved to.
     */
    public void moveTo(Point p){
    	player.ballPointGetter().setLocation(p);
    	player.getPlayerFace().setLocation(player.ballPointGetter().x - (int)player.getPlayerFace().getWidth()/2,player.ballPointGetter().y);
    }

    /* Getter methods */
    /**
     * Getter method for "playerFace".
     */
    public Rectangle getPlayerFace(){
    	return this.player.getPlayerFace();
    }
    
    /**
     * Getter method for "DEF_MOVE_AMOUNT".
     * @return
     */
    public int getDEF_MOVE_AMOUNT() {
    	return player.getDEF_MOVE_AMOUNT();
    }
	
    public int getMAX() {
    	return player.getMAX();
    }
    
    public int getMIN() {
    	return player.getMIN();
    }
    
}
