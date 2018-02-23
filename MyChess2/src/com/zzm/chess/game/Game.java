package com.zzm.chess.game;

import java.util.LinkedList;

import com.zzm.chess.Thread.KeyManager;
import com.zzm.chess.Thread.MouseManager;

public class Game {
	public static final int BLACK = 1;
	public static final int RED = BLACK + 1;

	/**
	 * ���ǵ�����Ҫ���������,����ʹ�þ�̬����,ThreadLocal�����߳̿緽����������ʹ��
	 */
	public static ThreadLocal<Bag> tl = new ThreadLocal<Bag>();
	private Bag bag;

	public Game() {
		System.out.println("---��ӭʹ�ñ������嵥����---");
		try {
			bag = new Bag(new ChessPanel("������԰�"), new Step(), new LinkedList<String>(), new LinkedList<String>());
		} catch (Exception e) {
			System.out.println("new Game��");
			e.printStackTrace();
		}
		tl.set(bag);
	}

	public static void startDirection() {
		System.out.println("�������ʼ����Ϸ");
		System.out.println("1.�������");
		System.out.println("2.�鿴��ʷ��¼");
		System.out.println("3.ɾ����ǰ����");
		System.out.println("4.ɾ����ʷ��¼");
	}

	public static void runDirection() {
		System.out.println("���ѡ��Ҫ�ƶ�������,�Ҽ�ѡ����Ҫ�ƶ���λ��");
		System.out.println("1:����");
		System.out.println("2:ȡ������");
		System.out.println("3:���浱ǰ���");
		System.out.println("4:������Ϸ��¼");
		System.out.println("����:��ʾ");
	}

	public static void gameOverDirection() {
		System.out.println("�����������Ϸ");
		System.out.println("ESC:�˳���Ϸ");
		System.out.println("�����������ʼ��Ϸ");
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
