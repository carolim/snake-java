/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
public class Game implements Runnable {
	public void run() {
		
		// Top-level frame in which game components live
		// Be sure to change "TOP LEVEL FRAME" to the name of your game
		final JFrame frame = new JFrame("Snake");
		frame.setLocation(800, 600);


		// Status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		//status panel displays current score 
		final JLabel status = new JLabel("Score: ");
		status.setFont((new Font("Courier New",1,15)));
		status_panel.add(status);
		
		// Main playing area
		final GameCourt court = new GameCourt(status);
		frame.add(court, BorderLayout.CENTER);
		
		

		//RESET button 
		// Note here that when we add an action listener to the reset
		// button, we define it as an anonymous inner class that is
		// an instance of ActionListener with its actionPerformed()
		// method overridden. When the button is pressed,
		// actionPerformed() will be called.
		final JButton reset = new JButton("Reset");
		reset.setFont(new Font("Courier New",1,15));
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				court.reset();
			}
		});
		
		//INSTRUCTIONS button
		//if this button is pressed, screen will show a set of instructions
		final JButton instructions = new JButton("Instructions");
		instructions.setFont(new Font("Courier New",1,15));
		instructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//activate instructions screen 
				court.instructions();
				
			}
		});
		
		status_panel.add(reset);
		status_panel.add(instructions);
		
		// Put the frame on the screen
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Start game
		court.reset();
	}

	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
