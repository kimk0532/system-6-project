import java.awt.*;


public class Enemy {
	public static int E_ST_DEATH = 0;
	public static int E_ST_ALIVE = 1;
	public static int E_ST_DAMAGE = 2;
	public static int E_ST_BALL = 3;
	public static int E_ST_ROLL = 4;
	public static int E_ST_BLAST = 5;
	public static int E_WIDTH = 50;
	public static int E_HEIGHT = 100;
	
	public int state, damage;
	public int x, y, dx, dy, tx;
	private Rectangle bb;
	int da; //damage ¡ı∞°
	
	Enemy(){
		state = E_ST_DEATH;
		damage = 0;
		bb = new Rectangle(0,0,E_WIDTH,E_HEIGHT);
		da = 1;
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
	
	void birth(int layer){
		state = E_ST_ALIVE;
		x = SnowBros.FRAME_W/3;
		dx = 5;
		dy = 10;
		tx = SnowBros.FRAME_W - 50;
		y = SnowBros.FRAME_H - 55 - (layer * 100);
		bb.x = (int) (x - E_WIDTH/2);
		bb.y = (int) (y - E_HEIGHT/2);
	}
	
	void blast() {
		state = E_ST_BLAST;
		bb.x = 0;
		bb.y = 0;
	}
	
	void Move(){
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
			
		}
	}
	
	void Damage(){
		if(damage < 4)
			if(damage>=3)
				damage+=1; //¥´¿Ã 1∏∏ Ω◊¿Ã∞‘
			else
				damage+=da; //damage¡ı∞°
		if(damage == 4)
			state = E_ST_BALL;
		else
			state = E_ST_DAMAGE;
	}
	
	void Roll(){
		x += dx;
		bb.x = x - E_WIDTH/2;
		bb.y = y - E_HEIGHT/2;
	}
	
	void Bounce(){
		dx *= -1;
	}
	
	void Down(){
		y += dy;
	}
	
	void Draw(Graphics g){
		if(state == E_ST_ALIVE){
			g.setColor(Color.RED);
			g.fillOval(x-25, y-50, 50, 50);
			g.fillOval(x-25, y, 50, 50);
		}
		else if(state == E_ST_DAMAGE){
			g.setColor(Color.RED);
			g.fillOval(x-25, y-50, 50, 50);
			g.fillOval(x-25, y, 50, 50);
			g.setColor(Color.WHITE);
			g.fillRect(x-25, y+25-(25*(damage-1)), 50, 25+(25*(damage-1)));
		}
		else if(state == E_ST_BALL || state == E_ST_ROLL){
			g.setColor(Color.RED);
			g.fillOval(x-25, y-50, 50, 50);
			g.fillOval(x-25, y, 50, 50);
			g.setColor(Color.WHITE);
			g.fillRect(x-25, y+25-(25*(damage-1)), 50, 25+(25*(damage-1)));
		}
	}
}
