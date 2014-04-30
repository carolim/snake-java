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
 * 
 * Rectangular 'wall' blocks, that appear with greater frequency,
 * generated at random sizes as the user progresses to higher levels
 * 
 * @author carolynlim
 *
 */
public class Walls extends GameObj {
	public static final int SIZE = 22; 
	public static final int INIT_X = (int)(Math.random() * 800); //randomized INITIAL x coord
	public static final int INIT_Y = (int)(Math.random() * 600); //randomized INITIAL y coord
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	public static final int MAX_NUM_WALLS = 8;
	
	//linked list to hold wall width + height
	public LinkedList<Point> wall_sizes;
	

	public Walls (int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, SIZE, SIZE, courtWidth,
				courtHeight);
		
		wall_sizes = new LinkedList<Point>();
	
	}
	
	//add walls to the game board at
	//random points
	@Override
	public void add() {
		
		//create random position for wall: 
		//coordinates represent the top left-hand
		//corner of the wall
		int rand_x = (int)(Math.random() * max_x);
		int rand_y = (int)(Math.random() * max_y);
		game_objs.add(new Point(rand_x, rand_y));
		
		//add a corresponding randomized width and height 
		//so that the 'wall' generated will be of an 
		//arbitrary size 
		int wall_width = (int)(Math.random()*50);
		int wall_height = (int)(Math.random()*50);
		wall_sizes.add(new Point(wall_width, wall_height));
		
	}

	@Override
	public void draw(Graphics g) {
		
		g.setColor(Color.RED);
		
		//draw all the walls so far (as long as there is 
		//something to draw)
		if (game_objs.size() >= 1) {
		for(int i = 0; i< game_objs.size();i++) {
			Point p = game_objs.get(i);
			Point wall_coords = wall_sizes.get(i);
			g.fillRect(p.x,p.y,wall_coords.x,wall_coords.y);
		}
		}
	}
}