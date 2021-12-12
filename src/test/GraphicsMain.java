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

/**
 * The "main" class for the program that is the starting point of the entire code.  Initializes "GameFrame.Java" and in turns causes a 
 * Domino effect which calls other functions of the code to make the program run.
 * @author KhooChenNan
 *
 */
public class GraphicsMain {

	/**
	 * The starting point of initialization of the entire program.  It creates a new game frame then initializes it.
	 * @param args Stores the Java line commands.
	 */
    public static void main(String[] args){
        EventQueue.invokeLater(() -> new GameFrame().initialize());
    }
}
