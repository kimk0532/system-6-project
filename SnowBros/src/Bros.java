import java.awt.*;

public class Bros {
	public static int B_ST_ALIVE = 1;
	public static int B_ST_BLAST = 2;
	public static int B_WIDTH = 60;
	public static int B_HEIGHT = 80;
	
	private int state, x, y, count;
	private Rectangle bb;
	
	Bros(){
		state = B_ST_ALIVE;
		x = SnowBros.FRAME_W / 2;
		y = SnowBros.FRAME_H - 80;
		bb = new Rectangle(x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT);
	}
	
	int getState() { return state; }
	int getX() { return x; }
	int getY() { return y; }
	
	void StartBros(){
		
	}
	
	void MoveRight(){
		x += 5;
	}
	
	void MoveLeft(){
		x-= 5;
	}
	
	void Jump(){
		
	}
	
	void Item(){
		
	}
	
	void Draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillOval(x-25, y-25, 50, 50);
		g.fillOval(x-25, y+25, 50, 50);
	}
}
