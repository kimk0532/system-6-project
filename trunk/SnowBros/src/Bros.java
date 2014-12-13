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
	public static int JUMPDOWN_R = 14;
	public static int JUMPDOWN_L = 15;
	
	public static int RIGHT = 1;
	public static int LEFT = -1;
	public int x, y,state, RL, jumpState, motion, imagecount, image;
	private int startY;
	private Rectangle bb;
	int xi,yi; //speed ¾ÆÀÌÅÛ
	
	Bros(){
		motion = 0;
		state = B_ST_DEATH;
		x = 0;
		y = 0;
		bb = new Rectangle(x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT);
		xi = 7;
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
		if(state == B_ST_JUMPUP || jumpState == 1)
			;
		else
			motion = MOVE_R;
		x += xi;
		RL = RIGHT;
		bb.x = x - B_WIDTH/2;
		bb.y = y - B_HEIGHT/2;
	}
	
	void MoveLeft(){
		if(state == B_ST_JUMPUP || jumpState == 1)
			;
		else
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
		y += yi;
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
		if(motion == BIRTH){
			imagecount++;
			image = imagecount / 5;
			if(image == SnowBrosComponent.bros_birth.length){
				image = 0;
				imagecount = 0;
				motion = 0;
			}
			g.drawImage(SnowBrosComponent.bros_birth[image], x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT,null);
		}
		else if(motion == BLAST){
			imagecount++;
			image = imagecount / 5;
			if(image == SnowBrosComponent.bros_blast.length){
				image = 0;
				imagecount = 0;
			}
			g.drawImage(SnowBrosComponent.bros_blast[image], x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT,null);
		}
		else if(motion == MOVE_R){
			imagecount++;
			image = imagecount / 4;
			if(image >= SnowBrosComponent.bros_move_r.length){
				image = 0;
				imagecount = 0;
			}
			g.drawImage(SnowBrosComponent.bros_move_r[image], x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT,null);
		}
		else if(motion == MOVE_L){
			imagecount++;
			image = imagecount / 4;
			if(image >= SnowBrosComponent.bros_move_l.length){
				image = 0;
				imagecount = 0;
			}
			g.drawImage(SnowBrosComponent.bros_move_l[image], x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT,null);
		}
		else if(motion == SMOVE_R){
			imagecount++;
			image = imagecount / 3;
			if(image >= SnowBrosComponent.bros_smove_r.length){
				image = 0;
				imagecount = 0;
			}
			g.drawImage(SnowBrosComponent.bros_smove_r[image], x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT,null);
		}
		else if(motion == SMOVE_L){
			imagecount++;
			image = imagecount / 3;
			if(image >= SnowBrosComponent.bros_smove_l.length){
				image = 0;
				imagecount = 0;
			}
			g.drawImage(SnowBrosComponent.bros_smove_l[image], x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT,null);
		}
		else if(motion == JUMP_R){
			if(state == B_ST_ALIVE)
				g.drawImage(SnowBrosComponent.bros_jump_r[5], x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT,null);
			else
				g.drawImage(SnowBrosComponent.bros_jump_r[(startY-y)/20], x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT,null);
		}
		else if(motion == JUMP_L){
			if(state == B_ST_ALIVE)
				g.drawImage(SnowBrosComponent.bros_jump_l[5], x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT,null);
			else
				g.drawImage(SnowBrosComponent.bros_jump_l[(startY-y)/20], x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT,null);
		}
		else if(motion == JUMPDOWN_R){
			g.drawImage(SnowBrosComponent.bros_jump_r[5], x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT,null);
		}
		else if(motion == JUMPDOWN_L){
			g.drawImage(SnowBrosComponent.bros_jump_l[5], x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT,null);
		}
		else if(motion == SHOT_R){
			imagecount++;
			image = imagecount / 3;
			if(image >= SnowBrosComponent.bros_shot_r.length){
				image = 0;
				imagecount = 0;
				motion = 0;
			}
			g.drawImage(SnowBrosComponent.bros_shot_r[image], x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT,null);
		}
		else if(motion == SHOT_L){
			imagecount++;
			image = imagecount / 3;
			if(image >= SnowBrosComponent.bros_shot_l.length){
				image = 0;
				imagecount = 0;
				motion = 0;
			}
			g.drawImage(SnowBrosComponent.bros_shot_l[image], x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT,null);
		}
		else if(motion == ROLL_R){
			imagecount++;
			image = imagecount / 3;
			if(image >= SnowBrosComponent.bros_roll_r.length){
				image = 0;
				imagecount = 0;
				motion = 0;
			}
			g.drawImage(SnowBrosComponent.bros_roll_r[image], x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT,null);
		}
		else if(motion == ROLL_L){
			imagecount++;
			image = imagecount / 3;
			if(image >= SnowBrosComponent.bros_roll_l.length){
				image = 0;
				imagecount = 0;
				motion = 0;
			}
			g.drawImage(SnowBrosComponent.bros_roll_l[image], x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT,null);
		}
		else{
			if(RL == LEFT)
				g.drawImage(SnowBrosComponent.bros_normal_l, x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT, null);
			else
				g.drawImage(SnowBrosComponent.bros_normal_r, x - B_WIDTH/2, y - B_HEIGHT/2, B_WIDTH, B_HEIGHT, null);
		}
		
	}
}
