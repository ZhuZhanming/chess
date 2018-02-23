package com.zzming.chess.entity;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Random;

import com.zzming.chess.message.StartMessage;

/**
 * һ����Ϸ�໥��ս�Ĺ���
 */
public class Game {
	private Integer id;
	private Integer redId;
	private Integer blackId;
	private Integer winId;
	private Timestamp startTime;
	private Timestamp endTime;

	private StreamBag redBag;
	private StreamBag blackBag;

	public Game(StreamBag red, StreamBag black) {
		this.startTime = new Timestamp(System.currentTimeMillis());
		this.redBag = red;
		this.blackBag = black;
		if (new Random().nextInt(2) == 1)
			swap();
		this.redId = red.getPlayerId();
		this.blackId = black.getPlayerId();
	}

	/**
	 * ����StreamBag
	 */
	private void swap() {
		StreamBag t = redBag;
		redBag = blackBag;
		blackBag = t;
	}

	/**
	 * ��˫�����������Ϣ
	 */
	public void send(Object msg) throws IOException {
		redBag.writeObject(msg);
		blackBag.writeObject(msg);
	}

	/**
	 * ���߿ͻ�����Ϸ��ʼ��Ϣ
	 */
	public void start() throws IOException {
		redBag.writeObject(new StartMessage(StartMessage.RED));
		// System.out.println(red.getSocket().getPort()+":"+"�ѷ��Ϳ�ʼ��Ϣ");
		blackBag.writeObject(new StartMessage(StartMessage.BLACK));
		// System.out.println(black.getSocket().getPort()+":"+"�ѷ��Ϳ�ʼ��Ϣ");
		System.out.println(redBag.getSocket() + "\n" + blackBag.getSocket());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRedId() {
		return redId;
	}

	public void setRedId(Integer redId) {
		this.redId = redId;
	}

	public Integer getBlackId() {
		return blackId;
	}

	public void setBlackId(Integer blackId) {
		this.blackId = blackId;
	}

	public Integer getWinId() {
		return winId;
	}

	public void setWinId(Integer winId) {
		this.winId = winId;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public StreamBag getRed() {
		return redBag;
	}

	public void setRed(StreamBag red) {
		this.redBag = red;
	}

	public StreamBag getBlack() {
		return blackBag;
	}

	public void setBlack(StreamBag black) {
		this.blackBag = black;
	}

}
