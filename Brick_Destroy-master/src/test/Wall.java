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

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;


public class Wall {

    public static final int LEVELS_COUNT = 4;

    public static final int CLAY = 1;
    public static final int STEEL = 2;
    public static final int CEMENT = 3;
    public static final int GRAPHITE = 4;
    public static final int VIBRANIUM = 5;

    private Random rnd; // Random number generator 
    private Rectangle area;

    Brick[] bricks;
    Ball ball;
    Player player;

    private Brick[][] levels;
    private int level;
    private PlayerController playerController;

    private Point startPoint;
    private int brickCount;
    private int ballCount;
    private boolean ballLost;

    /* Wall constructor */    
    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

    	/* Coordinates of the starting point = the coordinates of the ball position */
        this.startPoint = new Point(ballPos);
        
        //levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;

        ballCount = 3; // Number of balls/lives
        ballLost = false;

        rnd = new Random(); // Initializes variable "rnd"

        makeBall(ballPos); // Calling the make ball function and makes the ball at the starting coordinates
        int speedX,speedY;
        do{
        	speedX = rnd.nextInt(5) - 2; // Speed X randomly generated between 0-5 (inclusive) -  2
        }while(speedX == 0); // Makes sure that the horizontal speed of ball will never be 0 or else it'll keep randomly generating a number
        do{
            speedY = -rnd.nextInt(3); // Speed Y randomly generated between 0-3 (inclusive) and then multiplies by -1
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY); // Sets horizontal and vertical speed according to the randomly generated speed

        player = new Player((Point) ballPos.clone(),100,10, drawArea);

        playerController = new PlayerController(player);
        
        area = drawArea;
    }
    
    public void nextLevel(){
        bricks = levels[level++];
        this.brickCount = bricks.length;
    }
    
    public boolean hasLevel(){
        return level < levels.length;
    }
    
    /* Makes the ball */
    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }
    
    /* Getter methods */
    public Player playerGetter() {
    	return player;
    }
    
    public PlayerController playerControllerGetter() {
    	return playerController;
    }
    
    public Ball ballGetter() {
    	return ball;
    }
    
    public int brickCountGetter() {
    	return brickCount;
    }
    
    public int ballCountGetter() {
    	return ballCount;
    }
    
    public Brick[] bricksGetter() {
    	return bricks;
    }
    
    public boolean ballLostGetter() {
    	return ballLost;
    }
    
    public Point startPointGetter() {
    	return startPoint;
    }
    
    public Random rndGetter() {
    	return rnd;
    }
    
    public Rectangle areaGetter() {
    	return area;
    }
    
    public int levelGetter() {
    	return level;
    }
    
    /* Setter methods */
    public void levelsSetter(Brick[][] levelsInput){
    	this.levels = levelsInput;
    }
    
    
    public void ballSetter(Ball ballInput) {
    	this.ball = ballInput;
    }
    
    public void brickCountSetter(int brickCountInput) {
    	this.brickCount = brickCountInput;
    }
    
    public void ballCountSetter(int ballCountInput) {
    	this.ballCount = ballCountInput;
    }
    
    public void bricksSetter(Brick[] bricksInput) {
    	this.bricks = bricksInput;
    }
    
    public void ballLostSetter(boolean ballLostInput) {
    	this.ballLost = ballLostInput;
    }
    
    public void startPointSetter(Point startPointInput) {
    	this.startPoint = startPointInput;
    }
    
    public void rndSetter(Random rndInput) {
    	this.rnd = rndInput;
    }
    
    public void areaSetter(Rectangle areaInput) {
    	this.area = areaInput;
    }
    
    public void levelSetter(int levelInput) {
    	this.level = levelInput;
    }

}
