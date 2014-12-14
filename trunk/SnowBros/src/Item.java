import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Item {
	public static int I_ST_DEATH = 0;
	public static int LIFE = 1;
	public static int SPEED = 2;
	public static int STRONG = 3;
	public static int DISTANCE = 4;
	public static int SCORE = 5;
	public static int I_WIDTH = 20;
	public static int I_HEIGHT = 20;
	
	public int state;
	private int x, y;
	private String p;
	
	private Rectangle bb;
	
	Item() {
		state = I_ST_DEATH;
		bb = new Rectangle(0, 0, I_WIDTH, I_HEIGHT);
	}
	
	int getState(){
		return state;
	}
		
	Rectangle getBBox(){
		return bb;
	}
	
	void birth(){
		if(Util.prob100(15)){
			state = LIFE;
		}
		else if(Util.prob100(15)){
			state = SPEED;
		}
		else if(Util.prob100(15)){
			state = STRONG;
		}
		else if(Util.prob100(15)){
			state = DISTANCE;
		}
		else if(Util.prob100(30)){
			state = SCORE;
		}
		else
			state = I_ST_DEATH;
		
		if(Util.prob100(15)){
			y = 140;
		}
		else if(Util.prob100(15)){
			y = 240;
		}
		else if(Util.prob100(15)){
			y = 340;
		}
		else if(Util.prob100(15)){
			y = 440;	
		}
		else if(Util.prob100(15)){
			y = 540;
		}
		else if(Util.prob100(15)){
			y = 640;
		}
		if(Util.prob100(15))
			p = "item5.png";
		else if(Util.prob100(15))
			p = "item6.png";
		else if(Util.prob100(15))
			p = "item7.png";
		else if(Util.prob100(15))
			p = "item8.png";
		else if(Util.prob100(15))
			p = "item9.png";
		else
			p = "item10.png";
		
		x = Util.rand(40,SnowBros.FRAME_W-40);
		bb.x = x - I_WIDTH/2;
		bb.y = y - I_HEIGHT/2;
	}
	
	void Blast(){
		state = I_ST_DEATH;
	}
	
	void Draw(Graphics g){
		
		if(state == LIFE){
			g.drawImage(SnowBrosComponent.item_red, x, y, I_WIDTH, I_HEIGHT, null);
		}
		if(state == SPEED){
			g.drawImage(SnowBrosComponent.item_green, x, y, I_WIDTH, I_HEIGHT, null);
		}
		if(state == STRONG){
			g.drawImage(SnowBrosComponent.item_blue, x, y, I_WIDTH, I_HEIGHT, null);
		}
		if(state == DISTANCE){
			g.drawImage(SnowBrosComponent.item_yellow, x, y, I_WIDTH, I_HEIGHT, null);
		}
		Image score;
		if(state == SCORE){
			try {
				score = ImageIO.read(new File(p));
				} catch (IOException e) { 
					
				}
			g.drawImage(SnowBrosComponent.item_green, x, y, I_WIDTH, I_HEIGHT, null);
		}
	}
}
