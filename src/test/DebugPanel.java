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

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * A class that holds the elements to display the debugging.  Unlike DebugConsole, DebugConsole holds the code for debugging while
 * DebugPanel holds the code for visual displays.
 * @author KhooChenNan
 *
 */
public class DebugPanel extends JPanel {

	/**
	 * The background color.
	 */
    private static final Color DEF_BKG = Color.WHITE;

    /**
     * Button for skipping level.
     */
    private JButton skipLevel;
    /**
     * Button for resetting the balls.
     */
    private JButton resetBalls;

    /**
     * The horizontal speed of the ball.
     */
    private JSlider ballXSpeed;
    /**
     * The vertical speed of the ball.
     */
    private JSlider ballYSpeed;

    /**
     * A wall object.
     */
    private Wall wall;
    /**
     * A wall controller object.
     */
    private WallController wallController;

    /**
     * Constructor for the DebugPanel.  Takes in 2 input parameters: WallInput and wallControllerInput.
     * @param wallInput
     * @param wallControllerInput
     */
    public DebugPanel(Wall wallInput, WallController wallControllerInput){

        this.wall = wallInput;
        this.wallController = wallControllerInput;

        initialize();

        skipLevel = makeButton("Skip Level",e -> wall.nextLevel());
        resetBalls = makeButton("Reset Balls",e -> wallController.resetBallCount());

        ballXSpeed = makeSlider(-4,4,e -> wallController.setBallXSpeed(ballXSpeed.getValue()));
        ballYSpeed = makeSlider(-4,4,e -> wallController.setBallYSpeed(ballYSpeed.getValue()));

        this.add(skipLevel);
        this.add(resetBalls);

        this.add(ballXSpeed);
        this.add(ballYSpeed);

    }

    /**
     * A function for initialization of debugging.
     */
    private void initialize(){
        this.setBackground(DEF_BKG);
        this.setLayout(new GridLayout(2,2));
    }

    /**
     * A function for constructing the button.
     * @param title The title of the button.  (To be passed as a parameter when initializing the button)
     * @param e An event handler that is responsible when the user clicks on a component.
     * @return A JButton type object "out" is returned. (A button)
     */
    private JButton makeButton(String title, ActionListener e){
        JButton out = new JButton(title);
        out.addActionListener(e);
        return  out;
    }

    /**
     * A function that constructs the track bar.
     * @param min The minimum value to be passed as a parameter.
     * @param max The maximum value to be passed as a parameter.
     * @param e A ChangeListener object that notifies when the object has changed.
     * @return A JSlider type object "out" is returned.  (A track bar)
     */
    private JSlider makeSlider(int min, int max, ChangeListener e){
        JSlider out = new JSlider(min,max);
        out.setMajorTickSpacing(1);
        out.setSnapToTicks(true);
        out.setPaintTicks(true);
        out.addChangeListener(e);
        return out;
    }

    /**
     * A function that sets the horizontal and vertical speed of the ball.
     * @param x The horizontal speed of the ball.
     * @param y The vertical speed of the ball.
     */
    public void setValues(int x,int y){
        ballXSpeed.setValue(x);
        ballYSpeed.setValue(y);
    }
}
