import java.awt.*;

public class Missile {
	public static int M_ST_DEATH = 0;
	public static int M_ST_ALIVE = 1;
	public static int M_WIDTH = 20;
	public static int M_HEIGHT = 30;
	
	public int state;
	private int x, y, z;
	private int dx;
	private int count;
	
	private Rectangle bb;
	
	Missile() {
		z = 0;
		state = M_ST_DEATH;
		bb = new Rectangle(0, 0, M_WIDTH, M_HEIGHT);
	}
	
	int getState(){
		return state;
	}
	Rectangle getBBox(){
		return bb;
	}
	
	void shot (int x, int y){
		if(state == M_ST_DEATH){
			state = M_ST_ALIVE;
			this.x = x;
			this.y = y;
			dx = -20;
			bb = new Rectangle(x - M_WIDTH/2, y - M_HEIGHT/2, M_WIDTH, M_HEIGHT);
		}
	}
	
	void moveR() {
		if(state == M_ST_ALIVE){
			z += 5;
			x += dx;
			bb.y = y -M_HEIGHT/2;
			if(x < -40 || x > SnowBros.FRAME_W)
				state = M_ST_DEATH;
		}
		if(z > 50){
			state = M_ST_DEATH;
			z = 0;
		}
	}
	
	void moveL() {
		if(state == M_ST_ALIVE){
			z += 5;
			x -= dx;
			bb.y = y -M_HEIGHT/2;
			if(x < -40 || x > SnowBros.FRAME_W)
				state = M_ST_DEATH;
		}
		if(z > 50){
			state = M_ST_DEATH;
			z = 0;
		}
	}
	
	void blast(){
		state = M_ST_DEATH;
	}
	
	void Draw(Graphics g){
		if(state == M_ST_ALIVE){
			g.fillOval(x, y, M_WIDTH, M_HEIGHT);
		}
	}
}
