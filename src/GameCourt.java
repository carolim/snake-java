/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. 
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {
	
	private BufferedReader r; //reader to read in text
	private BufferedWriter out; //to overwrite the text 
	private int highscore; //stores the current high score
	private boolean health_init; //boolean to check if health obj has been initialized
	
	// the state of the game logic
	private Snake snake; // creates a new snake
	private Heart heart; //creates a good object (heart)
	private Crown crown; //creates a good object (crown)
	private Bad bad; //creates a bad object (skull) 
	private Health health; 
	
	public boolean playing = false; // whether the game is running
	private JLabel status; 
	
	//images to be loaded (game over & instructions)
	public static final String game_overimg = "gameover.png";
	private static BufferedImage img;
	
	public static final String instructions_img = "instructions.png";
	private static BufferedImage instructionsimg;
	
	public boolean instructionspressed = false;
	
	// game constants
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
		
		//attempt to load images 
		try {
			if (img == null) {
				img = ImageIO.read(new File(game_overimg));
				instructionsimg = ImageIO.read(new File(instructions_img));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
		
		String s;
		
		try {
			
			//read in first (& should be only) line from highscore text 
			r = new BufferedReader(new FileReader("highscore.txt"));
			s = r.readLine();
			s = s.trim(); //get rid of all whitespace
			highscore = Integer.parseInt(s); //store current high score 
			
		} catch (IOException e) {}
		
		
		
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
		
		//Health timer - once health object is added to the board,
		//will stay on the board for 5 seconds before disappearing 
		
		
		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				
				//key is pressed, change snake velocity accordingly
				
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


		});

		//just updating private status variable 
		this.status = status;
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {
		
		//initialize game objects
		snake = new Snake(COURT_WIDTH, COURT_HEIGHT);
		heart = new Heart(COURT_WIDTH, COURT_HEIGHT);
		bad = new Bad(COURT_WIDTH, COURT_HEIGHT);
		crown = new Crown(COURT_WIDTH, COURT_HEIGHT);
		health = null;
		health_init = false; //health obj. has NOT been initialized 

		//reset score and level 
		snake.set_score(0);
		snake.set_level(1);
		
		//reset velocity 
		SNAKE_VELOCITY_X = 4;
		SNAKE_VELOCITY_Y = 4;
		
		//reset instructions pressed to false
		instructionspressed = false;
		
		//set playing status to true 
		playing = true;
		
		//reset label text 
		status.setText("High Score:" + Integer.toString(highscore) + "     "
				+ "Level:" + Integer.toString(snake.get_level()) + 
				"     Score:" + Integer.toString(snake.current_score()) + "     ");
		
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
			
			
			//if health object has been initialized, respawn it with 
			//frequency .005
			if ((health!=null) && (Math.random()<0.02)) {
				health.respawn();
			}
			
			//if score is high enough, generate a new health object 
			//with freq. .01 
			if ((snake.current_score() > 300) && (Math.random() < 0.01)) {
				
				//see if health obj has been initialized
				if (health_init==false) {
					//initialize new obj 
					health = new Health(COURT_WIDTH, COURT_HEIGHT);
					health_init = true;
				} 	
			}
			
			//if health object intersects snake, should 'cut off'
			//the snake's tail by 30 nodes 
			if ((health!=null) && (health.intersects(snake))) {
				health.respawn();
				snake.shrinkSnake(50);
			}
			
			//check to see if snake has hit a heart 
			if ((heart.intersects(snake))) {
				
				//randomly spawn a new bad object, .5 of the time
				if (Math.random() < 0.5) {
					bad.add();
				}
				
				//if current score is high enough, randomly
				//spawn a crown, with probability .3
				
				if ((snake.current_score() > 200) && (Math.random() < 0.3)) {
						crown.add_crown();
				}
				
				//check current score of snake
				//'level up' every 100 pts 
				if ((snake.current_score()!=0) && (snake.current_score() % 100 == 0))  {
					snake.inc_level();
					
					//increase velocity
					SNAKE_VELOCITY_X += 1;
					SNAKE_VELOCITY_Y += 1;
						
				}
				
				//update length of snake 
				snake.growSnake(5);
				
				//current heart object 'disappears,' generating
				//new heart in randomly generated, different location
				heart.add();
				
				//update score accordingly
				snake.inc_score(10);
				
				//update status
				status.setText("High Score:" + Integer.toString(highscore) + "     " +
				"Level:" + Integer.toString(snake.get_level()) + 
				"     Score:" + Integer.toString(snake.current_score()) + "     ");
			}
			
			//check if snake has intersected a crown
			if (crown.intersects(snake)) {
				
				//randomly spawn a bad object, with frequency .6
				if (Math.random() < 0.6) {
					bad.add();
				}
				
				//update length of the snake
				snake.growSnake(5);
				
				//if score is high enough, randomly spawn another crown
				//with frequency .3 
				if ((snake.current_score() > 350) && (Math.random() < 0.3)) {
					crown.add_crown();
				}
				
				//check current score of snake
				//'level up' every 100 pts 
				if ((snake.current_score()!=0) && (snake.current_score() % 100 == 0))  {
					snake.inc_level();
					
					//increase velocity
					SNAKE_VELOCITY_X += 1;
					SNAKE_VELOCITY_Y += 1;
					
				}
				
				//increment score by 30 points
				snake.inc_score(30);
				
				//update status 
				status.setText("High Score:" + Integer.toString(highscore) + "     "
						+ "Level:" + Integer.toString(snake.get_level()) + 
				"     Score:" + Integer.toString(snake.current_score()) + "     ");		
			}
			
			
			//check if snake has hit the wall, intersected with a bad object 
			//or hit itself. if it has, the game is over 
			else if ((snake.hasHitWall() || (bad.intersects(snake)
				|| (snake.willHitItself())))) {
				
				//TODO: CHECK IF CURRENT SCORE IS GREATER THAN HIGH SCORE
				//IF IT IS, UPDATE HIGHSCORE.TXT
				
				if (snake.current_score() > highscore) {
					
					//set local highscore 
					highscore = snake.current_score();
					
					//write new highscore to file
					try {
						
						//open stream, write to file, close stream
						out = new BufferedWriter(new FileWriter("highscore.txt"));
						out.write(Integer.toString(snake.current_score()));
						out.close();
						
					} catch (IOException e) {} 

				}
				
				playing = false;
				
			}

			// update the display
			repaint();
		}
   }

	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		//check if instructions button has been pressed 
		if (instructionspressed) {
			
		//if so, draw the instructions screen
		g.drawImage(instructionsimg, 0, 120, 800, 400, null);
			
		}
		
		//check if game is over
		else if (!playing) {
	
			//if yes, draw game over sign 
			g.drawImage(img, 150, 150, 500, 300, null);
			
		} else {
			
			//redraw game components 
			snake.draw(g);
			heart.draw(g);
			bad.draw(g);
			crown.draw(g);	
			
			//checks to see if there is a health obj. on the game board
			if (health!=null) {
				health.draw(g);
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}

/*
 * Stuff to implement: 
 * !!!!IMPT: need to make sure two objs not generated at same pos
 * need to check to make sure no other objects are in the vicinity before adding 
 * eg. heart is not generated where snake body is 
 * HEALTH -> should appear in a diff place when it is regenerated 
 */
