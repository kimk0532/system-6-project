import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
	
	private int score; //점수
	private int life; //라이프
	private int count; //애니메이션 카운터
	private Image background;
	private Image bros_normal;
	private Image bros_jump[];
	private Image bros_mover[];
	private Image bros_movel[];
	private Image bros_shot[];
	SnowBrosComponent(){
		
		
		try {
			background = ImageIO.read(new File("Image/background.jpg"));
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
				if((i < 10 || i > X - 20) && j < Y - 100)
					map[STAGE1][i][j] = 2;
				else if((i < 10 || i > X - 20) && j >= Y - 100)
					map[STAGE1][i][j] = 3;
				if(j < Y && j >= Y - 10)
					map[STAGE1][i][j] = 1;
			}
		}
		STATE = TITLE;
		count = 0;
		
	}
	
	class TimerHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(bros.state == Bros.B_ST_JUMPUP)
				bros.JumpUp();
			else if(bros.state == Bros.B_ST_ALIVE){
				if(map[STAGE1][bros.x][bros.y+50] == 1){
					bros.jumpState = 0;
				}
				else
					bros.JumpDown();
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
				if(enemy[i].state == Enemy.E_ST_DEATH)
					enemy[i].birth(i);
				else if(enemy[i].state == Enemy.E_ST_ALIVE){
					enemy[i].Move();
					if(bros.getBBox().intersects(enemy[i].getBBox())){
						enemy[i].Move();
						bros.Blast();
						life--; //LIFE감소
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
						if(map[STAGE1][enemy[i].x+25][enemy[i].y] == 2){
							enemy[i].Bounce();
							rolldir[i] = SHOT_LEFT;
						}
						else if(map[STAGE1][enemy[i].x+25][enemy[i].y] == 3)
							enemy[i].blast();
					}
					else if(rolldir[i] == SHOT_LEFT){
						if(map[STAGE1][enemy[i].x-25][enemy[i].y] == 2){
							enemy[i].Bounce();
							rolldir[i] = SHOT_RIGHT;
						}
						else if(map[STAGE1][enemy[i].x-25][enemy[i].y] == 3)
							enemy[i].blast();
					}
					if(map[STAGE1][enemy[i].x][enemy[i].y+50] == 0){
						enemy[i].Down();
					}
					for(int j = 0; j < MAX_ENEMY; j++){
						if(j != i && enemy[i].getBBox().intersects(enemy[j].getBBox())){
							enemy[j].blast();
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
						bros.xi = 20;
						bros.yi = 20;
					}
				}
				if(myItem[i].state==Item.STRONG){ //damage 2씩 증가
					if(bros.getBBox().intersects(myItem[i].getBBox())){
						myItem[i].Blast();
						for(int j = 0; j < MAX_ENEMY; j++){
							enemy[j].da = 2;
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
				if(map[STAGE1][bros.x+25][bros.y] >= 2)
					;
				else
					bros.MoveRight();
			}
			else if(brosleft == 1){
				if(map[STAGE1][bros.x-25][bros.y] >= 2)
					;
				else
					bros.MoveLeft();
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
				}
			}
			else if(STATE == STAGE1){ //STATE1일때
				if(code == KeyEvent.VK_RIGHT)
					brosright = 0;
				else if (code == KeyEvent.VK_LEFT)
					brosleft = 0;
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
			if(STATE == STAGE1){ //STAGE1일때
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
							if(rolldir[j] == SHOT_RIGHT)
								enemy[j].dx = 10;
							else if(rolldir[j] == SHOT_LEFT)
								enemy[j].dx = -10;
							break;
						}
					}
					for(int j = 0; j < MAX_ENEMY; j++){
						if(noshot != 1){
							for(int i = 0; i < MAX_MISSILE; i++){
								if(shotDelay == 0){
									if(myShot[i].state == Missile.M_ST_DEATH){
										myShot[i].Shot(bros.getX(), bros.getY());
										if(bros.RL == Bros.RIGHT)
											myShotd[i] = SHOT_RIGHT;
										else if(bros.RL == Bros.LEFT)
											myShotd[i] = SHOT_LEFT;
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
			g.drawImage(background, 0, 0, X, Y, null);
		
//		g.setColor(Color.BLACK);
//		g.fillRect(0, 0, X, Y);
		//상태별 문자 출력
		if(STATE == TITLE){
			int tx = X/2 - 300;
			int ty = Y/2 - 50;
			int i;
			
			Font font = new Font(Font.SANS_SERIF,Font.BOLD,100);
			g.setFont(font);
			g.setColor(Color.white);
			for(i=-5; i<=5; i++){
				g.drawString("SnowBros",tx-i, ty-i);
			}
			g.setColor(Color.red);
			g.drawString("SnowBros", tx, ty);
			
			if(count<10){
				font = new Font(Font.SANS_SERIF, Font.BOLD, 36);
				g.setFont(font);
				g.setColor(Color.WHITE);
				g.drawString("PRESS SPACE KEY", X/2-150, Y/4*3);
			}
		}
		else if (STATE == STAGE1){
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
		else if(STATE == ENDING){
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
