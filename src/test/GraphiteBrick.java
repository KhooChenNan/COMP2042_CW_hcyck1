package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

import test.Brick.Crack;

/**
 * The class for the Graphite brick type.  It inherits from the "Brick.Java" and implements the parent class' abstract method.  It also 
 * consists of multiple attributes of the Graphite brick such as the name, inner color, border color, and strength.  However, this is
 * different from bricks such as Cement as it has an additional attribute (the probability to reduce its lifecount).
 * @author KhooChenNan
 *
 */
public class GraphiteBrick extends Brick {

	/* Has 2 lives and a probability of 0.5 chance to reduce it by 1 */
	
	/**
	 * The name of the brick.
	 */
    private static final String NAME = "Graphite Brick";
    /**
     * The inner color of the Graphite brick.
     */
    private static final Color DEF_INNER = new Color(153, 153, 0);
    /**
     * The border color of the Graphite brick.
     */
    private static final Color DEF_BORDER = Color.BLACK;
    /**
     * The strength of the Graphite brick.
     */
    private static final int GRAPHITE_STRENGTH = 2;
    /**
     * The probability to reduce Graphite brick's life count.
     */
    private static final double GRAPHITE_PROBABILITY = 0.5;

    /**
     * A randomly generated value to decide the reduction of graphite's life count.
     */
    private Random rnd;
    /**
     * The cracking of Graphite brick.
     */
    private Crack crack;
    /**
     * The visuals of the Graphite brick.
     */
    private Shape brickFace;
	
    /**
     * Constructor for the "CementBrick.Java" class.  Takes in 2 input parameters like any other brick types: the coordinates and size.
     * It then initializes the cracks and visuals.  Since its life count doesn't reduce on every impact, it uses a randomly generated value
     * to decide so it also initializes the randomly generated value as well.
     * @param point The coordinates for the brick.
     * @param size The size for the brick.
     */
	public GraphiteBrick(Point point, Dimension size) {
		super(NAME, point, size, DEF_BORDER, DEF_INNER, GRAPHITE_STRENGTH);
		crack = new Crack(DEF_CRACK_DEPTH, DEF_STEPS);
		rnd = new Random();
		brickFace = super.brickFace;
	}

    @Override
    /**
     * A function that constructs the visual of the brick.
     * @param pos The coordinates of the brick.
     * @param size The size of the brick.
     * @return A new area enclosed within the coordinates for brick is returned.
     */
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    @Override
    /**
     * A boolean function which sets the impact on the bricks.  If the brick is broken, it will return false.  Else, it will reduces the strength
     * count by 1 with a probability of 0.5 and draws the cracking on the brick no matter if the life count is reduced or not.
     * @param point The coordinates of the brick on impact.
     * @param dir The direction of impact.
     * @return Returns false if brick is broken.  Else, it will return true/the value of isBroken() from the parent class (Brick.Java).
     */
    public boolean setImpact(Point2D point, int dir) {
    	if (super.isBroken()) {
    		return false;
    	}
    	impact(); // .5 chance to reduce the life by 1
    	if(!super.isBroken()) {
    		crack.makeCrack(point, dir);
    		updateBrick();
    		return super.isBroken();
    	}
    	return true;
    }

    @Override
    /**
     * Getter method for variable "brickFace" due to its "private" access specifier.
     * @return The variable "brickFace" is returned.
     */
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * A function that updates the Brick like "CementBrick.Java".  If the brick isn't broken, it will draw the cracking.
     */
    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;
        }
    }
    
    /**
     * A function that repairs the Graphite brick.  It calls the repair function from its parent class (Brick.Java), then resets and sets
     * the visual for the brick equal to the parent class brick.
     */
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }
    
    /**
     * A function that reduces the life count of the brick based on the probability.  A randomly generated value 0-1 will be compared with the
     * graphite's probability.  If it is lesser than 0.5, it will only reduce the life count.  Else, it will not call the impact() function
     * from parent class to reduce its life count.
     */
    public void impact(){
        if(rnd.nextDouble() < GRAPHITE_PROBABILITY){
            super.impact(); // reduces the life
        }
    }
}
