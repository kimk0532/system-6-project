import java.awt.*;

public class Missile {
	public static int M_ST_DEATH = 0;
	public static int M_ST_ALIVE = 1;
	public static int M_WIDTH = 20;
	public static int M_HEIGHT = 30;
	public static int RIGHT = 1;
	public static int LEFT = -1;
	
	public int state;
	private int x, y, z;
	private int dx, motion ;
	int di; //이동거리
	
	
	private Rectangle bb;
	
	Missile() {
		z = 0;
		state = M_ST_DEATH;
		bb = new Rectangle(0, 0, M_WIDTH, M_HEIGHT);
		di = 75;
	}
	
	int getState(){
		return state;
	}
	Rectangle getBBox(){
		return bb;
	}
	
	void Shot (int x, int y){
		state = M_ST_ALIVE;
		this.x = x;
		this.y = y-25;
		dx = 15; //이동거리
		bb = new Rectangle(x - M_WIDTH/2, y - M_HEIGHT/2, M_WIDTH, M_HEIGHT);
	}
	
	void MoveR() {
		if(state == M_ST_ALIVE){
			motion = RIGHT;
			z += 5;
			x += dx;
			bb.x = x - M_WIDTH/2;
			bb.y = y - M_HEIGHT/2;
			if(x < -40 || x > SnowBros.FRAME_W)
				state = M_ST_DEATH;
		}
		if(z > di){
			state = M_ST_DEATH;
			z = 0;
		}
	}
	
	void MoveL() {
		if(state == M_ST_ALIVE){
			motion = LEFT;
			z += 5;
			x -= dx;
			bb.x = x - M_WIDTH/2;
			bb.y = y - M_HEIGHT/2;
			if(x < -40 || x > SnowBros.FRAME_W)
				state = M_ST_DEATH;
		}
		if(z > di){
			state = M_ST_DEATH;
			z = 0;
		}
	}
	
	void Blast(){
		state = M_ST_DEATH;
		z = 0;
	}
	
	void Draw(Graphics g){
		if(state == M_ST_ALIVE){
			if(motion == RIGHT && SnowBrosComponent.big == 0)
				g.drawImage(SnowBrosComponent.missile_right, x, y, M_WIDTH, M_HEIGHT, null);
			else if(motion ==LEFT && SnowBrosComponent.big == 0)
				g.drawImage(SnowBrosComponent.missile_left, x, y, M_WIDTH, M_HEIGHT, null);
			else if(motion == RIGHT && SnowBrosComponent.big == 1)
				g.drawImage(SnowBrosComponent.missileb_right, x, y, M_WIDTH, M_HEIGHT, null);
			else if(motion == LEFT && SnowBrosComponent.big == 1)
				g.drawImage(SnowBrosComponent.missileb_left, x, y, M_WIDTH, M_HEIGHT, null);
		}
	}
}
