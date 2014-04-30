/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 	Heart object, which can be eaten by the snake for 10 points. 
 * 	Heart objects are stored in a linked list of Points corresponding 
 * 	to their respective positions, and are generated randomly on the 
 *  board when the snake 'eats' one of the hearts. 
 *  
 * @author carolynlim
 *
 */
public class Heart extends GameObj {
	
	
	public static final String img_file = "good.png";
	public static final int SIZE = 30; //size of the heart 
	public static final int INIT_X = (int)(Math.random() * 740); //randomized INITIAL x coord
	public static final int INIT_Y = (int)(Math.random() * 540); //randomized INITIAL y coord
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	public static final int MAX_NUM_HEARTS = 5; //the max. number of hearts on the board at any time

	private static BufferedImage img;
	
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
		
	}

	@Override
	public void draw(Graphics g) {
		
		//draw all hearts in list 
		for (int i=0; i< game_objs.size(); i++) {
		Point p = game_objs.get(i);
		g.drawImage(img, p.x, p.y, width, height, null);
		}
	}


	//check if any of the hearts has intersected an object
	@Override 
	public boolean intersects(GameObj obj) {
		for (int i=0; i<game_objs.size();i++) {
			Point p = game_objs.get(i);
			if (p.x + width >= obj.pos_x
					&& p.y + height >= obj.pos_y
					&& obj.pos_x + obj.width >= p.x
					&& obj.pos_y + obj.height >= p.y) {
				
				//has intersected, remove heart
				remove(i);
				return true;
			}
		}
		return false;
	}

	//method that generates a random number of heart objs
	//in random locations (between 1-3 at a time)
	@Override
	public void add() {
		int num_times = (int)(Math.random()*3);
		
		//make sure there is always at least one heart
		//on the board
		if ((num_times==0) && (game_objs.size()==0)) {
			num_times = 1;
		}
		
		//make sure there are not too many hearts on
		//the board at any one time 
		if (game_objs.size() <= MAX_NUM_HEARTS) {
		for(int i = 0; i<num_times; i++) {
			int rand_x = (int)(Math.random() * max_x);
			int rand_y = (int)(Math.random() * max_y);
			game_objs.add(new Point(rand_x, rand_y));
		}
		}
	}
}