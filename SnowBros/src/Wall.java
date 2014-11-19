import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

class Wall {
	public static int W_ST_ALIVE = 1; // ALIVE ����
	public static int W_ST_BLAST = 2; // ���� ����
	public static int W_WIDTH = 40; // ���� ����
	public static int W_HEIGHT = 30; // ���� ����
	public static int COLS = 20;
	public static int ROWS = 20;
	
	private int state; // ���� ����
	private int x, y; // ��ǥ ����
	private int count; // ������ ���� count ����
	
	private Rectangle bb; // Bounding Box ����
	
	Wall(){ // ������
		state = W_ST_ALIVE; // �ʱ���¸� ALIVE�� ����
		x = ( SnowBros.FRAME_W / 2 ) + ( W_WIDTH * 5 ); // �ʱ� x��ǥ�� ����(���)
		y = SnowBros.FRAME_H - ( W_HEIGHT * 10 ); // �ʱ� y��ǥ�� ȭ�� �Ʒ��ʿ� ������ ���
		bb = new Rectangle(
				x - W_WIDTH/2, y - W_HEIGHT/2,
				W_WIDTH, W_HEIGHT); // Bounding Box�� �ʱ�ȭ
	}
	
	int getState()	{ return state;	} // ���¸� �˷��ִ� �޼���
	int getX()		{ return x;		} // x��ǥ�� �˷��ִ� �޼���
	int getY()		{ return y;		} // y��ǥ�� �˷��ִ� �޼���
	Rectangle getBBox() {	return bb;	} // Bounding Box�� �˷��ִ� �޼���
	
	
	void init_Wall(){ //
		state = W_ST_ALIVE; // �ʱ���¸� ALIVE�� ����
		x = ( SnowBros.FRAME_W / 2 ) + ( W_WIDTH * 6 ); // �ʱ� x��ǥ�� ����(���)
		y = SnowBros.FRAME_H - ( W_HEIGHT * 2 ); // �ʱ� y��ǥ�� ȭ�� �Ʒ��ʿ� ������ ���
		bb = new Rectangle(
				x - W_WIDTH/2, y - W_HEIGHT/2,
				W_WIDTH, W_HEIGHT); // Bounding Box�� �ʱ�ȭ
	}
	
	void setWallPosition(int x, int y){
		x = COLS * x;
		y = ROWS * y;
	}
	
	void draw(Graphics g) { // ȭ���� �׸��� �޼���
		g.setColor(Color.darkGray);
		g.fillRect(x, y, W_WIDTH, W_HEIGHT);
	}
}
