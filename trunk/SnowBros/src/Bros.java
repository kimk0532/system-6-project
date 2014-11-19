import java.awt.*;

public class Bros {
	public static int B_ST_ALIVE = 1;
	public static int B_ST_BLAST = 2;
	public static int B_ST_JUMPUP = 3;
	public static int B_ST_JUMPDOWN = 4;
	public static int B_WIDTH = 60;
	public static int B_HEIGHT = 80;
	public static int RIGHT = 0;
	public static int LEFT = 1;
	public int x, y,state, RL, jumpState;
	private int count, startY;
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
		RL = RIGHT;
	}
	
	void MoveLeft(){
		x-= 5;
		RL = LEFT;
	}
	
	void Jump(){
		startY = y;
		jumpState = 1;
		state = B_ST_JUMPUP;
	}
	
	void Jumpup(){
		y -= 10;
		if(startY - y > 150)
			state = B_ST_ALIVE;
	}
	
	void Jumpdown(){
		y += 10;
	}
	
	void Item(){
		
	}
	
	void Draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillOval(x-25, y-25, 50, 50);
		g.fillOval(x-25, y+25, 50, 50);
		g.setColor(Color.BLACK);
		g.fillOval(x-15, y-12, 10, 10);
		g.fillOval(x+5, y-12, 10, 10);
		g.setColor(Color.RED);
		g.fillArc(x-8, y-7, 15, 15, 0, 360);
	}
}
