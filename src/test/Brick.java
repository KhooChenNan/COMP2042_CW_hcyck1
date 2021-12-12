package test;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * An abstract class which will then be inherited by the specific brick types such as Clay and Cement.  It consists of some general
 * attributes which a brick must have while some attributes like the cracking of the brick isn't a necessity.  Since there aren't much
 * universal operations in terms of shared common functions among Steel, Clay, Cement, and etc, there will be no need to separate it into
 * MVC (Model-View-Controller) design pattern so as to keep the code clean.
 * @author KhooChenNan
 *
 */
abstract public class Brick  {

	/**
	 * The minimum crack of a specific brick.
	 */
    public static final int MIN_CRACK = 1;
    /**
     * The crack depth of a specific brick.
     */
    public static final int DEF_CRACK_DEPTH = 1;
    /**
     * The steps of a specific brick.
     */
    public static final int DEF_STEPS = 35;

    /**
     * To be redirect the ball towards upward if the initial direction of ball is towards downward.
     */
    public static final int UP_IMPACT = 100;
    /**
     * To be redirect the ball towards downward if the initial direction of ball is towards upward.
     */
    public static final int DOWN_IMPACT = 200;
    /**
     * To be redirect the ball towards left side if the initial direction of ball is towards right side.
     */
    public static final int LEFT_IMPACT = 300;
    /**
     * To be redirect the ball towards right side if the initial direction of ball is towards left side.
     */
    public static final int RIGHT_IMPACT = 400;

    /**
     * Crack class which is only implemented by bricks that crack such as Cement and Graphite.
     * @author KhooChenNan
     *
     */
    public class Crack{

    	/**
    	 * The cracking sections of that specific brick.
    	 */
        private static final int CRACK_SECTIONS = 3;
        /**
         * The jumping probability of that specific brick.
         */
        private static final double JUMP_PROBABILITY = 0.7;

        /**
         * The "left" direction of impact on bricks.
         */
        public static final int LEFT = 10;
        /**
         * The "right" direction of impact on bricks.
         */
        public static final int RIGHT = 20;
        /**
         * The "upwards" direction of impact on bricks.
         */
        public static final int UP = 30;
        /**
         * The "downwards" direction of impact on bricks.
         */
        public static final int DOWN = 40;
        /**
         * The "vertical" direction of impact on bricks.
         */
        public static final int VERTICAL = 100;
        /**
         * The "horizontal" direction of impact on bricks.
         */
        public static final int HORIZONTAL = 200;

        /**
         * The visuals of the crack.
         */
        private GeneralPath crack;

        /**
         * The depth of the cracks of the brick.
         */
        private int crackDepth;
        /**
         * The steps of the brick.
         */
        private int steps;

        /**
         * Constructor for the crack class.  It takes in 2 parameters crackDepth and steps and then later on set both of the variables
         * into that specific brick's crackDepth and steps.
         * @param crackDepth The crack depth of that specific brick.
         * @param steps The steps of that specific brick.
         */
        public Crack(int crackDepth, int steps){
            crack = new GeneralPath();
            this.crackDepth = crackDepth;
            this.steps = steps;
        }

        /**
         * Draws the cracking line on the bricks if there are any impacts.
         * @return The line "crack" of GeneralPath data type is returned.  
         */
        public GeneralPath draw(){
            return crack;
        }

        /**
         * Resets the cracking of the brick.
         */
        public void reset(){
            crack.reset();
        }

        /**
         * Makes the crack on the specific brick types.  Only Cement and Graphite will be implementing this.  For example, if the 
         * direction is "left", it will first sets the location of the starting point then the end point, followed by setting the value
         * of a the coordinate "tmp" based on the start and end point.  After that, it will make the crack based on the impact and "tmp".
         * @param point The coordinates to be set on.
         * @param direction The direction to be set on.  (E.g. Left, right, up, down)
         */
        protected void makeCrack(Point2D point, int direction){
            Rectangle bounds = Brick.this.brickFace.getBounds();

            Point impact = new Point((int)point.getX(),(int)point.getY());
            Point start = new Point();
            Point end = new Point();

            switch(direction){
                case LEFT:
                    start.setLocation(bounds.x + bounds.width, bounds.y);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    Point tmp = makeRandomPoint(start,end,VERTICAL);
                    makeCrack(impact,tmp);

                    break;
                case RIGHT:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,VERTICAL);
                    makeCrack(impact,tmp);

                    break;
                case UP:
                    start.setLocation(bounds.x, bounds.y + bounds.height);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeCrack(impact,tmp);
                    
                    break;
                case DOWN:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x + bounds.width, bounds.y);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeCrack(impact,tmp);

                    break;
            }
        }

        /**
         * Unlike makeCrack() function, this function will not take in any direction and will make the crack based on the 2 input
         * parameters and they are the starting and ending coordinates.
         * @param start The starting coordinate
         * @param end The ending coordinate
         */
        protected void makeCrack(Point start, Point end){
            GeneralPath path = new GeneralPath();

            path.moveTo(start.x,start.y);

            double w = (end.x - start.x) / (double)steps;
            double h = (end.y - start.y) / (double)steps;

            int bound = crackDepth;
            int jump  = bound * 5;

            double x,y;

            for(int i = 1; i < steps;i++){
                x = (i * w) + start.x;
                y = (i * h) + start.y + randomInBounds(bound);

                if(inMiddle(i,CRACK_SECTIONS,steps)) {
                	y += jumps(jump,JUMP_PROBABILITY);
                }
                path.lineTo(x,y);
            }
            path.lineTo(end.x,end.y);
            crack.append(path,true);
        }

        /**
         * A function that takes in the inbounds and then returns a calculated value which consists of a randomly generated value up until a 
         * certain integer.
         * @param bound The inbounds to be passed on for calculation of variable "n".
         * @return An integer of randomly generated value with the range from 0 to "n" minus the inbounds.
         */
        private int randomInBounds(int bound){
            int n = (bound * 2) + 1;
            return rnd.nextInt(n) - bound;
        }

        /**
         * A boolean function that takes in 3 parameters: "i", "steps", and "divisions" and does calculation for variable "low" and "up".
         * @param i The number of counts.
         * @param steps The number of steps.
         * @param divisions The number of divisions.
         * @return Returns true if "i" is greater than "low" and "i" lesser than up.  Else, returns false.
         */
        private boolean inMiddle(int i,int steps,int divisions){
            int low = (steps / divisions);
            int up = low * (divisions - 1);

            return  (i > low) && (i < up);
        }

        /**
         * A function that returns 0 if the randomly generated decimal is smaller than the probability of jumping.  Else, it calls the function
         * randomInBounds() by passing in the parameter bound, which is the inbounds, and then returns the calculated value.
         * @param bound The inbounds.
         * @param probability The probability of jumping.
         * @return The value "0" is returned if the randomly generated decimal is smaller than the probability of jumping.  Else, it returns
         * an integer which is calculated through the function randomInBounds() that takes in the variable "bound".
         */
        private int jumps(int bound,double probability){
            if(rnd.nextDouble() > probability)
                return randomInBounds(bound);
            return  0;
        }

        /**
         * Makes a random point and takes in 3 parameters: initial coordinates, resulting coordinates, and its direction.  If the direction
         * is "horizontal", it sets the variable "out" with a specific coordinates after calculation and it will then be returned.  Likewise for 
         * the case of vertical direction.
         * @param from The initial coordinates (where it is from)
         * @param to The resulting coordinates (where it is going to)
         * @param direction The direction of where it is going towards
         * @return Coordinates to be returned due to the resulting direction.
         */
        private Point makeRandomPoint(Point from,Point to, int direction){

            Point out = new Point();
            int pos;

            switch(direction){
                case HORIZONTAL:
                    pos = rnd.nextInt(to.x - from.x) + from.x;
                    out.setLocation(pos,to.y);
                    break;
                case VERTICAL:
                    pos = rnd.nextInt(to.y - from.y) + from.y;
                    out.setLocation(to.x,pos);
                    break;
            }
            return out;
        }
    }

    /**
     * A random value which is initialized to be used for calculations.  If not specified, its default generated value is from 0 to 1 (inclusive).
     */
    private static Random rnd;
    
    /**
     * The name of the brick.
     */
    private String name;
    /**
     * The visuals of the brick in Shape data type.
     */
    Shape brickFace;

    /**
     * The border color of the brick
     */
    private Color border;
    /**
     * The inner color of the brick
     */
    private Color inner;

    /**
     * The highest possible strength of the brick.
     */
    private int fullStrength;
    /**
     * The current strength of the brick.
     */
    private int strength;

    /**
     * A boolean data type which tells the program whether the brick is broken or not.
     */
    private boolean broken;

    /**
     * The constructor of for the class "Brick.Java".  Initializes the random value, the initial value of "broken" variable to false
     * to construct it.  It also sets the name, visual, border color, inner color, and full strength of the brick.
     * @param name The name of the brick.
     * @param pos The coordinates of the brick.
     * @param size The size of the brick.
     * @param border The border color of the brick.
     * @param inner The inner color of the brick.
     * @param strength The strength of the brick.
     */
    public Brick(String name, Point pos,Dimension size,Color border,Color inner,int strength){
        rnd = new Random();
        broken = false;
        this.name = name;
        brickFace = makeBrickFace(pos,size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;
    }

    /**
     * An abstract method as mentioned prior to this which will then be implemented later on by other brick type classes.
     * @param pos The coordinates of the brick.
     * @param size The size of the brick.
     * @return The visual of the brick is returned.
     */
    protected abstract Shape makeBrickFace(Point pos,Dimension size);

    /**
     * Sets the impact of the brick by taking in 2 input parameters, its point and direction.
     * @param point The coordinates of the brick.
     * @param dir The direction of the impact.
     * @return If the brick is broken, it will return false.  Else, it calls the impact() function and then returns the variable "broken".
     */
    public  boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact();
        return  broken;
    }

    /**
     * A method that gets the brick.
     * @return A brick in Shape data type is returned.
     */
    public abstract Shape getBrick();

    /**
     * Getter method for the border color of that specific brick.
     * @return The border color of that specific brick is returned.
     */
    public Color getBorderColor(){
        return  border;
    }

    /**
     * Getter method for the inner color of that specific brick.
     * @return The inner color of that specific brick is returned.
     */
    public Color getInnerColor(){
        return inner;
    }

    /**
     * A function which determines the direction of reflection for the ball.  E.g. If the ball is from right, the variable "out" will then be set
     * to a specific value which tells the program to reflect it to that direction.
     * @param b The ball which comes into contact.
     * @return Integer data type "out" which determines the impact's direction.
     */
    public final int findImpact(Ball b){
        if(broken)
            return 0;
        int out  = 0;
        if(brickFace.contains(b.right))
            out = LEFT_IMPACT;
        else if(brickFace.contains(b.left))
            out = RIGHT_IMPACT;
        else if(brickFace.contains(b.up))
            out = DOWN_IMPACT;
        else if(brickFace.contains(b.down))
            out = UP_IMPACT;
        return out;
    }

    /**
     * Getter method for the variable "broken" due to its "private" access specifier.
     * @return A boolean data type which tells the program whether it is broken or not is returned.  "true" if broken, "false" if not.
     */
    public final boolean isBroken(){
        return broken;
    }

    /**
     * Repairs the brick to their respective full strength.  E.g. If a brick has 1 life count left, calling this function will immediately
     * increase its life count back to their highest possible life count.
     */
    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    /**
     * When the ball comes into contact with the brick, this function causes the strength of the brick to be reduced by 1 unless 
     * they are specially made like Steel.
     */
    public void impact(){
        strength--;
        broken = (strength == 0);
    }
}





