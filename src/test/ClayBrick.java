package test;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * The class for the Clay brick type.  It inherits from the "Brick.Java" and implements the parent class' abstract method.  It also 
 * consists of multiple attributes of the Clay brick such as the name, inner color, border color, and strength.
 * @author KhooChenNan
 *
 */
public class ClayBrick extends Brick {

	/**
	 * The name of the brick.
	 */
    private static final String NAME = "Clay Brick";
    /**
     * The inner color of the Clay brick.
     */
    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    /**
     * The border color of the Clay brick.
     */
    private static final Color DEF_BORDER = Color.GRAY;
    /**
     * The strength of the Clay brick.
     */
    private static final int CLAY_STRENGTH = 1;

    /**
     * Constructor for the "ClayBrick.Java" class.  Takes in 2 input parameters like any other brick types: the coordinates and size.
     * Since it does not have any visuals for cracking, it will call the parent class' constructor and pass in its name, coordinates,
     * size, border color, inner color, and strength.
     * @param point The coordinates for the brick.
     * @param size The size for the brick.
     */
    public ClayBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
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
     * @return The variable "brickFace" from its parent class is returned.
     */
    public Shape getBrick() {
        return super.brickFace;
    }
}
