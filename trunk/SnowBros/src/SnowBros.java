import java.awt.*;
import java.awt.event.*;

import javax.swing.*;



class SnowBrosComponent extends JComponent{
	public static int MAX_ENEMY = 5;
	public static int MAX_ITEM = 10;
	public static int MAX_MISSILE = 10;
	public static int STAGE = 3;
	public static int STAGE1 = 0;
	public static int STAGE2 = 1;
	public static int STAGE3 = 2;
	public static int X = SnowBros.FRAME_W;
	public static int Y = SnowBros.FRAME_H;
	
	private Timer t;
	private Bros bros;
	private Enemy[] enemy;
	private Item[] myItem;
	private Missile[] myShot;
	public int[][][] map;
	int shotDelay,brosright,brosleft,brosdown;
	
	SnowBrosComponent(){
		t = new Timer(30, new TimerHandler());
		t.start();
		this.addKeyListener(new KeyHandler());
		this.setFocusable(true);
		bros = new Bros();
		enemy = new Enemy[MAX_ENEMY];
		for(int i = 0; i < MAX_ENEMY; i++)
			enemy[i] = new Enemy();     
		myItem = new Item[MAX_ITEM];
		for(int i = 0; i < MAX_ITEM; i++)
			myItem[i] = new Item();
		myShot = new Missile[MAX_MISSILE];
		for(int i = 0; i < MAX_MISSILE; i++)
			myShot[i] = new Missile();
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
				if(i < 10 || i > X - 10)
					map[STAGE1][i][j] = 2;
				if(j <=Y-1 && j > Y-11)
					map[STAGE1][i][j] = 1;
				
			}
		}
	}
	
	class TimerHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(bros.state == Bros.B_ST_JUMPUP)
				bros.JumpUp();
			else if(bros.state == Bros.B_ST_ALIVE){
				if(map[STAGE1][bros.x][bros.y+75] == 1){
					bros.jumpState = 0;
				}
				else
					bros.JumpDown();
			}
			for(Missile m : myShot){
				if(m.state == Missile.M_ST_ALIVE){
					if(bros.RL == Bros.RIGHT)
						m.moveL();
					else if(bros.RL == Bros.LEFT)
						m.moveR();
				}
			}
			if(shotDelay > 0)
				shotDelay -= 1;
			for(int i = 0; i < MAX_ENEMY; i++){
				if(enemy[i].state == Enemy.E_ST_DEATH)
					enemy[i].birth(i);
				else
					enemy[i].move();
			}
			if(brosright == 1){
				if(map[STAGE1][bros.x+25][bros.y] == 2)
					;
				else
					bros.MoveRight();
			}
			else if(brosleft == 1){
				if(map[STAGE1][bros.x-25][bros.y] == 2)
					;
				else
					bros.MoveLeft();
			}
			repaint();
		}

	}

	class KeyHandler extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			int code = e.getKeyCode();
			if(code == KeyEvent.VK_RIGHT)
				brosright = 0;
			else if (code == KeyEvent.VK_LEFT)
				brosleft = 0;
			else if (code == KeyEvent.VK_DOWN)
				brosdown = 0;
		}
		@Override
		public void keyPressed(KeyEvent e) {
			int code = e.getKeyCode();
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
				for(int i = 0; i < MAX_MISSILE; i++){
					if(shotDelay == 0){
						if(myShot[i].state == Missile.M_ST_DEATH){
							myShot[i].shot(bros.getX(), bros.getY());
							shotDelay = 10;
							break;
						}
					}
				}
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, SnowBros.FRAME_W, SnowBros.FRAME_H);
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
		bros.Draw(g);
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
