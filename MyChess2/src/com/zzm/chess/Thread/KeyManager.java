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
	 * 添加属性cp版键盘监听器
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
		System.out.println("输入你要删除的困局(可以不输后缀哦)");
		IOmanager.findStep();
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		String info = IOmanager.deleteStep(name);
		deleteAlert(info);
	}
	private void deleteBackAction(){
		System.out.println("输入你要删除的记录(可以不输后缀哦)");
		IOmanager.findDeque();
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		String info = IOmanager.deleteDeque(name);
		deleteAlert(info);
	}
	private void deleteAlert(String info){
		if(info!=null){
			System.out.println(info);
			System.out.println("文件已删除!");
		}else{
			System.out.println("操作失败,文件不存在");
		}
	}
	private void loadBackAction() {
		System.out.println("输入你要记录(可以不输后缀哦)");
		IOmanager.findDeque();
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		try {
			bag.getBack().addAll(IOmanager.loadDeque(name));
			String stepStr = bag.getBack().peek();
			Step.initStep(bag.getStep(), Step.parseStep(stepStr));
			System.out.println("加载成功...");
		} catch (Exception e) {
			System.out.println("文件不存在");
			Game.startDirection();
		}
	}

	private void loadStepAction() {
		System.out.println("输入你要的读取的死局(可以不输后缀哦)");
		IOmanager.findStep();
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		try {
			Step.initStep(bag.getStep(), IOmanager.loadStep(name));
			System.out.println("加载成功...");
		} catch (Exception e) {
			System.out.println("文件不存在");
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
		System.out.println("请输入保存的名字(或回车使用系统名):");
		Scanner scan = new Scanner(System.in);
		String name = scan.nextLine();
		return name;
	}

	private void saveBack() {
		String name = getName();
		String str = IOmanager.saveDeque(bag.getBack(), name);
		if (str != null) {
			System.out.println("保存的文件名:" + str);
		} else {
			System.out.println("保存失败!");
		}
	}

	private void saveStep() {
		String name = getName();
		String str = IOmanager.saveStep(bag.getStep(), name);
		if (str != null) {
			System.out.println("保存成功文件名为:" + str);
		} else {
			System.out.println("系统异常,保存失败!");
		}
	}

	private void front() {
		if (bag.frontAction()) {
			System.out.println("前进成功");
		} else {
			System.out.println("前进失败,已经到顶端了");
		}
	}

	private void back() {
		if (bag.backAction()) {
			System.out.println("后退成功");
		} else {
			System.out.println("后退失败,应经到低端了");
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
