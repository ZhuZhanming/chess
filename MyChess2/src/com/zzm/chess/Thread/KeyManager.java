package com.zzm.chess.Thread;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

import com.zzm.chess.game.Bag;
import com.zzm.chess.game.ChessPanel;
import com.zzm.chess.game.Game;
import com.zzm.chess.game.IOmanager;
import com.zzm.chess.game.Step;

public class KeyManager extends KeyAdapter {
	private Bag bag;

	public KeyManager() {
		super();
		this.bag = Game.tl.get();
	}

	/**
	 * �������cp����̼�����
	 */
	public void addKey() {
		ChessPanel cp = bag.getCp();
		cp.addKeyListener(this);
		cp.setFocusable(true);
		cp.requestFocus();
	}

	public void keyPressed(KeyEvent e) {
		if (Game.tl.get() == null) {
			Game.tl.set(bag);
		}
		int key = e.getKeyCode();
		switch (bag.getStep().getState()) {
		case Step.GAMEOVER:
			processGameOverKey(key);
			break;
		case Step.RUNNING:
			processRunningKey(key);
			break;
		case Step.START:
			processStartKey(key);
			break;
		}
		bag.paint();
	}

	private void processStartKey(int key) {
		switch (key) {
		case KeyEvent.VK_1:
			loadStepAction();
			break;
		case KeyEvent.VK_2:
			loadBackAction();
			break;
		case KeyEvent.VK_3:
			deleteStepAction();
			break;
		case KeyEvent.VK_4:
			deleteBackAction();
			break;
		default:
			Game.startDirection();
		}
	}
	private void deleteStepAction(){
		System.out.println("������Ҫɾ��������(���Բ����׺Ŷ)");
		IOmanager.findStep();
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		String info = IOmanager.deleteStep(name);
		deleteAlert(info);
	}
	private void deleteBackAction(){
		System.out.println("������Ҫɾ���ļ�¼(���Բ����׺Ŷ)");
		IOmanager.findDeque();
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		String info = IOmanager.deleteDeque(name);
		deleteAlert(info);
	}
	private void deleteAlert(String info){
		if(info!=null){
			System.out.println(info);
			System.out.println("�ļ���ɾ��!");
		}else{
			System.out.println("����ʧ��,�ļ�������");
		}
	}
	private void loadBackAction() {
		System.out.println("������Ҫ��¼(���Բ����׺Ŷ)");
		IOmanager.findDeque();
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		try {
			bag.getBack().addAll(IOmanager.loadDeque(name));
			String stepStr = bag.getBack().peek();
			Step.initStep(bag.getStep(), Step.parseStep(stepStr));
			System.out.println("���سɹ�...");
		} catch (Exception e) {
			System.out.println("�ļ�������");
			Game.startDirection();
		}
	}

	private void loadStepAction() {
		System.out.println("������Ҫ�Ķ�ȡ������(���Բ����׺Ŷ)");
		IOmanager.findStep();
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		try {
			Step.initStep(bag.getStep(), IOmanager.loadStep(name));
			System.out.println("���سɹ�...");
		} catch (Exception e) {
			System.out.println("�ļ�������");
			Game.startDirection();
		}
	}

	private void processRunningKey(int key) {
		switch (key) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_1:
			back();
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_2:
			front();
			break;
		case KeyEvent.VK_3:
			saveStep();
			break;
		case KeyEvent.VK_4:
			saveBack();
			break;
		default:
			Game.runDirection();
			break;
		}
	}

	private static String getName() {
		System.out.println("�����뱣�������(��س�ʹ��ϵͳ��):");
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		return name;
	}

	private void saveBack() {
		String name = getName();
		String str = IOmanager.saveDeque(bag.getBack(), name);
		if (str != null) {
			System.out.println("������ļ���:" + str);
		} else {
			System.out.println("����ʧ��!");
		}
	}

	private void saveStep() {
		String name = getName();
		String str = IOmanager.saveStep(bag.getStep(), name);
		if (str != null) {
			System.out.println("����ɹ��ļ���Ϊ:" + str);
		} else {
			System.out.println("ϵͳ�쳣,����ʧ��!");
		}
	}

	private void front() {
		if (bag.frontAction()) {
			System.out.println("ǰ���ɹ�");
		} else {
			System.out.println("ǰ��ʧ��,�Ѿ���������");
		}
	}

	private void back() {
		if (bag.backAction()) {
			System.out.println("���˳ɹ�");
		} else {
			System.out.println("����ʧ��,Ӧ�����Ͷ���");
		}
	}

	private void processGameOverKey(int key) {
		switch (key) {
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		}
	}
}
