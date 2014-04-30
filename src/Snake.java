/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;

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

	private static int score; //keeps track of score associated with the snake
	private static int level; //keeps track of current level
	

	public Snake(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE,
				courtWidth, courtHeight);
		
		score = 0; //initial score is 0
		level = 1; //initial level is 1
		
	}
	
	@Override 
	public void move() {
		
		//update all the joints of the snake (except the head) 
		//increment it so that each joint is now located at the
		//position of the one previously in front of it 
		
		 for(int i = game_objs.size()-1; i >= 1; i--) {
	            game_objs.get(i).setLocation(game_objs.get(i-1));
	        }
		 
		 //update position of the snake 'head'
	     game_objs.set(0, new Point((pos_x += v_x),(pos_y += v_y)));
	     clip();
	}
	
	@Override 
	public boolean intersects(GameObj obj) {
		//get coordinates of snake 'head'
		Point p = game_objs.getFirst();
		return (p.x + width >= obj.pos_x
				&& p.y + height >= obj.pos_y
				&& obj.pos_x + obj.width >= p.x
				&& obj.pos_y + obj.height >= p.y);
	}
	
	
	//method to see if snake intersects itself
	public boolean willHitItself() {
		
		//loop through to see if coordinates of 
		//the snake's head and any of his body match
		//if so, the snake has hit itself 
		if (game_objs.size() > 1) {
			
		for (int i=1; i<game_objs.size(); i++) {
			Point p = game_objs.getFirst();
			Point p1 = game_objs.get(i);
			
			if ((p.x + v_x == p1.x) && (p.y + v_y == p1.y)) 
				return true;
			}
		}
		
		//snake has not hit its own body 
		return false;
	}
	
	
	@Override
	public boolean hasHitWall() {
		
		//get coordinates of snake 'head'
		Point p = game_objs.getFirst();
		
		if ((p.x + v_x < 0) || (p.x + v_x >= max_x)
				|| (p.y + v_y < 0) || (p.y + v_y >= max_y)) {
			System.out.println(Integer.toString(p.x));
			return true;
		}
	
		else return false;
	}
	
	//method to let snake grow by however many 'nodes' we want
	public void growSnake(int x) {
		while (x > 0) {
			
			//add a new point to the snake's tail
			game_objs.add(new Point (game_objs.getLast()));
			x--;
			
		}
	}

	
	//method to get current score
	public int current_score() {
		return score;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.blue);
		
		//draw rest of body
		for(int i = 0; i < game_objs.size(); i++) {
			//get each particular point
            Point p = game_objs.get(i);
            //draw as a rectangle 
            g.fillRect(p.x, p.y, 15, 15);
            
        }
	}

	//increase current level
	public void inc_level() {
		level++;
	}
	
	//returns current level
	public int get_level() {
		return level;
	}
	
	//sets level as specified
	public void set_level(int i) {
		level = i;	
	}

	//sets snake score as specified
	public void set_score(int i) {
		score = i;
	}
	
	//increments score by amount i
	public void inc_score(int i) {
		score += i;
	}
	
	//method to shrink snake by however many 'nodes' we want
	public void shrinkSnake(int i) {
		
		//check to make sure snake has enough nodes
		if (game_objs.size() > i) {
			for (int j=0;j<i;j++) {
				game_objs.removeLast();
			} 
			} else {
				//remove all nodes but the first node
				for (int j=1;j<game_objs.size();j++) {
					game_objs.removeLast();
				}
			}
		}
}