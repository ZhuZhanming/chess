package com.zzm.chess.game;

import java.util.LinkedList;

import com.zzm.chess.Thread.KeyManager;
import com.zzm.chess.Thread.MouseManager;

public class Game {
	public static final int BLACK = 1;
	public static final int RED = BLACK + 1;

	/**
	 * 考虑到后期要做出网络版,不能使用静态变量,ThreadLocal用于线程跨方法共享数据使用
	 */
	public static ThreadLocal<Bag> tl = new ThreadLocal<Bag>();
	private Bag bag;

	public Game() {
		System.out.println("---欢迎使用笨蛋象棋单机版---");
		try {
			bag = new Bag(new ChessPanel("象棋测试版"), new Step(), new LinkedList<String>(), new LinkedList<String>());
		} catch (Exception e) {
			System.out.println("new Game错");
			e.printStackTrace();
		}
		tl.set(bag);
	}

	public static void startDirection() {
		System.out.println("鼠标点击开始新游戏");
		System.out.println("1.加载棋局");
		System.out.println("2.查看历史记录");
		System.out.println("3.删除以前困局");
		System.out.println("4.删除历史记录");
	}

	public static void runDirection() {
		System.out.println("左键选择要移动的棋子,右键选择你要移动的位置");
		System.out.println("1:悔棋");
		System.out.println("2:取消悔棋");
		System.out.println("3:保存当前棋局");
		System.out.println("4:保存游戏记录");
		System.out.println("其他:提示");
	}

	public static void gameOverDirection() {
		System.out.println("鼠标点击继续游戏");
		System.out.println("ESC:退出游戏");
		System.out.println("其他任意键开始游戏");
	}
	
	public void start() {
		new MouseManager().addMouse();
		new KeyManager().addKey();
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
}
