import java.awt.*;


public class Enemy {
	public static int E_ST_DEATH = 0;
	public static int E_ST_ALIVE = 1;
	public static int E_ST_BLAST = 2;
	public static int E_WIDTH = 50;
	public static int E_HEIGHT = 100;
	
	public int state;
	private int dr, count;
	private int x, y, dx, dy, tx, ty, len;
	private Rectangle bb;
	
	Enemy(){
		state = E_ST_DEATH;
		bb = new Rectangle(0,0,E_WIDTH,E_HEIGHT);
	}
	
	int getState(){
		return state;
	}
	
	double getX(){
		return x;
	}
	
	double getY(){
		return y;
	}
	
	Rectangle getBBox(){
		return bb;
	}
	
	void birth(){
		state = E_ST_ALIVE;
		x = SnowBros.FRAME_W/3;
		dx = 5;
		tx = SnowBros.FRAME_W - 50;
		y = SnowBros.FRAME_H - 80;
		bb.x = (int) (x - E_WIDTH/2);
		bb.y = (int) (y - E_HEIGHT/2);
	}
	
	void blast() {
		state = E_ST_BLAST;
		count = 15;
		bb.x = 0;
		bb.y = 0;
	}
	
	void move(){
		if(state == E_ST_ALIVE){
			x += dx;
			if(Math.abs(tx-x) < 100){
				if(tx > SnowBros.FRAME_W/2){
					tx = 50;
					dx = -5;
				}
				else{
					tx = SnowBros.FRAME_W-50;
					dx = 5;
				}
			}
			bb.x = x - E_WIDTH/2;
			bb.y = y - E_HEIGHT/2;
			
		}
		else if ( state == E_ST_BLAST){
			count--;
			if(count == 0)
				state = E_ST_DEATH;
		}
	}
	
	void Draw(Graphics g){
		if(state == E_ST_ALIVE){
			g.setColor(Color.RED);
			g.fillOval(x-25, y-25, 50, 50);
			g.fillOval(x-25, y+25, 50, 50);
		}
	}
}
