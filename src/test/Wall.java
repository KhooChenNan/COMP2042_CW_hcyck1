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

/**
 * The "model" class for "Wall.Java" after refactoring it to adhere according to the MVC (Model-View-Controller) design pattern.
 * @author KhooChenNan
 *
 */
public class Wall {

	/**
	 * The number of different levels
	 */
    public static final int LEVELS_COUNT = 4;

    /**
     * A set value for each the Clay brick type.
     */
    public static final int CLAY = 1;
    /**
     * A set value for each the Steel brick type.
     */
    public static final int STEEL = 2;
    /**
     * A set value for each the Cement brick type.
     */
    public static final int CEMENT = 3;
    /**
     * A set value for each the Graphite brick type.
     */
    public static final int GRAPHITE = 4;
    /**
     * A set value for each the Vibranium brick type.
     */
    public static final int VIBRANIUM = 5;

    private Random rnd; // Random number generator
    private Rectangle area;

    /**
     * The bricks of the brick breaker game.
     */
    Brick[] bricks;
    /**
     * The ball of the brick breaker game.
     */
    Ball ball;
    /**
     * The player/moving slab of the brick breaker game.
     */
    Player player;

    private Brick[][] levels;
    /**
     * The level of the game.
     */
    private int level;
    /**
     * The controller for the player adhering to the MVC design pattern.
     */
    private PlayerController playerController;

    /**
     * The starting coordinates.
     */
    private Point startPoint;
    /**
     * The number of bricks.
     */
    private int brickCount;
    /**
     * The number of balls.
     */
    private int ballCount;
    /**
     * A boolean type variable that checks whether the ball is lost or not.
     */
    private boolean ballLost;

    /**
     * The constructor for the "Wall" class.  It takes in 5 input parameters: drawArea, brickCount, lineCount, brickDimensionRatio, and ballPos.
     * It sets the coordinates of the starting point to the coordinates of the ball's position.  It then sets the variable "ballCount" to 3 as 
     * that is the initial life count given.  A ball will then be made by passing in the starting point of coordinates.  Lastly, a player and
     * playerController for the player that was just created will be initialized too.
     * @param drawArea The area (a coordinated space) of the game.
     * @param brickCount The number of bricks for the level.
     * @param lineCount The number of lines of bricks for the level.
     * @param brickDimensionRatio The brick to dimension ratio.
     * @param ballPos The coordinates of the ball's position.
     */
    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

    	/* Coordinates of the starting point = the coordinates of the ball position */
        this.startPoint = new Point(ballPos);
        
        level = 0;

        ballCount = 3; // Number of balls/lives
        ballLost = false; // Logically speaking, if ballLost = true, means you'll start off the game by losing one life.

        rnd = new Random(); // Initializes variable "rnd"

        makeBall(ballPos); // Calling the make ball function and makes the ball at the starting coordinates
        int speedX,speedY;
        do{
        	speedX = rnd.nextInt(5) - 2; // Speed X randomly generated between 0-5 (inclusive) -  2.  If hard-coded, the launching angle will be static.
        }while(speedX == 0); // Makes sure that the horizontal speed of ball will never be 0 or else it'll keep randomly generating a number
        do{
        	speedY = -5; // Hard-coded for consistency
        }while(speedY == 0);

        ball.setSpeed(speedX,speedY); // Sets horizontal and vertical speed according to the randomly generated speed

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        playerController = new PlayerController(player);
        
        area = drawArea;
    }
    
    /**
     * A function that increases level by setting the bricks data type to the levels and the brickCount to the bricks' length.
     */
    public void nextLevel(){
        bricks = levels[level++];
        this.brickCount = bricks.length;
    }
    
    /**
     * A boolean data type that checks the level.
     * @return True if the variable "level" is smaller than "levels.length".  Else, it returns false.
     */
    public boolean hasLevel(){
        return level < levels.length;
    }
    
    /**
     * Initializes a new ball by passing in an input parameter: The coordinates of the ball.
     * @param ballPos The coordinates of the ball.
     */
    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }
    
    /**
     * Getter method for the variable "player" due to its "private" access specifier.
     * @return The variable "player" is returned.
     */
    public Player playerGetter() {
    	return player;
    }
    
    /**
     * Getter method for the variable "playerController" due to its "private" access specifier.
     * @return The variable "playerController" is returned.
     */
    public PlayerController playerControllerGetter() {
    	return playerController;
    }
    
    /**
     * Getter method for the variable "ball" due to its "private" access specifier.
     * @return The variable "ball" is returned.
     */
    public Ball ballGetter() {
    	return ball;
    }
    
    /**
     * Getter method for the variable "brickCount" due to its "private" access specifier.
     * @return The variable "brickCount" is returned.
     */
    public int brickCountGetter() {
    	return brickCount;
    }
    
    /**
     * Getter method for the variable "ballCount" due to its "private" access specifier.
     * @return The variable "ballCount" is returned.
     */
    public int ballCountGetter() {
    	return ballCount;
    }
    
    /**
     * Getter method for the variable "bricks" due to its "private" access specifier.
     * @return The variable "bricks" is returned.
     */
    public Brick[] bricksGetter() {
    	return bricks;
    }
    
    /**
     * Getter method for the variable "ballLost" due to its "private" access specifier.
     * @return The variable "ballLost" is returned.
     */
    public boolean ballLostGetter() {
    	return ballLost;
    }
    
    /**
     * Getter method for the variable "startPoint" due to its "private" access specifier.
     * @return The variable "startPoint" is returned.
     */
    public Point startPointGetter() {
    	return startPoint;
    }
    
    /**
     * Getter method for the variable "rnd" due to its "private" access specifier.
     * @return The variable "rnd" is returned.
     */
    public Random rndGetter() {
    	return rnd;
    }
    
    /**
     * Getter method for the variable "area" due to its "private" access specifier.
     * @return The variable "area" is returned.
     */
    public Rectangle areaGetter() {
    	return area;
    }
    
    /**
     * Getter method for the variable "level" due to its "private" access specifier.
     * @return The variable "level" is returned.
     */
    public int levelGetter() {
    	return level;
    }
    
    /**
     * Setter method for the variable "levels" due to its "private" access specifier.
     * @param levelsInput The input parameter "levels" to be set.
     */
    public void levelsSetter(Brick[][] levelsInput){
    	this.levels = levelsInput;
    }
    
    /**
     * Setter method for the variable "ball" due to its "private" access specifier.
     * @param ballInput The input parameter "ball" to be set.
     */
    public void ballSetter(Ball ballInput) {
    	this.ball = ballInput;
    }
    
    /**
     * Setter method for the variable "brickCount" due to its "private" access specifier.
     * @param brickCountInput The input parameter "brickCount" to be set.
     */
    public void brickCountSetter(int brickCountInput) {
    	this.brickCount = brickCountInput;
    }
    
    /**
     * Setter method for the variable "ballCount" due to its "private" access specifier.
     * @param ballCountInput The input parameter "ballCount" to be set.
     */
    public void ballCountSetter(int ballCountInput) {
    	this.ballCount = ballCountInput;
    }
    
    /**
     * Setter method for the variable "bricks" due to its "private" access specifier.
     * @param bricksInput The input parameter "bricks" to be set.
     */
    public void bricksSetter(Brick[] bricksInput) {
    	this.bricks = bricksInput;
    }
    
    /**
     * Setter method for the variable "ballLost" due to its "private" access specifier.
     * @param ballLostInput The input parameter "ballLost" to be set.
     */
    public void ballLostSetter(boolean ballLostInput) {
    	this.ballLost = ballLostInput;
    }
    
    /**
     * Setter method for the variable "startPoint" due to its "private" access specifier.
     * @param startPointInput The input parameter "startPoint" to be set.
     */
    public void startPointSetter(Point startPointInput) {
    	this.startPoint = startPointInput;
    }
    
    /**
     * Setter method for the variable "rnd" due to its "private" access specifier.
     * @param rndInput The input parameter "rnd" to be set.
     */
    public void rndSetter(Random rndInput) {
    	this.rnd = rndInput;
    }
    
    /**
     * Setter method for the variable "area" due to its "private" access specifier.
     * @param areaInput The input parameter "area" to be set.
     */
    public void areaSetter(Rectangle areaInput) {
    	this.area = areaInput;
    }
    
    /**
     * Setter method for the variable "level" due to its "private" access specifier.
     * @param levelInput The input parameter "level" to be set.
     */
    public void levelSetter(int levelInput) {
    	this.level = levelInput;
    }

}
