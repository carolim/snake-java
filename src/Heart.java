/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

/**
 * 'Good' Game Object, that gives the user 10 points 
 *  Stored as a linkedlist, with their positions
 *  generated randomly throughout the game 
 *  
 *  This object gives the user the least number of points of all the 
 *  'good objects'
 *  
 * @author carolynlim
 *
 */
public class Heart extends GameObj {
	public static final String img_file = "good.png";
	public static final int SIZE = 30; //size of the apple
	public static final int INIT_X = (int)(Math.random() * 800); //randomized INITIAL x coord
	public static final int INIT_Y = (int)(Math.random() * 600); //randomized INITIAL y coord
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	public static final int MAX_NUM_HEARTS = 8;

	private static BufferedImage img;
	
	private LinkedList<Point> heart_objs;
	
	public Heart(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, SIZE, SIZE, courtWidth,
				courtHeight);
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
		
		//intitalize linkedlist
		heart_objs = new LinkedList<Point>();
		//add 'first heart' at initial positions x and y
		heart_objs.addFirst(new Point(pos_x, pos_y));
		
	}
	


	@Override
	public void draw(Graphics g) {
		//draw all hearts 
		for (int i=0; i< heart_objs.size(); i++) {
		Point p = heart_objs.get(i);
		g.drawImage(img, p.x, p.y, width, height, null);
		}
	}


	//check if any of the hearts has intersected an object
	@Override 
	public boolean intersects(GameObj obj) {
		for (int i=0; i<heart_objs.size();i++) {
			Point p = heart_objs.get(i);
			if (p.x + width >= obj.pos_x
					&& p.y + height >= obj.pos_y
					&& obj.pos_x + obj.width >= p.x
					&& obj.pos_y + obj.height >= p.y) {
				
				//has intersected, remove heart
				remove_heart(i);
				return true;
			}
		}
		return false;
	}
	
	//method that removes heart object intersected with 
	public void remove_heart(int i) {
		heart_objs.remove(i);
	}
	
	//method that generates a random number of heart objs
	//in random locations (between 1-3 at a time)
	public void add_heart() {
		int num_times = (int)(Math.random()*3);
		
		//make sure there is always at least one heart
		//on the board
		if ((num_times==0) && (heart_objs.size()==0)) {
			num_times = 1;
		}
		
		//make sure there are not too many hearts on
		//the board at any one time 
		if (heart_objs.size() <= MAX_NUM_HEARTS) {
		for(int i = 0; i<num_times; i++) {
			int rand_x = (int)(Math.random() * max_x);
			int rand_y = (int)(Math.random() * max_y);
			heart_objs.add(new Point(rand_x, rand_y));
		}
		}
	}


}