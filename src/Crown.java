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
 * 'Good' Game Object, that gives the user 30 points 
 *  (TO DO:)Stored as a linkedlist, with their positions
 *  generated randomly throughout the game 
 *  
 *  This object, a crown, gives the user the most points of all the 
 *  'good objects'
 *  
 * @author carolynlim
 *
 */
public class Crown extends GameObj {
	public static final String img_file = "crown.png";
	public static final int SIZE = 30; //size of the crown
	public static final int INIT_X = (int)(Math.random() * 740); //randomized INITIAL x coord
	public static final int INIT_Y = (int)(Math.random() * 540); //randomized INITIAL y coord
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	public static final int MAX_NUM_CROWNS = 5; //max no. of crowns to be on the board at any one time

	private static BufferedImage img;
	
	private LinkedList<Point> crown_objs;

	public Crown(int courtWidth, int courtHeight) {
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
		crown_objs = new LinkedList<Point>();
		//add 'first heart' at initial positions x and y
		crown_objs.addFirst(new Point(pos_x, pos_y));
		
	
	}
	


	@Override
	public void draw(Graphics g) {
		//draw all crowns 
		for (int i=0; i< crown_objs.size(); i++) {
		Point p = crown_objs.get(i);
		g.drawImage(img, p.x, p.y, width, height, null);
		}
	}
	
	//check if any of the crowns has intersected an object
	@Override 
	public boolean intersects(GameObj obj) {
		for (int i=0; i<crown_objs.size();i++) {
			Point p = crown_objs.get(i);
			if (p.x + width >= obj.pos_x
					&& p.y + height >= obj.pos_y
					&& obj.pos_x + obj.width >= p.x
					&& obj.pos_y + obj.height >= p.y) {
				
				//has intersected, remove crown
				remove_crown(i);
				return true;
			}
		}
		return false;
	}
	
	//method that removes crown object intersected with 
	public void remove_crown(int i) {
		crown_objs.remove(i);
	}
	
	//method that generates a random number of crown objs
	//in random locations (only 1 at a time)
	public void add_crown() {

		//make sure there are not too many crowns on
		//the board at any one time 
		if (crown_objs.size() <= MAX_NUM_CROWNS) {
			
			//if there aren't, add crown at a random location
			int rand_x = (int)(Math.random() * max_x);
			int rand_y = (int)(Math.random() * max_y);
			crown_objs.add(new Point(rand_x, rand_y));
			
		}
	}

}