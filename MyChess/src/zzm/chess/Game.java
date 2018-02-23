package zzm.chess;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 500;
	public static final int HEIGHT = 550;
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int GAMEOVER = 2;
	private int state = START;// 当前状态(默认启动状态)
	private int X, Y;// 左键选择棋子的坐标
	private int sx, sy;// 记录左键选择成功时的行和列
	private int I, J;// 右键移动的坐标
	private piece t = new piece();// 要移动的棋子
	private piece bc = new piece();// 要被吃的棋子
	public int index = -1;
	public static boolean flag = true;// 默认红方先走
	public static int[][] flags = new int[10][9];
	public static BufferedImage background;
	public static BufferedImage c;
	public static BufferedImage c_black;
	public static BufferedImage m;
	public static BufferedImage m_black;
	public static BufferedImage x;
	public static BufferedImage x_black;
	public static BufferedImage s;
	public static BufferedImage s_black;
	public static BufferedImage j;
	public static BufferedImage j_black;
	public static BufferedImage p;
	public static BufferedImage p_black;
	public static BufferedImage b;
	public static BufferedImage b_black;
	public static BufferedImage select;
	piece[] pieces = new piece[] { new C(0, 0, 1), new C(0, 8, 1), new C(9, 0, 2), new C(9, 8, 2), new M(0, 1, 1),
			new M(0, 7, 1), new M(9, 1, 2), new M(9, 7, 2), new X(0, 2, 1), new X(0, 6, 1), new X(9, 2, 2),
			new X(9, 6, 2), new S(0, 3, 1), new S(0, 5, 1), new S(9, 3, 2), new S(9, 5, 2), new J(0, 4, 1),
			new J(9, 4, 2), new P(2, 1, 1), new P(2, 7, 1), new P(7, 1, 2), new P(7, 7, 2), new B(3, 0, 1),
			new B(3, 2, 1), new B(3, 4, 1), new B(3, 6, 1), new B(3, 8, 1), new B(6, 0, 2), new B(6, 2, 2),
			new B(6, 4, 2), new B(6, 6, 2), new B(6, 8, 2) };
	static piece[] pieceBoss = new piece[2];
	static {
		try {
			c = ImageIO.read(Game.class.getResource("c.png"));
			c_black = ImageIO.read(Game.class.getResource("c_black.png"));
			m = ImageIO.read(Game.class.getResource("m.png"));
			m_black = ImageIO.read(Game.class.getResource("m_black.png"));
			x = ImageIO.read(Game.class.getResource("x.png"));
			x_black = ImageIO.read(Game.class.getResource("x_black.png"));
			s = ImageIO.read(Game.class.getResource("s.png"));
			s_black = ImageIO.read(Game.class.getResource("s_black.png"));
			j = ImageIO.read(Game.class.getResource("j.png"));
			j_black = ImageIO.read(Game.class.getResource("j_black.png"));
			p = ImageIO.read(Game.class.getResource("p.png"));
			p_black = ImageIO.read(Game.class.getResource("p_black.png"));
			b = ImageIO.read(Game.class.getResource("b.png"));
			b_black = ImageIO.read(Game.class.getResource("b_black.png"));
			background = ImageIO.read(Game.class.getResource("bbbbbb.png"));
			select = ImageIO.read(Game.class.getResource("zzming.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void action() {
		flagsAction();
		MouseAdapter l = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				switch (state) {
				case START:
					state = RUNNING;
					break;
				case GAMEOVER:
					pieces = new piece[] { new C(0, 0, 1), new C(0, 8, 1), new C(9, 0, 2), new C(9, 8, 2),
							new M(0, 1, 1), new M(0, 7, 1), new M(9, 1, 2), new M(9, 7, 2), new X(0, 2, 1),
							new X(0, 6, 1), new X(9, 2, 2), new X(9, 6, 2), new S(0, 3, 1), new S(0, 5, 1),
							new S(9, 3, 2), new S(9, 5, 2), new J(0, 4, 1), new J(9, 4, 2), new P(2, 1, 1),
							new P(2, 7, 1), new P(7, 1, 2), new P(7, 7, 2), new B(3, 0, 1), new B(3, 2, 1),
							new B(3, 4, 1), new B(3, 6, 1), new B(3, 8, 1), new B(6, 0, 2), new B(6, 2, 2),
							new B(6, 4, 2), new B(6, 6, 2), new B(6, 8, 2) };
					flagsAction();
					flag = true;
					index = -1;
					sx = 11;
					state = START;
				}
				if (state == RUNNING) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						X = e.getX();
						Y = e.getY();
						int x = Y / 50;
						int y = X / 50;
						selectPiece(x, y);
					}
					if (e.getButton() == MouseEvent.BUTTON3) {
						I = e.getX();
						J = e.getY();
						int i = J / 50;
						int j = I / 50;
						if (flag) {
							red(i, j);
						} else {
							black(i, j);
						}
						checkGameOver();
						flagsAction();
					}
				}
			}
		};
		this.addMouseListener(l);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				for (int i = 0,j=0; i < pieces.length; i++) {
					piece p = new piece();
					p = pieces[i];
					if (p instanceof J) {
						pieceBoss[j++]=p;
					}
				}

				repaint();
			}

		}, 100, 100);
	}

	public void checkGameOver() {
		int num = 0;
		for (int i = 0; i < pieces.length; i++)
			if (pieces[i] instanceof J)
				num++;
		if (num == 1)
			state = GAMEOVER;
	}

	public void flagsAction() {
		for (int i = 0; i < 10; i++)
			for (int j = 0; j < 9; j++)
				flags[i][j] = 0;
		for (int i = 0; i < pieces.length; i++) {
			flags[pieces[i].row][pieces[i].col] = pieces[i].color;
		}
	}

	public void selectPiece(int x, int y) {
		select(x, y);
		if (index == -1)
			return;
		if ((pieces[index].color == 2 && flag) || (pieces[index].color == 1 && !flag)) {
			t = pieces[index];
			sx = pieces[index].row;
			sy = pieces[index].col;
		}
	}

	public void red(int i, int j) {
		if (t.color == 1)
			return;
		if (flags[i][j] == 1 && t.eat(i, j)) {
			eat(i, j);
		} else if (flags[i][j] == 0 && t.run(i, j)) {
			t.moveto(i, j);
		}
	}

	public void black(int i, int j) {
		if (t.color == 2)
			return;
		if (flags[i][j] == 2 && t.eat(i, j)) {
			eat(i, j);
		} else if (flags[i][j] == 0 && t.run(i, j)) {
			t.moveto(i, j);
		}
	}

	public void select(int x, int y) {
		if (flags[x][y] != 0) {
			for (int i = 0; i < pieces.length; i++)
				if (pieces[i].select(x, y)) {
					index = i;
					break;
				}
		}
	}

	public void eat(int i, int j) {
		select(i, j);
		t.moveto(i, j);
		bc = pieces[index];
		pieces[index] = pieces[pieces.length - 1];
		pieces[pieces.length - 1] = bc;
		pieces = Arrays.copyOf(pieces, pieces.length - 1);
	}

	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
		paintLine(g);
		if (state != START) {
			paintWho(g);
			paintSelect(g);
			paintPiece(g);
		}
		paintState(g);

	}

	public void paintLine(Graphics g) {
		for (int i = 0; i < 11; i++)
			g.drawLine(20, 20 + 50 * i, 420, 20 + 50 * i);
		for (int i = 0; i < 9; i++)
			g.drawLine(20 + 50 * i, 20, 20 + 50 * i, 220);
		for (int i = 0; i < 9; i++)
			g.drawLine(20 + 50 * i, 270, 20 + 50 * i, 470);
		g.drawLine(20, 220, 20, 270);
		g.drawLine(420, 220, 420, 270);
		g.drawLine(170, 20, 270, 120);
		g.drawLine(270, 20, 170, 120);
		g.drawLine(170, 370, 270, 470);
		g.drawLine(270, 370, 170, 470);
	}

	public void paintWho(Graphics g) {
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
		if (flag) {
			g.setColor(new Color(0xff0000));
			g.drawString("红方走", WIDTH / 2 - 85, HEIGHT / 2 - 20);
		} else {
			g.setColor(new Color(0x000000));
			g.drawString("黑方走", WIDTH / 2 - 85, HEIGHT / 2 - 20);
		}
	}

	public void paintSelect(Graphics g) {
		if (index != -1)
			g.drawImage(select, sy * 50 - 5, sx * 50 - 5, null);
	}

	public void paintPiece(Graphics g) {
		for (int i = 0; i < pieces.length; i++) {
			g.drawImage(pieces[i].image, pieces[i].col * 50, pieces[i].row * 50, null);
		}
	}

	public void paintState(Graphics g) {
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		switch (state) {
		case START:
			g.setColor(new Color(0xff0000));
			g.drawString("点击开始游戏", WIDTH / 2 - 110, HEIGHT / 2 - 70);
			break;
		case GAMEOVER:
			g.setColor(new Color(0x000000));
			g.drawString("将军,游戏结束", WIDTH / 2 - 120, HEIGHT / 2 - 70);
			break;
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("象棋测试版");// import javax.swing.JFrame;
		Game game = new Game();
		frame.add(game);
		frame.setSize(WIDTH - 50, HEIGHT);
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);// 自动调用paint(g)
		game.action();
	}
}
