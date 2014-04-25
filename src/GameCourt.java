/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {
	
	// the state of the game logic
	private Snake snake; // creates a new snake
	private Heart heart; //creates a good object - heart!!
	private Crown crown; //creates a crown (good object)
	private Bad bad; //creates 'bad' object snake cannot touch

	public boolean playing = false; // whether the game is running
	private JLabel status; 
	
	//string names of images to be loaded 
	public static final String game_overimg = "gameover.png";
	private static BufferedImage img;
	
	public static final String instructions_img = "instructions.png";
	private static BufferedImage instructionsimg;
	
	public boolean instructionspressed = false;
	
	// Game constants
	public static final int COURT_WIDTH = 800;
	public static final int COURT_HEIGHT = 600;
	public static int SNAKE_VELOCITY_X = 5;
	public static int SNAKE_VELOCITY_Y = 5;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;


	public GameCourt(JLabel status) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		setBackground(Color.YELLOW);
		
		//initialize game over image & instructions image
		try {
			if (img == null) {
				img = ImageIO.read(new File(game_overimg));
				instructionsimg = ImageIO.read(new File(instructions_img));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
		
		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				//key is pressed, change velocities accordingly
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					snake.v_y = 0;
					snake.v_x = -SNAKE_VELOCITY_X;
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					snake.v_y = 0;
					snake.v_x = SNAKE_VELOCITY_X;
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					snake.v_x = 0;
					snake.v_y = SNAKE_VELOCITY_Y;
				}
				else if (e.getKeyCode() == KeyEvent.VK_UP) {
					snake.v_x = 0;
					snake.v_y = -SNAKE_VELOCITY_Y;	
				}
			}
			
			public void keyReleased(KeyEvent e) {
				//nothing here
			}

		});

		//just updating private status variable 
		this.status = status;
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {

		snake = new Snake(COURT_WIDTH, COURT_HEIGHT);
		heart = new Heart(COURT_WIDTH, COURT_HEIGHT);
		bad = new Bad(COURT_WIDTH, COURT_HEIGHT);
		crown = new Crown(COURT_WIDTH, COURT_HEIGHT);
		
		//reset score and level 
		instructionspressed = false;
		snake.set_score(0);
		snake.set_level(1);
		
		//reset velocity 
		SNAKE_VELOCITY_X = 4;
		SNAKE_VELOCITY_Y = 4;
		
		//set playing status to true 
		playing = true;
		status.setText("Score: " + Integer.toString(snake.current_score()));
		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	
	//display the instructions screen
	public void instructions() {
	
		instructionspressed = true;
		requestFocusInWindow();
		repaint();
	}
	

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing) {
			//advance the snake in its current direction
			snake.move();
			
			//check to see if snake has hit heart 
			if ((heart.intersects(snake))) {
				
				//randomly spawn a new bad object (with probability .5)
				if (Math.random() < 0.5) {
					bad.add_bad();
				}
				
				//if level is high enough, spawn a crown randomly
				if ((snake.get_level() > 2) && (Math.random() < 0.3)) {
						crown.add_crown();
				}
				
				//check current score of snake
				//'level up' every 100 pts 
				if ((snake.current_score()!=0) && (snake.current_score() % 100 == 0))  {
					snake.inc_level();
					
					//increase velocity
					SNAKE_VELOCITY_X += 4;
					SNAKE_VELOCITY_Y += 4;
				}
				
				//update length of snake 
				snake.growSnake(5);
				
				//current heart object 'disappears,' generating
				//new heart in randomly generated, different location
				heart.add_heart();
				
				//update score accordingly
				snake.inc_score_heart();
				
				//update status
				status.setText("Score:" + Integer.toString(snake.current_score()));
			}
			
			//check if snake has intersected a crown
			if (crown.intersects(snake)) {
				
				//randomly spawn a bad object, with greater frequency than 
				//if we were to hit a heart
				if (Math.random() < 0.7) {
					bad.add_bad();
				}
				
				//update length of the snake
				snake.growSnake(5);
				
				//if level is high enough, spawn another crown (although
				//probability of crown appearing should still be low, 
				//as it is a relatively 'rarer' object
				if ((snake.get_level() > 2) && (Math.random() < 0.3)) {
					crown.add_crown();
				}
				
				//check current score of snake
				//'level up' every 100 pts 
				if ((snake.current_score()!=0) && (snake.current_score() % 100 == 0))  {
					snake.inc_level();
					
					//increase velocity
					SNAKE_VELOCITY_X += 4;
					SNAKE_VELOCITY_Y += 4;
				}
				
				//increment score by 30 points
				snake.inc_score_crown();
				
				//update status 
				status.setText("Score:" + Integer.toString(snake.current_score()));
						
			}
			//check if snake has hit the wall
			//if it has, game over
			else if ((snake.hasHitWall() || (bad.intersects(snake)))) {
				
				playing = false;
				
			}
			
			//TO DO: check if snake has hit itself

			// update the display
			repaint();
		}
   }

	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		//check if instructions button has been pressed 
		if (instructionspressed) {
			
		//draw instructions screen
		g.drawImage(instructionsimg, 0, 120, 800, 400, null);
			
		}
		//check if game is over
		else if (!playing) {
		
			//if yes, draw game over sign 
			g.drawImage(img, 150, 150, 500, 300, null);
			
		} else {
			snake.draw(g);
			heart.draw(g);
			bad.draw(g);
			crown.draw(g);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}

/*
 * Stuff to implement: 
 * make panel look nicer!!
 * high score button ?
 * IMPT: snake collides with itself
 * need to make sure two objs not generated at same pos
 * eg. heart is not generated where snake body is 
 * implement diff kinds of 'good' hearts -> diff scores
 * 
 * 
 */
