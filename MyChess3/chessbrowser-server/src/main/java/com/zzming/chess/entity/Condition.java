package com.zzming.chess.entity;

import java.sql.Timestamp;

public class Condition {
	private Integer id;
	private Integer status;
	private Integer color;
	private Timestamp begin;
	private Timestamp end;
	public Condition() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public Timestamp getBegin() {
		return begin;
	}
	public void setBegin(Timestamp begin) {
		this.begin = begin;
	}
	public Timestamp getEnd() {
		return end;
	}
	public void setEnd(Timestamp end) {
		this.end = end;
	}
	
}
