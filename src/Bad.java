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
 * GameObj bad objects, that cause the game to be over if the 
 * snake intersects with them at any time 
 * Bad objects are stored in a linked list, and are added randomly 
 * to the game board as the game goes on
 * @author carolynlim
 *
 */
public class Bad extends GameObj {
	public static final String img_file = "bad.png";
	public static final int SIZE = 22; //size of the bad object
	public static final int INIT_X = (int)(Math.random() * 800); //randomized INITIAL x coord
	public static final int INIT_Y = (int)(Math.random() * 600); //randomized INITIAL y coord
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;

	private static BufferedImage img;
	
	//linkedlist containing all 'bad' objects
	public LinkedList<Point> bad_objs;
	

	public Bad (int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, SIZE, SIZE, courtWidth,
				courtHeight);
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
		
		//initialize linkedlist
		bad_objs = new LinkedList<Point>();
		//add first 'bad object' to the list
		bad_objs.addFirst(new Point(pos_x, pos_y));
		
	}
	
	//add a random number of bad objects to
	//random locations on the game board 
	public void add_bad() {
		int num_times = (int)(Math.random()*3);
		
		 for (int i =0; i<num_times; i++) {
		//create random position for bad obj
		int rand_x = (int)(Math.random() * max_x);
		int rand_y = (int)(Math.random() * max_y);
		bad_objs.add(new Point(rand_x, rand_y));
		 }
		
	}
	
	
	@Override 
	public boolean intersects(GameObj obj) {
		//iterate through linked list, 
		//see if anything is touching the obj
		for (int i=0; i<bad_objs.size(); i++) {
			Point p = bad_objs.get(i);
			if (p.x + width >= obj.pos_x
					&& p.y + height >= obj.pos_y
					&& obj.pos_x + obj.width >= p.x
					&& obj.pos_y + obj.height >= p.y) return true;
		}
		
		return false;
	}

	@Override
	public void draw(Graphics g) {
		//draw all the  bad objects thus far 
		for(int i = 0; i< bad_objs.size();i++) {
			Point p = bad_objs.get(i);
			g.drawImage(img, p.x, p.y, width, height, null);
		}
	}
}