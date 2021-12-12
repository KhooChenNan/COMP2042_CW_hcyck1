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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * The rubber ball class which will be used on the game as the ball.
 * @author KhooChenNan
 *
 */
public class RubberBall extends Ball {

	/**
	 * The radius of the rubber ball.
	 */
    private static final int DEF_RADIUS = 13;
    /**
     * The inner color of the rubber ball.
     */
    private static final Color DEF_INNER_COLOR = new Color(255, 219, 88);
    /**
     * The border color of the rubber ball.
     */
    private static final Color DEF_BORDER_COLOR = DEF_INNER_COLOR.darker().darker();

    /**
     * Constructor for the "Rubberball.Java" class.  Calls the parent class' constructor to pass in the rubber ball's attributes.
     * @param center The coordinates of the ball to be constructed.
     */
    public RubberBall(Point2D center){
        super(center,DEF_RADIUS,DEF_RADIUS,DEF_INNER_COLOR,DEF_BORDER_COLOR);
    }

    @Override
    /**
     * A function that makes the rubber ball.  It takes in 3 parameters: The center coordinates, the first radius for integer x while the second
     * radius for integer y.
     */
    protected Shape makeBall(Point2D center, int radiusA, int radiusB) {

        double x = center.getX() - (radiusA / 2);
        double y = center.getY() - (radiusB / 2);

        return new Ellipse2D.Double(x,y,radiusA,radiusB);
    }
}
