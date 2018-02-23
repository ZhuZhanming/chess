package com.zzm.chess.game;

import java.util.Deque;

public class Bag {
	private ChessPanel cp;
	private Step step;
	private Deque<String> back;
	private Deque<String> front;

	public Bag(ChessPanel cp, Step step, Deque<String> back, Deque<String> front) {
		super();
		this.cp = cp;
		this.step = step;
		this.back = back;
		this.front = front;
	}

	public ChessPanel getCp() {
		return cp;
	}

	public Step getStep() {
		return step;
	}

	public Deque<String> getBack() {
		return back;
	}

	public Deque<String> getFront() {
		return front;
	}

	/**
	 * ��ʼո�µ���Ϸ
	 */
	public void newGame() throws Exception {
		Step.initStep(step, new Step("newgame"));// �쳣
		back.push(step.toString());
		step.setState(Step.RUNNING);
		Game.runDirection();
	}

	/**
	 * ��
	 */
	public void paint() {
		cp.paintStep(step.toString());
	}

	/**
	 * ����
	 * 
	 * @return�ɹ�û��
	 */
	public boolean backAction() {
		if (back.size() > 1) {
			String stepStr = back.pop();
			front.push(stepStr);
			Step.initStep(step, Step.parseStep(back.peek()));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ǰ��
	 * 
	 * @return�ɹ�û��
	 */
	public boolean frontAction() {
		if (front.size() > 0) {
			String stepStr = front.pop();
			back.push(stepStr);
			Step.initStep(step, Step.parseStep(stepStr));
			return true;
		} else {
			return false;
		}
	}
}
