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

	public static void main(String[] args) {

	}
}
