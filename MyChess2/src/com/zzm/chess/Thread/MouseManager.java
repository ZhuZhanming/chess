package com.zzm.chess.Thread;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.zzm.chess.game.Bag;
import com.zzm.chess.game.Game;
import com.zzm.chess.game.Step;

public class MouseManager extends MouseAdapter {
	private Bag bag;

	public MouseManager() {
		super();
		this.bag = Game.tl.get();
	}

	/**
	 * 添加属性cp版的鼠标监听器
	 */
	public void addMouse() {
		bag.getCp().addMouseListener(this);
	}

	public void mouseClicked(MouseEvent e) {
		/*
		 * 跟main方法不是一个线程,不能直接得到当前棋盘的bag,但能指向同一个bag
		 */
		if (Game.tl.get() == null) {
			Game.tl.set(bag);
		}
		Step step = bag.getStep();
		switch (step.getState()) {
		case Step.START:
			try {
				bag.newGame();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			break;
		case Step.GAMEOVER:
			Step.initStep(step, new Step());
			bag.getBack().clear();
			break;
		case Step.RUNNING:
			int x = e.getY() / 50;
			int y = e.getX() / 50;
			if (e.getButton() == MouseEvent.BUTTON1) {
				step.selectPiece(x, y);
			}
			if (e.getButton() == MouseEvent.BUTTON3) {
				step.move(x, y);
			}
			break;
		}
		bag.getCp().paintStep(step.toString());
	}

}
