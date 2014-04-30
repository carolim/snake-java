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
 * This 'Health' Game Object appears very occasionally on the game board, 
 * and when it does, only stays for a limited period of time. 
 * When the Snake intersects this Game Object, this will cause a clipping
 * of its tail, such that it is now shorter than before 
 * 
 * @author carolynlim
 *
 */

public class Health extends StaticObj {
	
	
	public static final String img_file = "health.png";
	public static final int SIZE = 30; //size of the heart 
	public static final int INIT_X = (int)(Math.random() * 720); //randomized INITIAL x coord
	public static final int INIT_Y = (int)(Math.random() * 520); //randomized INITIAL y coord
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	public static final int MAX_NUM_HEALTH = 2;

	private static BufferedImage img;
	
	public Health(int courtWidth, int courtHeight) {
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
		g.drawImage(img, pos_x, pos_y, width, height, null);
	}
	
	//method that, when called, allows object to respawn at a different position
	public void respawn() {
		pos_x = (int)(Math.random() * 800);
		pos_y = (int)(Math.random() * 600);
	}
}
