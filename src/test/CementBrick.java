package test;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * The class for the Cement brick type.  It inherits from the "Brick.Java" and implements the parent class' abstract method.  It also 
 * consists of multiple attributes of the Cement brick such as the name, inner color, border color, and strength.
 * @author KhooChenNan
 *
 */
public class CementBrick extends Brick {

	/**
	 * The name of the brick.
	 */
    private static final String NAME = "Cement Brick";
    /**
     * The inner color of the Cement brick.
     */
    private static final Color DEF_INNER = new Color(147, 147, 147);
    /**
     * The border color of the Cement brick.
     */
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    /**
     * The strength of the Cement brick.
     */
    private static final int CEMENT_STRENGTH = 2;

    /**
     * The cracking of the Cement brick.
     */
    private Crack crack;
    /**
     * The visuals of the Cement brick.
     */
    private Shape brickFace;

    /**
     * Constructor for the "CementBrick.Java" class.  Takes in 2 input parameters like any other brick types: the coordinates and size.
     * It then initializes the cracks and visuals.
     * @param point The coordinates for the brick.
     * @param size The size for the brick.
     */
    public CementBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
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
     * count by 1 and draw the cracking on the brick.
     * @param point The coordinates of the brick on impact.
     * @param dir The direction of impact.
     * @return Returns false if brick is broken.  Else, it will return true.
     */
    public boolean setImpact(Point2D point, int dir) {
        if(super.isBroken())
            return false;
        super.impact(); // reduces the strength count by 1
        if(!super.isBroken()){
            crack.makeCrack(point,dir); // won't need this if not showing crack
            updateBrick(); // draws the crack
            return false;
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
     * A function that updates the Brick.  If the brick isn't broken, it will draw the cracking.
     */
    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;
        }
    }

    /**
     * A function that repairs the Cement brick.  It calls the repair function from its parent class (Brick.Java), then resets and sets
     * the visual for the brick equal to the parent class brick.
     */
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }
}
