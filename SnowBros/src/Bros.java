import java.awt.*;

public class Bros {
	public static int B_ST_ALIVE = 1;
	public static int B_ST_BLAST = 2;
	public static int B_ST_JUMPUP = 3;
	public static int B_WIDTH = 50;
	public static int B_HEIGHT = 100;
	public static int RIGHT = 1;
	public static int LEFT = -1;
	public int x, y,state, RL, jumpState;
	private int startY;
	private Rectangle bb;
	int xi,yi; //speed ¾ÆÀÌÅÛ
	
	Bros(){
		state = B_ST_ALIVE;
		x = SnowBros.FRAME_W / 2;
		y = SnowBros.FRAME_H - 55;
		bb = new Rectangle(x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT);
		xi = 10;
		yi = 10;
	}
	
	int getState() { return state; }
	int getX() { return x; }
	int getY() { return y; }

	Rectangle getBBox(){
		return bb;
	}
	
	void StartBros(){
		state = B_ST_ALIVE;
		x = SnowBros.FRAME_W / 2;
		y = SnowBros.FRAME_H - 55;
		bb = new Rectangle(x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT);
	}
	
	void MoveRight(){
		x += xi;
		RL = RIGHT;
		bb.x = x - B_WIDTH/2;
		bb.y = y - B_HEIGHT/2;
	}
	
	void MoveLeft(){
		x-= xi;
		RL = LEFT;
		bb.x = x - B_WIDTH/2;
		bb.y = y - B_HEIGHT/2;
	}
	
	void Jump(){
		startY = y;
		jumpState = 1;
		state = B_ST_JUMPUP;
		bb.x = x - B_WIDTH/2;
		bb.y = y - B_HEIGHT/2;
	}
	
	void JumpUp(){
		y -= yi; 
		if(startY - y >= 100)
			state = B_ST_ALIVE;
		bb.x = x - B_WIDTH/2;
		bb.y = y - B_HEIGHT/2;
	}
	
	void JumpDown(){
		y += 10;
		bb.x = x - B_WIDTH/2;
		bb.y = y - B_HEIGHT/2;
	}
	
	void Blast(){
		state = B_ST_BLAST;
		StartBros();
	}
	
	void Item(){
		
	}
	
	void Draw(Graphics g){
		if(startY-y == 30 || startY-y == 70){
			if(state == B_ST_JUMPUP){
				g.setColor(Color.WHITE);
				g.fillOval(x-50, y-25, 50, 50);
				g.fillOval(x, y-25, 50, 50);
				g.setColor(Color.BLACK);
				g.fillOval(x-40, y-10, 10, 10);
				g.fillOval(x-40, y+10, 10, 10);
				g.setColor(Color.RED);
				g.fillArc(x-8, y-12, 15, 15, 0, 360);
			}
			else{
				g.setColor(Color.WHITE);
				g.fillOval(x-25, y-50, 50, 50);
				g.fillOval(x-25, y, 50, 50);
				g.setColor(Color.BLACK);
				g.fillOval(x-15, y-30, 10, 10);
				g.fillOval(x+5, y-30, 10, 10);
				g.setColor(Color.RED);
				g.fillArc(x-8, y-22, 15, 15, 0, 360);
			}
		}
		else{
			g.setColor(Color.WHITE);
			g.fillOval(x-25, y-50, 50, 50);
			g.fillOval(x-25, y, 50, 50);
			g.setColor(Color.BLACK);
			g.fillOval(x-15, y-30, 10, 10);
			g.fillOval(x+5, y-30, 10, 10);
			g.setColor(Color.RED);
			g.fillArc(x-8, y-22, 15, 15, 0, 360);
		}
	}
}
