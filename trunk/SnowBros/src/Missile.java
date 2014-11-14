import java.awt.*;

public class Missile {
	public static int M_ST_DEATH = 0;
	public static int M_ST_ALIVE = 1;
	public static int M_WIDTH = 20;
	public static int M_HEIGHT = 30;
	
	int state;
	private int x, y;
	private int dy;
	private int count;
	
	private Rectangle bb;
	
	Missile() {
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
			dy = -20;
			bb = new Rectangle(x - M_WIDTH/2, y - M_HEIGHT/2, M_WIDTH, M_HEIGHT);
		}
	}
	
	void move() {
		if(state == M_ST_ALIVE){
			y += dy;
			bb.y = y -M_HEIGHT/2;
			if(y < -40 )
				state = M_ST_DEATH;
			count = (count + 1) % 2;
		}
	}
	
	void blast(){
		state = M_ST_DEATH;
	}
	
	void Draw(Graphics g){
		if(state == M_ST_ALIVE){
			g.setColor(Color.RED);
			g.fillOval(x - 4, y - 20, 8, 16);
			g.setColor(new Color(0, 128, 0));
			g.fillOval(x - 10, y, 20, 8);
			g.setColor(new Color(0, 192, 0));
			g.fillRect(x - 4, y - 12, 8, 22);
			if( count == 1){
				g.setColor(Color.RED);
				g.fillOval(x - 4, y +10, 8, 20);
				g.setColor(Color.YELLOW);
				g.fillOval(x - 2, y + 10, 4, 14);
			}
		}
	}
}
