import java.awt.*;

public class Bros {
	public static int B_ST_DEATH = 0;
	public static int B_ST_ALIVE = 1;
	public static int B_ST_BLAST = 2;
	public static int B_ST_JUMPUP = 3;
	public static int B_WIDTH = 50;
	public static int B_HEIGHT = 100;
	public static int BIRTH = 1;
	public static int BLAST = 2;
	public static int MOVE_R = 3;
	public static int MOVE_L = 4;
	public static int SMOVE_R = 5;
	public static int SMOVE_L = 6;
	public static int JUMP_R = 7;
	public static int JUMP_L = 8;
	public static int SHOT_R = 9;
	public static int SHOT_L = 10;
	public static int ROLL_R = 11;
	public static int ROLL_L = 12;
	public static int NEXT = 13;
	
	
	public static int RIGHT = 1;
	public static int LEFT = -1;
	public int x, y,state, RL, jumpState, motion;
	private int startY;
	private Rectangle bb;
	int xi,yi; //speed ¾ÆÀÌÅÛ
	
	Bros(){
		motion = 0;
		state = B_ST_DEATH;
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
		motion = BIRTH;
		state = B_ST_ALIVE;
		x = SnowBros.FRAME_W / 2;
		y = SnowBros.FRAME_H - 55;
		bb = new Rectangle(x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT);
	}
	
	void MoveRight(){
		motion = MOVE_R;
		x += xi;
		RL = RIGHT;
		bb.x = x - B_WIDTH/2;
		bb.y = y - B_HEIGHT/2;
	}
	
	void MoveLeft(){
		motion = MOVE_L;
		x-= xi;
		RL = LEFT;
		bb.x = x - B_WIDTH/2;
		bb.y = y - B_HEIGHT/2;
	}
	
	void Jump(){
		if(RL == RIGHT)
			motion = JUMP_R;
		else if(RL == LEFT)
			motion = JUMP_L;
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
		motion = BLAST;
		state = B_ST_BLAST;
		StartBros();
	}
	
	void Item(){
		
	}
	
	void Draw(Graphics g){
		g.drawImage(SnowBrosComponent.bros_normal_l,x,y,null);
//		g.setColor(Color.WHITE);
//		g.fillOval(x-25, y-50, 50, 50);
//		g.fillOval(x-25, y, 50, 50);
//		g.setColor(Color.BLACK);
//		g.fillOval(x-15, y-30, 10, 10);
//		g.fillOval(x+5, y-30, 10, 10);
//		g.setColor(Color.RED);
//		g.fillArc(x-8, y-22, 15, 15, 0, 360);
	}
}
