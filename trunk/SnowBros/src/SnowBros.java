import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ImageConsumer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

class SnowBrosComponent extends JComponent{
	public static int MAX_ENEMY = 5;
	public static int MAX_ITEM = 5;
	public static int MAX_MISSILE = 10;
	public static int STAGE = 3;
	public static int STAGE1 = 0;
	public static int STAGE2 = 1;
	public static int STAGE3 = 2;
	public static int TITLE = 4; //TITLE
	public static int ENDING = 5; //ENDING
	public static int SHOT_RIGHT = 1;
	public static int SHOT_LEFT = -1;
	public static int X = SnowBros.FRAME_W;
	public static int Y = SnowBros.FRAME_H;
	
	public static int STATE; //게임 상태
	
	private Timer t;
	private Bros bros;
	private Enemy[] enemy;
	private Item[] myItem;
	private Missile[] myShot;
	private int[] myShotd;
	public int[][][] map;
	public int[] warm;
	int shotDelay, brosright, brosleft, brosdown;
	public int[] itemTime; //아이템시간
	private boolean ball[];
	private int noshot, k;
	private int rolldir[];
	private int enemystate;
	public static int big;
	private int score; //점수
	private int life; //라이프
	private int count; //애니메이션 카운터
	private Image background_title;
	private Image background_stage;
	private Image background_ending;
	public static Image bros_normal_r;
	public static Image bros_normal_l;
	public static Image bros_birth[];
	public static Image bros_blast[];
	public static Image bros_jump_r[];
	public static Image bros_jump_l[];
	public static Image bros_move_r[];
	public static Image bros_move_l[];
	public static Image bros_smove_r[];
	public static Image bros_smove_l[];
	public static Image bros_shot_r[];
	public static Image bros_shot_l[];
	public static Image bros_roll_r[];
	public static Image bros_roll_l[];
	public static Image bros_next[];
	public static Image enemy_move_r[];
	public static Image enemy_move_l[];
	public static Image enemy_damage[];
	public static Image item_red;
	public static Image item_blue;
	public static Image item_green;
	public static Image item_yellow;
	public static Image missile_right;
	public static Image missileb_right;
	public static Image missile_left;
	public static Image missileb_left;
	SnowBrosComponent(){
		enemystate = 0;
		big = 0;
		bros_birth = new Image[9];
		bros_blast = new Image[7];
		bros_jump_r = new Image[6];
		bros_jump_l = new Image[6];
		bros_move_r = new Image[3];
		bros_move_l = new Image[3];
		bros_smove_r = new Image[3];
		bros_smove_l = new Image[3];
		bros_shot_r = new Image[3];
		bros_shot_l = new Image[3];
		bros_roll_r = new Image[3];
		bros_roll_l = new Image[3];
		bros_next = new Image[2];
		enemy_move_r = new Image[2];
		enemy_move_l = new Image[2];
		enemy_damage = new Image[6];
		try {
			background_title = ImageIO.read(new File("Image/background_title.jpg"));
			background_stage = ImageIO.read(new File("Image/background_stage.jpg"));
			background_ending = ImageIO.read(new File("Image/background_ending.png"));
			bros_normal_r = ImageIO.read(new File("Image/Bros/bros_r.png"));
			bros_normal_l = ImageIO.read(new File("Image/Bros/bros_l.png"));
			
			for(int i = 0; i < 9; i++){
				bros_birth[i] = ImageIO.read(new File("Image/Bros/BIRTH/bros_birth"+(i+1)+".png"));
				if(i < 7)
					bros_blast[i] = ImageIO.read(new File("Image/Bros/BLAST/bros_blast"+(i+1)+".png"));
				if(i < 6){
					bros_jump_r[i] = ImageIO.read(new File("Image/Bros/JUMP_R/bros_jump_r"+(i+1)+".png"));
					bros_jump_l[i] = ImageIO.read(new File("Image/Bros/JUMP_L/bros_jump_l"+(i+1)+".png"));
					enemy_damage[i] = ImageIO.read(new File("Image/Enemy/DAMAGE/enemy_damage"+(i+1)+".png"));
				}
				if(i < 3){
					bros_move_r[i] = ImageIO.read(new File("Image/Bros/MOVE_R/bros_move_r"+(i+1)+".png"));
					bros_move_l[i] = ImageIO.read(new File("Image/Bros/MOVE_l/bros_move_l"+(i+1)+".png"));
					bros_smove_r[i] = ImageIO.read(new File("Image/Bros/SMOVE_R/bros_smove_r"+(i+1)+".png"));
					bros_smove_l[i] = ImageIO.read(new File("Image/Bros/SMOVE_L/bros_smove_l"+(i+1)+".png"));
					bros_shot_r[i] = ImageIO.read(new File("Image/Bros/SHOT_R/bros_shot_r"+(i+1)+".png"));
					bros_shot_l[i] = ImageIO.read(new File("Image/Bros/SHOT_L/bros_shot_l"+(i+1)+".png"));
					bros_roll_r[i] = ImageIO.read(new File("Image/Bros/ROLL_R/bros_roll_r"+(i+1)+".png"));
					bros_roll_l[i] = ImageIO.read(new File("Image/Bros/ROLL_L/bros_roll_l"+(i+1)+".png"));
				}
				if(i < 2){
					bros_next[i] = ImageIO.read(new File("Image/Bros/NEXT/bros_next"+(i+1)+".png"));
					enemy_move_r[i] = ImageIO.read(new File("Image/Enemy/MOVE_R/enemy_move_r"+(i+1)+".png"));
					enemy_move_l[i] = ImageIO.read(new File("Image/Enemy/MOVE_L/enemy_move_l"+(i+1)+".png"));
				}
			}
			item_red = ImageIO.read(new File("Image/Item/item3.png"));
			item_blue = ImageIO.read(new File("Image/Item/item4.png"));
			item_green = ImageIO.read(new File("Image/Item/item2.png"));
			item_yellow = ImageIO.read(new File("Image/Item/item1.png"));
			missile_right = ImageIO.read(new File("Image/Missile/missile_r1.png"));
			missileb_right = ImageIO.read(new File("Image/Missile/missile_r2.png"));
			missile_left = ImageIO.read(new File("Image/Missile/missile_l1.png"));
			missileb_left = ImageIO.read(new File("Image/Missile/missile_l2.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		t = new Timer(30, new TimerHandler());
		t.start();
		this.addKeyListener(new KeyHandler());
		this.setFocusable(true);
		bros = new Bros();
		enemy = new Enemy[MAX_ENEMY];
		ball = new boolean[MAX_ENEMY];
		rolldir = new int[MAX_ENEMY];
		for(int i = 0; i < MAX_ENEMY; i++){
			enemy[i] = new Enemy();
			ball[i] = false;
			rolldir[i] = 0;
		}
		myItem = new Item[MAX_ITEM];
		for(int i = 0; i < MAX_ITEM; i++)
			myItem[i] = new Item();
		myShot = new Missile[MAX_MISSILE];
		myShotd = new int[MAX_MISSILE];
		for(int i = 0; i < MAX_MISSILE; i++){
			myShot[i] = new Missile();
			myShotd[i] = 1;
		}
		warm = new int[MAX_ENEMY];
		itemTime = new int[MAX_ITEM];
		map = new int[STAGE][X][Y];
		for(int i = 0; i < X; i++){
			for(int j = 0; j < Y; j++){
				
				if(j >= 590 && j < 600 && i <X -100)
					map[STAGE1][i][j] = 1;
				else if(j >= 490 && j < 500 && i > 100)
					map[STAGE1][i][j] = 1;
				else if(j >= 390 && j < 400 && i < X - 100)
					map[STAGE1][i][j] = 1;
				else if(j >= 290 && j < 300 && i > 100)
					map[STAGE1][i][j] = 1;
				else if(j >= 190 && j < 200 && i < X - 100)
					map[STAGE1][i][j] = 1;
				else
					map[STAGE1][i][j] = 0;
				if((i < 20 || i > X - 30) && j < Y - 100)
					map[STAGE1][i][j] = 2;
				else if((i < 20 || i > X - 30) && j >= Y - 100)
					map[STAGE1][i][j] = 3;
				if(j < Y && j >= Y - 10)
					map[STAGE1][i][j] = 1;
			}
		}
		//STAGE2
		for(int i = 0; i < X; i++){
			for(int j = 0; j < Y; j++){
				if(j >= 590 && j < 600 && i > X -300)
					map[STAGE2][i][j] = 1;
				else if(j >= 590 && j < 600 && i < 300)
					map[STAGE2][i][j] = 1;
				else if(j >= 490 && j < 500 && i > 100 && i < X - 100)
					map[STAGE2][i][j] = 1;
				else if(j >= 390 && j < 400 && i > X - 300)
					map[STAGE2][i][j] = 1;
				else if(j >= 390 && j < 400 && i < 300)
					map[STAGE2][i][j] = 1;
				else if(j >= 290 && j < 300 && i > 100 && i < X - 100)
					map[STAGE2][i][j] = 1;
				else
					map[STAGE2][i][j] = 0;
				if((i < 20 || i > X - 20) && j < Y - 100) //20으로수정
				map[STAGE2][i][j] = 2;
				else if((i < 20 || i > X - 20) && j >= Y - 100) //20으로수정
					map[STAGE2][i][j] = 3;
				if(j < Y && j >= Y - 10)
					map[STAGE2][i][j] = 1;
			}
		}
		//STAGE3
		for(int i = 0; i < X; i++){
			for(int j = 0; j < Y; j++){
				if(j >= 590 && j < 600 && i > 250 && i < X - 130)
					map[STAGE3][i][j] = 1;
				else if(j >= 490 && j < 500 && i > 130 && i < X - 250)
					map[STAGE3][i][j] = 1;
				else if(j >= 390 && j < 400 && i > 250 && i < X - 130)
					map[STAGE3][i][j] = 1;
				else if(j >= 290 && j < 300 && i > 130 && i < X - 250)
					map[STAGE3][i][j] = 1;
				else if(j >= 190 && j < 200 && i > 250 && i < X - 130)
					map[STAGE3][i][j] = 1;
				/////////
				else if(i < X - 130 && i > X - 153 && j < 190 && j >= 100) //속도아이템먹으면 20씩증가하므로 언덕 X좌표 20이상?
					map[STAGE3][i][j] = 2;
				else if(i > 130 && i < 153 && j < 290 && j >= 200)
					map[STAGE3][i][j] = 2;
				else if(i < X - 130 && i > X - 153 && j < 390 && j >= 300)
					map[STAGE3][i][j] = 2;
				else if(i > 130 && i < 153 && j < 490 && j > 400)
					map[STAGE3][i][j] = 2;
				else if(i < X - 130 && i > X - 153 && j < 590 && j >= 500)
					map[STAGE3][i][j] = 2;
				/////////
				else
					map[STAGE3][i][j] = 0;
				if((i < 20 || i > X - 20) && j < Y - 100)
					map[STAGE3][i][j] = 2;
				else if((i < 20 || i > X - 20) && j >= Y - 100)
					map[STAGE3][i][j] = 3;
				if(j < Y && j >= Y - 10)
					map[STAGE3][i][j] = 1;
			}
		}
		STATE = TITLE;
		count = 0;
		
	}
	
	class TimerHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(bros.state == Bros.B_ST_JUMPUP){
				bros.JumpUp();
				if(bros.RL == Bros.RIGHT)
					bros.motion = Bros.JUMP_R;
				else if(bros.RL == Bros.LEFT)
					bros.motion = Bros.JUMP_L;
			}
			else if(bros.state == Bros.B_ST_ALIVE){
				if(STATE == STAGE1){
					if(map[STAGE1][bros.x][bros.y+50] == 1){
						if(bros.jumpState == 1)
							bros.motion = 0;
						bros.jumpState = 0;
					}
					else{
						bros.jumpState = 1;
						bros.JumpDown();
						if(bros.RL == Bros.RIGHT)
							bros.motion = Bros.JUMPDOWN_R;
						else if(bros.RL == Bros.LEFT)
							bros.motion = Bros.JUMPDOWN_L;
					}
				}
				if(STATE == STAGE2){
					if(map[STAGE2][bros.x][bros.y+50] == 1){
						if(bros.jumpState == 1)
							bros.motion = 0;
						bros.jumpState = 0;
					}
					else{
						bros.jumpState = 1;
						bros.JumpDown();
						if(bros.RL == Bros.RIGHT)
							bros.motion = Bros.JUMPDOWN_R;
						else if(bros.RL == Bros.LEFT)
							bros.motion = Bros.JUMPDOWN_L;
					}
				}
				if(STATE == STAGE3){
					if(map[STAGE3][bros.x][bros.y+50] == 1 || map[STAGE3][bros.x][bros.y+50] == 2){
						if(bros.jumpState == 1)
							bros.motion = 0;
						bros.jumpState = 0;
					}
					else{
						bros.jumpState = 1;
						bros.JumpDown();
						if(bros.RL == Bros.RIGHT)
							bros.motion = Bros.JUMPDOWN_R;
						else if(bros.RL == Bros.LEFT)
							bros.motion = Bros.JUMPDOWN_L;
					}
				}
			}
			for(int i = 0; i < MAX_MISSILE; i++){
				if(myShot[i].state == Missile.M_ST_ALIVE){
					if(myShotd[i] == SHOT_RIGHT){
						myShot[i].MoveR();
					}
					else if(myShotd[i] == SHOT_LEFT)
						myShot[i].MoveL();
					for(int j = 0; j < MAX_ENEMY; j++){
						if(enemy[j].getBBox().intersects(myShot[i].getBBox()) && enemy[j].state != Enemy.E_ST_ROLL){
							enemy[j].state = Enemy.E_ST_DAMAGE;
							enemy[j].Damage();
							if(enemy[j].damage == 4) //damage가 4면 BALL상태
								enemy[j].state = Enemy.E_ST_BALL; 
							warm[j] = 100;
							myShot[i].Blast();
						}
					}
				}
			}
			if(shotDelay > 0)
				shotDelay -= 1;
			
			for(int i = 0; i < MAX_ENEMY; i++){
				if(enemy[i].state == Enemy.E_ST_DEATH){
					if(STATE == STAGE1)
						enemy[i].birth1(i);
					else if(STATE == STAGE2)
						enemy[i].birth2(i);
					else if(STATE == STAGE3)
						enemy[i].birth3(i);
				}
				else if(enemy[i].state == Enemy.E_ST_ALIVE){
					enemy[i].Move();
					if(bros.getBBox().intersects(enemy[i].getBBox())){
						enemy[i].Move();
						bros.Blast();
						bros.xi = 7;
						bros.yi = 10;
						life--; //LIFE감소
						for(int j = 0; j < MAX_ENEMY; j++){
							enemy[j].da = 1;
						}
						for(int j = 0; j < MAX_MISSILE; j++){
							myShot[j].di = 75; 
						}
						if(life==0){
							STATE = ENDING;
						}
					}
				}
				else if(enemy[i].state == Enemy.E_ST_BALL){
					if(bros.getBBox().intersects(enemy[i].getBBox()) && ball[i] == false){
						ball[i] = true;
						k = i;
						noshot = 1;
//						score += 50; //점수 증가
//						myItem[i].birth();//item생성
//						itemTime[i] = 150; //일정시간이 지나면 item 사라짐.
					}
					else if(!bros.getBBox().intersects(enemy[i].getBBox())){
						ball[i] = false;
					}
				}
				else if(enemy[i].state == Enemy.E_ST_ROLL ){
					enemy[i].Roll();
					if(rolldir[i] == SHOT_RIGHT){
						if(STATE == STAGE1){
							if(map[STAGE1][enemy[i].x+25][enemy[i].y] == 2){
								enemy[i].Bounce();
								rolldir[i] = SHOT_LEFT;
							}
							else if(map[STAGE1][enemy[i].x+25][enemy[i].y] == 3){
								enemy[i].blast();
								enemystate -= 1; //enemystate 1씩감소
								score += 50;
								myItem[i].birth();
								itemTime[i] = 150;
							}
						}
						else if(STATE == STAGE2){
							if(map[STAGE2][enemy[i].x+25][enemy[i].y] == 2){
								enemy[i].Bounce();
								rolldir[i] = SHOT_LEFT;
							}
							else if(map[STAGE2][enemy[i].x+25][enemy[i].y] == 3){
								enemy[i].blast();
								enemystate -= 1; //enemystate 1씩감소
								score += 50; 
								myItem[i].birth();
								itemTime[i] = 150; 
							}
						}
						else if(STATE == STAGE3){
							if(map[STAGE3][enemy[i].x+25][enemy[i].y] == 2){
								enemy[i].Bounce();
								rolldir[i] = SHOT_LEFT;
							}
							else if(map[STAGE3][enemy[i].x+25][enemy[i].y] == 3){
								enemy[i].blast();
								enemystate -= 1; //enemystate 1씩감소
								score += 50; 
								myItem[i].birth();
								itemTime[i] = 150; 
							}
						}
					}
					else if(rolldir[i] == SHOT_LEFT){
						if(STATE == STAGE1){
							if(map[STAGE1][enemy[i].x-25][enemy[i].y] == 2){
								enemy[i].Bounce();
								rolldir[i] = SHOT_RIGHT;
							}
							else if(map[STAGE1][enemy[i].x-25][enemy[i].y] == 3){
								enemy[i].blast();
								enemystate -= 1;
								score += 50; 
								myItem[i].birth();
								itemTime[i] = 150; 
							}
						}
						if(STATE == STAGE2){
							if(map[STAGE2][enemy[i].x-25][enemy[i].y] == 2){
								enemy[i].Bounce();
								rolldir[i] = SHOT_RIGHT;
							}
							else if(map[STAGE2][enemy[i].x-25][enemy[i].y] == 3){
								enemy[i].blast();
								enemystate -= 1;
								score += 50; 
								myItem[i].birth();
								itemTime[i] = 150; 
							}
						}
						if(STATE == STAGE3){
							if(map[STAGE3][enemy[i].x-25][enemy[i].y] == 2){
								enemy[i].Bounce();
								rolldir[i] = SHOT_RIGHT;
							}
							else if(map[STAGE3][enemy[i].x-25][enemy[i].y] == 3){
								enemy[i].blast();
								enemystate -= 1;
								score += 50; 
								myItem[i].birth();
								itemTime[i] = 150; 
							}
						}
					}
					if(map[STAGE1][enemy[i].x][enemy[i].y+50] == 0 && STATE == STAGE1){
						enemy[i].Down();
					}
					else if(map[STAGE2][enemy[i].x][enemy[i].y+50] == 0 && STATE == STAGE2){
						enemy[i].Down();
					}
					else if(map[STAGE3][enemy[i].x][enemy[i].y+50] == 0 && STATE == STAGE3){
						enemy[i].Down();
					}
					for(int j = 0; j < MAX_ENEMY; j++){
						if(j != i && enemy[i].getBBox().intersects(enemy[j].getBBox())){
							if(enemy[j].state == Enemy.E_ST_BALL){
								enemy[i].Bounce();
								rolldir[i] *= -1;
								enemy[j].state = Enemy.E_ST_ROLL;
								ball[j] = false;
								rolldir[j] = rolldir[i] * -1;
								enemy[j].dx = enemy[i].dx * -1;
								bros.motion = Bros.ROLL_R;
								bros.imagecount = 0;
							}
							else if (enemy[j].state == Enemy.E_ST_ALIVE || enemy[j].state == Enemy.E_ST_DAMAGE){
								enemy[j].blast();
								enemystate -= 1;
								score += 50; 
								myItem[j].birth();
								itemTime[j] = 150; 
							}
						}
					}
				}
				if(enemy[i].state == Enemy.E_ST_DAMAGE || enemy[i].state == Enemy.E_ST_BALL){
					if(warm[i] == 0){
						enemy[i].damage -= 1;
						if(enemy[i].damage < 4)//damage가 4보다 작으면 DAMAGE
							enemy[i].state = Enemy.E_ST_DAMAGE;
						if(enemy[i].damage == 0)
							enemy[i].state = Enemy.E_ST_ALIVE;
						warm[i] = 100;
						ball[i] = false;
					}
				}
				if(warm[i] > 0)
					warm[i] -= 1;
				if(!bros.getBBox().intersects(enemy[i].getBBox()) && k == i)
					noshot = 0;
			}
			for(int i = 0; i < MAX_ITEM; i++){ //itemtime 감소
				if(itemTime[i]> 0)
					itemTime[i] -= 1; 
			}
			
			//아이템
			for(int i = 0; i < MAX_ENEMY; i++){
				if(myItem[i].state==Item.LIFE){//LIFE 증가
					if(bros.getBBox().intersects(myItem[i].getBBox())){
						myItem[i].Blast();
						life += 1; 
					}
				}
				if(myItem[i].state==Item.SPEED){//이동속도증가 10단위로
					if(bros.getBBox().intersects(myItem[i].getBBox())){
						myItem[i].Blast();
						bros.xi = 15;
						bros.fast = 1;
					}
				}
				if(myItem[i].state==Item.STRONG){ //damage 2씩 증가
					if(bros.getBBox().intersects(myItem[i].getBBox())){
						myItem[i].Blast();
						for(int j = 0; j < MAX_ENEMY; j++){
							enemy[j].da = 2;
							big = 1;
						}
					}
				}
				if(myItem[i].state==Item.DISTANCE){ //장거리 공격
					if(bros.getBBox().intersects(myItem[i].getBBox())){
						myItem[i].Blast();
						for(int j = 0; j < MAX_MISSILE; j++){
							myShot[j].di = 100; 
						}
					}
				}
				if(myItem[i].state==Item.SCORE){ //SCORE 증가
					if(bros.getBBox().intersects(myItem[i].getBBox())){
						myItem[i].Blast();
						score += 30; 
					}
				}
			}
			for(int i = 0; i < MAX_ITEM; i++){ 
				if(itemTime[i] == 0){ //일정시간 지나면 item death
					myItem[i].state = Item.I_ST_DEATH;
				}
			}
			
			
			if(brosright == 1){
				if(STATE == STAGE1){
					if(map[STAGE1][bros.x+25][bros.y] >= 2)
						;
					else
						bros.MoveRight();
				}
				if(STATE == STAGE2){
					if(map[STAGE2][bros.x+25][bros.y] >= 2)
						;
					else
						bros.MoveRight();
				}
				if(STATE == STAGE3){
					if(map[STAGE3][bros.x+25][bros.y] >= 2)
						;
					else
						bros.MoveRight();
				}
			}
			else if(brosleft == 1){
				if(STATE == STAGE1){
					if(map[STAGE1][bros.x-25][bros.y] >= 2)
						;
					else
						bros.MoveLeft();
				}
				if(STATE == STAGE2){
					if(map[STAGE2][bros.x-25][bros.y] >= 2)
						;
					else
						bros.MoveLeft();
				}
				if(STATE == STAGE3){
					if(map[STAGE3][bros.x-25][bros.y] >= 2)
						;
					else
						bros.MoveLeft();
				}
			}			
			
			if(enemystate == 0){
				if(STATE == STAGE1){
					STATE = STAGE2;
					enemystate = MAX_ENEMY;
					bros.StartBros();
					for(int i = 0; i < MAX_ENEMY; i++){
						enemy[i].state = Enemy.E_ST_DEATH;
						enemy[i].damage = 0;
						ball[i] = false;
						rolldir[i] = 0;
					}
					for(int i = 0; i < MAX_ITEM; i++){
						myItem[i].state = Item.I_ST_DEATH;
					}
				}
				else if(STATE == STAGE2){
					STATE = STAGE3;
					enemystate = MAX_ENEMY;
					bros.StartBros();
					for(int i = 0; i < MAX_ENEMY; i++){
						enemy[i].state = Enemy.E_ST_DEATH;
						enemy[i].damage = 0;
						ball[i] = false;
						rolldir[i] = 0;
					}
					for(int i = 0; i < MAX_ITEM; i++){
						myItem[i].state = Item.I_ST_DEATH;
					}
				}
				else if(STATE == STAGE3)
					STATE = ENDING;
			}
			
			//카운터
			count = (count + 1)%20;
			repaint();
		}
	}

	class KeyHandler extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			int code = e.getKeyCode();
			if(STATE == TITLE){
				if(code == KeyEvent.VK_SPACE){ //게임 시작
					STATE = STAGE1;
					score = 0;
					life = 3;
					bros.StartBros();
					for(int i = 0; i < MAX_ENEMY; i++){
						enemy[i].state = Enemy.E_ST_DEATH;
						enemy[i].damage = 0;
						ball[i] = false;
						rolldir[i] = 0;
					}
					for(int i = 0; i < MAX_ITEM; i++){
						myItem[i].state = Item.I_ST_DEATH;
					}
					for(int j = 0; j < MAX_ENEMY; j++){
						enemy[j].da = 1;
					}
					for(int j = 0; j < MAX_MISSILE; j++){
						myShot[j].di = 75; 
					}
					brosright = 0;
					brosleft = 0;
					brosdown = 0;
				}
			}
			else if((STATE == STAGE1 || STATE == STAGE2 || STATE == STAGE3) && bros.blast == 0){ 
				if(code == KeyEvent.VK_RIGHT){
					if(bros.motion == Bros.MOVE_R)
						bros.motion = 0;
					brosright = 0;
				}
				else if (code == KeyEvent.VK_LEFT){
					if(bros.motion == Bros.MOVE_L)
						bros.motion = 0;
					brosleft = 0;
				}
				else if (code == KeyEvent.VK_DOWN)
					brosdown = 0;
			}
			else if (STATE == ENDING){
				if(code == KeyEvent.VK_ENTER){ //제목화면으로 이동
					STATE = TITLE;
				}
			}
			repaint();
		}
		@Override
		public void keyPressed(KeyEvent e) {
			int code = e.getKeyCode();
			if((STATE == STAGE1 || STATE == STAGE2 || STATE == STAGE3)  && bros.blast == 0){ //STAGE1일때
				if(code == KeyEvent.VK_RIGHT)
					brosright = 1;
				else if(code == KeyEvent.VK_LEFT)
					brosleft = 1;
				else if(code == KeyEvent.VK_DOWN)
					brosdown = 1;
				if(code == KeyEvent.VK_CONTROL){
					if(brosdown == 0){
						if(bros.jumpState == 0)
							bros.Jump();
					}
					else if(brosdown == 1){
						if(bros.y < SnowBros.FRAME_H-100)
							bros.JumpDown();
					}
				}
				else if(code == KeyEvent.VK_SPACE){
					for(int j = 0; j < MAX_ENEMY; j++){
						if(ball[j] == true){
							enemy[j].state = Enemy.E_ST_ROLL;
							ball[j] = false;
							rolldir[j] = bros.RL;
							if(rolldir[j] == SHOT_RIGHT){
								enemy[j].dx = 20;
								bros.motion = Bros.ROLL_R;
								bros.imagecount = 0;
							}
							else if(rolldir[j] == SHOT_LEFT){
								enemy[j].dx = -20;
								bros.motion = Bros.ROLL_L;
								bros.imagecount = 0;
							}
							break;
						}
					}
					for(int j = 0; j < MAX_ENEMY; j++){
						if(noshot != 1){
							for(int i = 0; i < MAX_MISSILE; i++){
								if(shotDelay == 0){
									if(myShot[i].state == Missile.M_ST_DEATH){
										myShot[i].Shot(bros.getX(), bros.getY());
										if(bros.RL == Bros.RIGHT){
											myShotd[i] = SHOT_RIGHT;
											bros.motion = Bros.SHOT_R;
											bros.imagecount = 0;
										}
										else if(bros.RL == Bros.LEFT){
											myShotd[i] = SHOT_LEFT;
											bros.motion = Bros.SHOT_L;
											bros.imagecount = 0;
										}
										shotDelay = 10;
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		//상태별 문자 출력
		if(STATE == TITLE){
			g.drawImage(background_title, 0, 0, X, Y, null);
			int tx = X/2 - 300;
			int ty = Y/2 - 50;
			int i;
			
			Font font = new Font(Font.SANS_SERIF,Font.BOLD,36);
			g.setFont(font);
			
			if(count<10){
				g.setColor(Color.WHITE);
				g.drawString("PRESS SPACE KEY", X/2-150, Y/8*7);
			}
		}
		else if (STATE == STAGE1){
			g.drawImage(background_stage, 0, 0, X, Y, null);
			g.setColor(Color.YELLOW);
			for(int i = 0; i < X; i++){
				for(int j = 0; j < Y; j++){
					if(map[STAGE1][i][j] == 1)
						g.fillRect(i, j, 1, 1);
				}
			}
			
			g.setColor(Color.WHITE);
			for(Missile m : myShot)
				m.Draw(g);
			for(Enemy e : enemy)
				e.Draw(g);
			for(Item i : myItem)
				i.Draw(g);
			bros.Draw(g);
			
			Font font = new Font(Font.MONOSPACED,Font.BOLD, 20);
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString("SCORE: " + score, 20, 30);
			g.drawString(" LIFE: " + life, 20, 60);
		}
		else if (STATE == STAGE2){
			g.drawImage(background_stage, 0, 0, X, Y, null);
			g.setColor(Color.YELLOW);
			for(int i = 0; i < X; i++){
				for(int j = 0; j < Y; j++){
					if(map[STAGE2][i][j] == 1)
						g.fillRect(i, j, 1, 1);
				}
			}
			
			g.setColor(Color.WHITE);
			for(Missile m : myShot)
				m.Draw(g);
			for(Enemy e : enemy)
				e.Draw(g);
			for(Item i : myItem)
				i.Draw(g);
			bros.Draw(g);
			
			Font font = new Font(Font.MONOSPACED,Font.BOLD, 20);
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString("SCORE: " + score, 20, 30);
			g.drawString(" LIFE: " + life, 20, 60);
		}
		
		else if (STATE == STAGE3){
			g.drawImage(background_stage, 0, 0, X, Y, null);
			g.setColor(Color.YELLOW);
			for(int i = 0; i < X; i++){
				for(int j = 0; j < Y; j++){
					if(map[STAGE3][i][j] == 1)
						g.fillRect(i, j, 1, 1);
				}
			}
			g.setColor(Color.YELLOW);
			for(int i = 0; i < X; i++){
				for(int j = 0; j < Y; j++){
					if(map[STAGE3][i][j] == 2)
						g.fillRect(i, j, 1, 1);
				}
			}

			g.setColor(Color.WHITE);
			for(Missile m : myShot)
				m.Draw(g);
			for(Enemy e : enemy)
				e.Draw(g);
			for(Item i : myItem)
				i.Draw(g);
			bros.Draw(g);
			
			Font font = new Font(Font.MONOSPACED,Font.BOLD, 20);
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString("SCORE: " + score, 20, 30);
			g.drawString(" LIFE: " + life, 20, 60);
		}
		else if(STATE == ENDING){
			g.drawImage(background_ending, 0, 0, X, Y, null);
			Font font = new Font(Font.SANS_SERIF, Font.BOLD, 36);
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString("YOUR SCORE IS "+score, X/2 - 150, Y/3);
			if(count <10){
				g.drawString("PRESS ENTER KEY", X/2-150, Y/3*2);
			}
		}
	}
}

public class SnowBros {
	public static int FRAME_W = 1000;
	public static int FRAME_H = 700;
	
	public static void main(String[] args) {
		JFrame f = new JFrame("Snow Bros");
		f.setSize(FRAME_W + 8, FRAME_H + 34);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SnowBrosComponent sc = new SnowBrosComponent();
		f.add(sc);
		f.setVisible(true);
	}
}
