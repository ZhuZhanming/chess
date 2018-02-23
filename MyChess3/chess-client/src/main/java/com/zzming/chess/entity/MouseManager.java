package com.zzming.chess.entity;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.zzming.chess.Main;
import com.zzming.chess.Handler.ClientHandler;

@Component("mouse")
@Lazy(true)
/**
 * Êó±ê¼àÌýÆ÷
 */
public class MouseManager extends MouseAdapter {
	@Resource(name = "step")
	private Step step;
	@Resource(name = "ch")
	private ClientHandler ch;

	public MouseManager() {
	}

	public void mouseClicked(MouseEvent e) {
		switch (step.getState()) {
		case Step.GAMEOVER:
			Main.setGameRunning(false);
			ch.action();
//			Main.updateStep("00,0066");³åÍ»£¬»­²»ÁË£¿
			// System.out.println(Step.parseState(step.toString()));
			break;
		case Step.RUNNING:
		case Step.DANGER:
			int x = e.getY() / 50;
			int y = e.getX() / 50;
			if (e.getButton() == MouseEvent.BUTTON1) {
				// System.out.println(x + ":" + y);
				step.selectPiece(x, y);
			}
			if (e.getButton() == MouseEvent.BUTTON3) {
				try {
					step.move(x, y);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			break;
		}
	}
}
