import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


class SnowBrosComponent extends JComponent{
	public static int MAX_ENEMY = 5;
	public static int MAX_ITEM = 10;
	public static int MAX_MISSILE = 10;
	
	private Timer t;
	private Bros bros;
	private Enemy[] enemy;
	private Item[] myItem;
	private Missile[] myShot;
	
	SnowBrosComponent(){
		t = new Timer(30, new TimerHandler());
		t.start();
		bros = new Bros();
		enemy = new Enemy[MAX_ENEMY];
		for(Enemy e : enemy)
			e = new Enemy();
		myItem = new Item[MAX_ITEM];
		for(Item i : myItem)
			i = new Item();
		myShot = new Missile[MAX_MISSILE];
		for(Missile m : myShot)
			m = new Missile();
	}
	
	class TimerHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			
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
		bros.Draw(g);
//		for(Missile m: myShot)
//			m.Draw(g);
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
