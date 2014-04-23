/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.util.LinkedList;

/**
 * Snake game object: Here, the snake's nodes are implemented 
 * as a linked list of points, each holding the coordinates 
 * of the various positions of the body. 
 * 
 */
public class Snake extends GameObj {

	//initial snake parameters 
	public static final int SIZE = 12;
	public static final int INIT_POS_X = 20;
	public static final int INIT_POS_Y = 20;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	
	//field variable: linkedlist for body of snake
	public LinkedList<Point> snake_body;
	
	//field variable: int to keep track of the score
	private int score;
	
	//field variables: ints to keep track of current level
	private int level;
	

	public Snake(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE,
				courtWidth, courtHeight);
		
		//set initial score to 0
		score = 0;
		
		//set initial level to 1
		level = 1;
		
		//initialize snake 
		snake_body = new LinkedList<Point>();
		//add 'head' at initial positions x and y
		snake_body.addFirst(new Point(pos_x, pos_y));
		//snake's initial length should be 3
		growSnake(5);
	}
	
	//override object move method 
	@Override 
	public void move() {
		
		//update all the joints of the snake (except the head) 
		//increment it so that each joint is now located at the
		//position of the one previously in front of it 
		
		 for(int i = snake_body.size()-1; i >= 1; i--) {
	            snake_body.get(i).setLocation(snake_body.get(i-1));
	        }
		 
		 //update position of the snake 'head'
	     snake_body.set(0, new Point((pos_x += v_x),(pos_y += v_y)));
	     clip();
	}
	
	//override obj intersects method 
	@Override 
	public boolean intersects(GameObj obj) {
		//get coordinates of snake 'head'
		Point p = snake_body.getFirst();
		return (p.x + width >= obj.pos_x
				&& p.y + height >= obj.pos_y
				&& obj.pos_x + obj.width >= p.x
				&& obj.pos_y + obj.height >= p.y);
	}
	
	//method to see if snake intersects itself
	public boolean intersectsItself() {
		//FILL IN THIS STUB
		
		return false;
	}
	
	//override hashitWall method 
	@Override
	public boolean hasHitWall() {
		//get coordinates of snake 'head'
		Point p = snake_body.getFirst();
		
		if ((p.x + v_x < 0) || (p.x + v_x >= max_x)
				|| (p.y + v_y < 0) || (p.y + v_y >= max_y)) {
			System.out.println(Integer.toString(p.x));
			return true;
		}
	
		else return false;
	}
	
	//method to let snake grow by however many nodes we want
	public void growSnake(int x) {
		while (x > 0) {
			//add a new point to the snake's tail
			snake_body.add(new Point (snake_body.getLast()));
			x--;
		}
	}
	
	
	//method to increment score when a crown
	//has been eaten
	public void inc_score_crown() {
		score += 10;
	}
	
	//method to get current score
	public int current_score() {
		return score;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.blue);
		
		//draw rest of body
		for(int i = 0; i < snake_body.size(); i++) {
			//get each particular point
            Point p = snake_body.get(i);
            //draw as a rectangle 
            g.fillRect(p.x, p.y, 15, 15);
            
        }
	}

	//method to increase current level
	public void inc_level() {
		level++;
	}

	public int get_level() {
		return level;
	}

	//method to increment score accordingly 
	//if snake has eaten a heart
	public void inc_score_heart() {
		score += 10;
	}

	//sets snake score as specified
	public void set_score(int i) {
		score = i;
	}
	
	//sets level as specified
	public void set_level(int i) {
		level = i;	
	}
	

}