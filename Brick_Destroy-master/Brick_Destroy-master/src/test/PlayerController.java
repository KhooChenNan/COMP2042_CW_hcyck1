package test;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;

public class PlayerController {

	private Player player;
	
	/* Initialization */
	public PlayerController(Player player) {
		this.player = player;
	}
	
	/* Movement of the player */
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
    public void moveLeft(){ // Causes player to move LEFT
    	player.moveAmountSetter(-getDEF_MOVE_AMOUNT());
    }

    public void moveRight(){ // Causes player to move RIGHT
    	player.moveAmountSetter(getDEF_MOVE_AMOUNT());
    }

    /* Stops the movement of the player */
    public void stop(){
    	player.moveAmountSetter(0);
    }
    
    /* Move the ball to a specific point */
    public void moveTo(Point p){
    	player.ballPointGetter().setLocation(p);
    	player.getPlayerFace().setLocation(player.ballPointGetter().x - (int)player.getPlayerFace().getWidth()/2,player.ballPointGetter().y);
    }

    /* Getter methods */
    public void getPlayerFace(){
    	this.player.getPlayerFace();
    }
    
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
