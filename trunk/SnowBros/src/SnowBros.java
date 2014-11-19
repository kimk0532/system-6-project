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
	private int[][][] map;
	
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
				if(j == 80 )
					map[STAGE1][i][j] = 1;
				else
					map[STAGE1][i][j] = 0;
			}
		}
	}
	
	class TimerHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(bros.state == Bros.B_ST_JUMPUP)
				bros.Jumpup();
			else if(bros.state == Bros.B_ST_JUMPDOWN)
				bros.Jumpdown();
			for(Missile m : myShot){
				if(m.state == Missile.M_ST_ALIVE){
					if(bros.RL == Bros.RIGHT)
						m.moveL();
					else if(bros.RL == Bros.LEFT)
						m.moveR();
				}
			}
			repaint();
		}

	}

	class KeyHandler extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			int code = e.getKeyCode();
			if(code == KeyEvent.VK_RIGHT){
				bros.MoveRight();
			}
			else if(code == KeyEvent.VK_LEFT){
				bros.MoveLeft();
			}
			if(code == KeyEvent.VK_CONTROL){
				bros.Jump();
			}
			else if(code == KeyEvent.VK_SPACE){
				for(int i = 0; i < MAX_MISSILE; i++){
					if(myShot[i].state == Missile.M_ST_DEATH){
						myShot[i].shot(bros.getX(), bros.getY());
						break;
					}
				}
			}
		}
	}

	
	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, SnowBros.FRAME_W, SnowBros.FRAME_H);
		g.setColor(Color.WHITE);
		for(int i = 0; i < X; i++){
			for(int j = 0; j < Y; j++){
				if(map[STAGE1][i][j] == 1)
					g.fillRect(i*10, j*10, 30, 30);
			}
		}
		bros.Draw(g);
		g.setColor(Color.WHITE);
		for(Missile m : myShot)
			m.Draw(g);
		for(Enemy e : enemy)
			e.Draw(g);
	}
}

public class SnowBros {
	public static int FRAME_W = 1400;
	public static int FRAME_H = 900;
	
	public static void main(String[] args) {
		JFrame f = new JFrame("Snow Bros");
		f.setSize(FRAME_W + 8, FRAME_H + 34);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SnowBrosComponent sc = new SnowBrosComponent();
		f.add(sc);
		f.setVisible(true);
	}
}
