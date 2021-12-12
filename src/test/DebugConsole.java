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
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * A class for debugging the codes.
 * @author KhooChenNan
 *
 */
public class DebugConsole extends JDialog implements WindowListener{

    private static final String TITLE = "Debug Console";

    /**
     * A JFrame object to be set in the constructor.
     */
    private JFrame owner;
    /**
     * A DebugPanel object to be initialized in the constructor.
     */
    private DebugPanel debugPanel;
    /**
     * GameBoard object to be set in the constructor.
     */
    private GameBoard gameBoard;
    /**
     * Wall object to be set in the constructor.
     */
    private Wall wall;
    /**
     * Wall controller object to be set in the constructor.
     */
    private WallController wallController;

    /**
     * Constructor for the "DebugConsole.Java" class.  Takes in 4 input parameters: owner of JFrame data type, wallInput, gameBoard,
     * and lastly wallControllerInput.
     * @param owner A JFrame object.
     * @param wallInput A wall object.
     * @param gameBoard A gameBoard object.
     * @param wallControllerInput A wall controller object.
     */
    public DebugConsole(JFrame owner,Wall wallInput,GameBoard gameBoard, WallController wallControllerInput){

        this.wall = wallInput;
        this.wallController = wallControllerInput;
        this.owner = owner;
        this.gameBoard = gameBoard;
        initialize();

        debugPanel = new DebugPanel(wallInput, wallControllerInput);
        this.add(debugPanel,BorderLayout.CENTER);

        this.pack();
    }

    /**
     * Initializes the DebugConsole for usage.
     */
    private void initialize(){
        this.setModal(true);
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.addWindowListener(this);
        this.setFocusable(true);
    }

    /**
     * Sets the location of DebugConsole.
     */
    private void setLocation(){
        int x = ((owner.getWidth() - this.getWidth()) / 2) + owner.getX();
        int y = ((owner.getHeight() - this.getHeight()) / 2) + owner.getY();
        this.setLocation(x,y);
    }
    
    @Override
    /**
     * Not used but needs to be implemented due to the concept of Abstraction.
     */
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    /**
     * Not used but needs to be implemented due to the concept of Abstraction.
     */
    public void windowClosing(WindowEvent windowEvent) {
        gameBoard.repaint();
    }

    @Override
    /**
     * Not used but needs to be implemented due to the concept of Abstraction.
     */
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    /**
     * Not used but needs to be implemented due to the concept of Abstraction.
     */
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    /**
     * Not used but needs to be implemented due to the concept of Abstraction.
     */
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    /**
     * Activates the window for the Debug Console.
     */
    public void windowActivated(WindowEvent windowEvent) {
        setLocation();
        Ball b = wall.ball;
        debugPanel.setValues(b.getSpeedX(),b.getSpeedY());
    }

    @Override
    /**
     * Not used but needs to be implemented due to the concept of Abstraction.
     */
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
