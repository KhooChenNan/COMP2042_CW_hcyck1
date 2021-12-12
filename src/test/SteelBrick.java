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
 * The class for the Steel brick type.  It inherits from the "Brick.Java" and implements the parent class' abstract method.  It also 
 * consists of multiple attributes of the Steel brick such as the name, inner color, border color, and strength.  Like Graphite brick,
 * it also consists of the probability to reduce its life count.
 * @author KhooChenNan
 *
 */
public class SteelBrick extends Brick {

	/* Doesn't crack and has a probability of 0.4 to break */
	
	/**
	 * The name of the brick.
	 */
    private static final String NAME = "Steel Brick";
    /**
     * The inner color of the Steel brick.
     */
    private static final Color DEF_INNER = new Color(203, 203, 201);
    /**
     * The border color of the Steel brick.
     */
    private static final Color DEF_BORDER = Color.BLACK;
    /**
     * The strength of the Steel brick.
     */
    private static final int STEEL_STRENGTH = 1;
    /**
     * The probability to reduce Steel brick's life count.
     */
    private static final double STEEL_PROBABILITY = 0.4;

    /**
     * A randomly generated value to decide the reduction of steel's life count.
     */
    private Random rnd; // Default value generated is from 0.0 to 1.0 (inclusive of both) if not specified the end range.
    /**
     * The visuals of the Graphite brick.
     */
    private Shape brickFace;

    /**
     * Constructor for the "SteelBrick.Java" class.  Takes in 2 input parameters like any other brick types: the coordinates and size.
     * It then initializes the randomly generated value and visuals since its life count doesn't reduce on every impact like Graphite.
     * It also calls the parent class' constructor to pass in its name, coordinates, size, border color, inner color, and strength.
     * @param point The coordinates for the brick.
     * @param size The size for the brick.
     */
    public SteelBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,STEEL_STRENGTH);
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
     * count by 1 with a probability of 0.4.
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
     * steel's probability.  If it is lesser than 0.4, it will only reduce the life count.  Else, it will not call the impact() function
     * from parent class to reduce its life count.  Concept wise, it is similar to Graphite.
     */
    public void impact(){ // 40% chance of breaking
        if(rnd.nextDouble() < STEEL_PROBABILITY){ // If random value is > 0.4, it won't break
            super.impact();
        }
    }

}
