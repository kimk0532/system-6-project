import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

class Wall {
	public static int W_ST_ALIVE = 1; // ALIVE 상태
	public static int W_ST_BLAST = 2; // 폭발 상태
	public static int W_WIDTH = 40; // 가로 길이
	public static int W_HEIGHT = 30; // 세로 길이
	public static int COLS = 20;
	public static int ROWS = 20;
	
	private int state; // 상태 변수
	private int x, y; // 좌표 변수
	private int count; // 폭발을 위한 count 변수
	
	private Rectangle bb; // Bounding Box 변수
	
	Wall(){ // 생성자
		state = W_ST_ALIVE; // 초기상태를 ALIVE로 지정
		x = ( SnowBros.FRAME_W / 2 ) + ( W_WIDTH * 5 ); // 초기 x좌표를 설정(가운데)
		y = SnowBros.FRAME_H - ( W_HEIGHT * 10 ); // 초기 y좌표를 화면 아래쪽에 공간을 띄움
		bb = new Rectangle(
				x - W_WIDTH/2, y - W_HEIGHT/2,
				W_WIDTH, W_HEIGHT); // Bounding Box를 초기화
	}
	
	int getState()	{ return state;	} // 상태를 알려주는 메서드
	int getX()		{ return x;		} // x좌표를 알려주는 메서드
	int getY()		{ return y;		} // y좌표를 알려주는 메서드
	Rectangle getBBox() {	return bb;	} // Bounding Box를 알려주는 메서드
	
	
	void init_Wall(){ //
		state = W_ST_ALIVE; // 초기상태를 ALIVE로 지정
		x = ( SnowBros.FRAME_W / 2 ) + ( W_WIDTH * 6 ); // 초기 x좌표를 설정(가운데)
		y = SnowBros.FRAME_H - ( W_HEIGHT * 2 ); // 초기 y좌표를 화면 아래쪽에 공간을 띄움
		bb = new Rectangle(
				x - W_WIDTH/2, y - W_HEIGHT/2,
				W_WIDTH, W_HEIGHT); // Bounding Box를 초기화
	}
	
	void setWallPosition(int x, int y){
		x = COLS * x;
		y = ROWS * y;
	}
	
	void draw(Graphics g) { // 화면을 그리는 메서드
		g.setColor(Color.darkGray);
		g.fillRect(x, y, W_WIDTH, W_HEIGHT);
	}
}
