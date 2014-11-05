import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


class SnowBrosComponent extends JComponent{
	
	private Timer t;
	private Bros bros;
	private Enemy[] enemy;
	private Item[] myItem;
	private Missile[] myShot;
	
	SnowBrosComponent(){
		t = new Timer(30, new TimerHandler());
		t.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
	}
}

public class SnowBros {
	public static int FRAME_W = 800;
	public static int FRAME_H = 600;
	
	public static void main(String[] args) {
		JFrame f = new JFrame("Snow Bros");
		f.setSize(FRAME_W + 8, FRAME_H + 34);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SnowBrosComponent sc = new SnowBrosComponent();
		f.add(sc);
		f.setVisible(true);
	}
}
