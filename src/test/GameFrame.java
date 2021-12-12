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
import java.awt.datatransfer.StringSelection;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

/**
 * A class that is responsible for the enabling of pages such as Home Menu, GameBoard, and Tutorial Menu.
 * @author KhooChenNan
 *
 */
public class GameFrame extends JFrame implements WindowFocusListener {

	/**
	 * The title string "Brick Destroy".
	 */
    private static final String DEF_TITLE = "Brick Destroy";

    /**
     * A GameBoard object.
     */
    private GameBoard gameBoard;
    /**
     * A HomeMenu object.
     */
    private HomeMenu homeMenu;
    /**
     * A TutorialMenu object.
     */
    private TutorialMenu tutorialMenu;

    /**
     * A boolean value that acts as a flag to check whether the user is gaming or not.
     */
    private boolean gaming;

    public GameFrame(){
        super();

        gaming = false;

        this.setLayout(new BorderLayout());

        gameBoard = new GameBoard(this);

        homeMenu = new HomeMenu(this,new Dimension(450,300));
        tutorialMenu = new TutorialMenu(this,new Dimension(450,300));

        this.add(homeMenu,BorderLayout.CENTER);

        this.setUndecorated(true);
    }

    /**
     * A function that initializes the GameFrame.
     */
    public void initialize(){
        this.setTitle(DEF_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.autoLocate();
        this.setVisible(true);
    }

    /**
     * A function that enables the GameBoard.
     */
    public void enableGameBoard(){
        this.dispose();
        this.remove(homeMenu);
        this.remove(tutorialMenu);
        this.add(gameBoard,BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);
    }
    
    /**
     * A function that enables the Tutorial Menu.
     */
    public void enableTutorialMenu() {
    	this.dispose();
    	this.remove(homeMenu);
    	this.add(tutorialMenu, BorderLayout.CENTER);
    	this.setUndecorated(false);
        initialize();
        this.addWindowFocusListener(this);
    }

    /**
     * A function that is responsible for auto locating and setting location.
     */
    private void autoLocate(){
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);
    }

    @Override
    /**
     * A function that is responsible for the window gaining focus.  If focus is gained, pressing any controls will work.
     */
    public void windowGainedFocus(WindowEvent windowEvent) {
        /*
            the first time the frame loses focus is because
            it has been disposed to install the GameBoard,
            so went it regains the focus it's ready to play.
            of course calling a method such as 'onLostFocus'
            is useful only if the GameBoard as been displayed
            at least once
         */
        gaming = true;
    }

    @Override
    /**
     * A function that is responsible for the window losing focus.  If focus is lost, pressing any controls won't work.
     */
    public void windowLostFocus(WindowEvent windowEvent) {
        if(gaming)
            gameBoard.onLostFocus();
    }
}
