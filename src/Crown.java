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
	public static final int INIT_X = (int)(Math.random() * 800); //randomized INITIAL x coord
	public static final int INIT_Y = (int)(Math.random() * 600); //randomized INITIAL y coord
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;

	private static BufferedImage img;

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
	
	}
	


	@Override
	public void draw(Graphics g) {
		//apple is drawn at a random pos on the gamecourt 
		g.drawImage(img, pos_x, pos_y, width, height, null);
	}


	//method that generates a new crown obj in a random location
	public void new_location() {
		
		pos_x = (int)(Math.random() * max_x);
		pos_y = (int)(Math.random() * max_y);
	}


}