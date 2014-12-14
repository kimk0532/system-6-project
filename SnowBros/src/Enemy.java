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
	public static int RIGHT = 1;
	public static int LEFT = -1;
	
	public int state, damage;
	public int image, imagecount;
	public int x, y, dx, dy, tx, RL;
	private Rectangle bb;
	int da; //damage ¡ı∞°
	int m;
	
	Enemy(){
		state = E_ST_DEATH;
		damage = 0;
		bb = new Rectangle(0,0,E_WIDTH,E_HEIGHT);
		da = 1;
		m = 0;
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
	
	void birth1(int layer){
		state = E_ST_ALIVE;
		if(layer/2 == 1){
			x = 100;
			dx = 5;
			tx = SnowBros.FRAME_W - 50;
			RL = RIGHT;
		}
		else{
			x = SnowBros.FRAME_W-100;
			dx = -5;
			tx = 50;
			RL = LEFT;
		}
		dy = 20;
		
		y = SnowBros.FRAME_H - 55 - (layer * 100);
		bb.x = (int) (x - E_WIDTH/2);
		bb.y = (int) (y - E_HEIGHT/2);
	}
	
	void birth2(int layer){
		state = E_ST_ALIVE;
		dx = 5;
		dy = 20;		
		bb.x = (int) (x - E_WIDTH/2);
		bb.y = (int) (y - E_HEIGHT/2);
		
		if(layer == 2 || layer == 4){
			m = 1;
			x = SnowBros.FRAME_W/3;
			y = SnowBros.FRAME_H - 55 - (layer * 100);
			tx = SnowBros.FRAME_W - 50;
			RL = RIGHT;
		}
		if(layer == 1){
			m = 2;
			x = 30;
			y = SnowBros.FRAME_H - 55 - (layer * 100);
			tx = 280;
			RL = RIGHT;
		}
		if(layer == 3){
			m = 3;
			x = SnowBros.FRAME_W - 30;
			y = SnowBros.FRAME_H - 55 - (layer * 100);
			tx = SnowBros.FRAME_W - 30;
		}
		if(layer == 0){
			m = 3;
			x = SnowBros.FRAME_W - 30;
			y = SnowBros.FRAME_H - 55 - (1 * 100);
			tx = SnowBros.FRAME_W - 30;
		}
	}
	
	void birth3(int layer){
		state = E_ST_ALIVE;
		dx = 5;
		dy = 20;
		bb.x = (int) (x - E_WIDTH/2);
		bb.y = (int) (y - E_HEIGHT/2);
		
		if(layer == 4 || layer == 2 || layer == 0){
			m = 4;
			x = 280;
			y = SnowBros.FRAME_H - 55 - ((layer+1) * 100);
			tx = SnowBros.FRAME_W - 180;
			RL = RIGHT;
		}
		if(layer == 3 || layer == 1){
			m = 5;
			x = SnowBros.FRAME_W - 280;
			y = SnowBros.FRAME_H - 55 - ((layer+1) * 100);
			tx = SnowBros.FRAME_W - 280;
		}
	}
	
	void blast() {
		state = E_ST_BLAST;
		bb.x = 0;
		bb.y = 0;
	}
	
	void Move(){
		if(state == E_ST_ALIVE){
			x += dx;
			if(m == 0 || m == 1){
				if(Math.abs(tx-x) < 100){
					if(tx > SnowBros.FRAME_W/2){
						RL = LEFT;
						tx = 50;
						dx = -5;
					}
					else{
						RL = RIGHT;
						tx = SnowBros.FRAME_W-50;
						dx = 5;
					}
				}
			}
			if(m == 2){
				if(Math.abs(tx-x) < 50){
					if(tx > 150){
						tx = 30;
						dx = -5;
						RL = LEFT;
					}
					else{
						tx = 280;
						dx = 5;
						RL = RIGHT;
					}
				}
			}
			
			if(m == 3){
				if(Math.abs(tx-x) < 50){
					if(tx > SnowBros.FRAME_W-150){
						tx = SnowBros.FRAME_W-280;
						dx = -5;
						RL = LEFT;
					}
					else{
						tx = SnowBros.FRAME_W-30;
						dx = 5;
						RL = RIGHT;
					}
				}
			}
			
			if(m == 4){
				if(Math.abs(tx-x) < 100){
					if(tx > SnowBros.FRAME_W/2+50){
						tx = 250;
						dx = -5;
						RL = LEFT;
					}
					else{
						tx = SnowBros.FRAME_W-180;
						dx = 5;
						RL = RIGHT;
					}
				}
			}
			if(m == 5){
				if(Math.abs(tx-x) < 100){
					if(tx > SnowBros.FRAME_W/2-50){
						tx = 180;
						dx = -5;
						RL = LEFT;
					}
					else{
						tx = SnowBros.FRAME_W-250;
						dx = 5;
						RL = RIGHT;
					}
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
			imagecount++;
			image = imagecount / 3;
			if(image >= SnowBrosComponent.enemy_move_r.length){
				image = 0;
				imagecount = 0;
			}
			if(RL == RIGHT)
				g.drawImage(SnowBrosComponent.enemy_move_r[image], x - E_WIDTH/2, y - E_HEIGHT/2, E_WIDTH, E_HEIGHT, null);
			else if(RL == LEFT){
				g.drawImage(SnowBrosComponent.enemy_move_l[image], x - E_WIDTH/2, y - E_HEIGHT/2, E_WIDTH, E_HEIGHT, null);
			}
		}
		else if(state == E_ST_DAMAGE){
			g.drawImage(SnowBrosComponent.enemy_damage[damage-1], x - E_WIDTH/2, y - E_HEIGHT/2, E_WIDTH, E_HEIGHT, null);
		}
		else if(state == E_ST_BALL) {
			g.drawImage(SnowBrosComponent.enemy_damage[3], x - E_WIDTH/2, y - E_HEIGHT/2, E_WIDTH, E_HEIGHT, null);
		}
		else if(state == E_ST_ROLL){
			imagecount++;
			image = imagecount / 3;
			if(image >= 3){
				image = 0;
				imagecount = 0;
			}
			g.drawImage(SnowBrosComponent.enemy_damage[image+3], x - E_WIDTH/2, y - E_HEIGHT/2, E_WIDTH, E_HEIGHT, null);
		}
	}
}
