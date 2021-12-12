package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * The class for the Vibranium brick type.  It inherits from the "Brick.Java" and implements the parent class' abstract method.  It also 
 * consists of multiple attributes of the Vibranium brick such as the name, inner color, border color, and strength, and probability to
 * reduce its life count.
 * @author KhooChenNan
 *
 */
public class VibraniumBrick extends Brick {

	/* Never cracks and has 3 lives. Probability of reducing life is 30%. */
	
	/**
	 * The name of the brick.
	 */
	private static final String NAME = "Vibranium Brick";
	/**
	 * The inner color of Vibranium brick.
	 */
	private static final Color DEF_INNER = new Color(102, 78, 174);
	/**
	 * The border color of Vibranium brick.
	 */
    private static final Color DEF_BORDER = Color.BLACK;
    /**
     * The strength of Vibranium brick.
     */
    private static final int VIBRANIUM_STRENGTH = 3;
    /**
     * The probability to reduce Vibranium brick's life count.
     */
    private static final double VIBRANIUM_PROBABILITY = 0.3;

    /**
     * A randomly generated value to decide the reduction of Vibranium's life count.
     */
    private Random rnd;
    /**
     * The visuals of the Vibranium brick.
     */
    private Shape brickFace;	
	
    /**
     * Constructor for the "VibraniumBrick.Java" class.  Takes in 2 input parameters like any other brick types: the coordinates and size.
     * It then initializes the visuals.  Since its life count doesn't reduce on every impact, it uses a randomly generated value
     * to decide so it also initializes the randomly generated value as well.  Then, it sets the visual to the parent class' visual.
     * @param point The coordinates for the brick.
     * @param size The size for the brick.
     */
	public VibraniumBrick(Point point, Dimension size) {
		super(NAME, point, size, DEF_BORDER, DEF_INNER, VIBRANIUM_STRENGTH);
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
     * Getter method for variable "brickFace" due to its "private" access specifier.
     * @return The variable "brickFace" is returned.
     */
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * A boolean function which sets the impact on the bricks.  If the brick is broken, it will return false.  Else, it will reduces the strength
     * count by 1 with a probability of 0.3.
     * @param point The coordinates of the brick on impact.
     * @param dir The direction of impact.
     * @return Returns false if brick is broken.  Else, it will return the value of isBroken() from the parent class (Brick.Java).
     */
    public  boolean setImpact(Point2D point , int dir){
        if(super.isBroken()) {
        	return false;
        }
        impact();
        return  super.isBroken();
    }

    /**
     * A function that reduces the life count of the brick based on the probability.  A randomly generated value 0-1 will be compared with the
     * vibranium's probability.  If it is lesser than 0.3, it will only reduce the life count.  Else, it will not call the impact() function
     * from parent class to reduce its life count.
     */
    public void impact(){
        if(rnd.nextDouble() < VIBRANIUM_PROBABILITY){
            super.impact(); 
        }
    }
}
