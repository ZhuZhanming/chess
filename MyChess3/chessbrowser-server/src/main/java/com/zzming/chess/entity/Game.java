package com.zzming.chess.entity;

import java.sql.Timestamp;

/**
 * 一句游戏相互对战的管理
 */
public class Game {
	private Integer id;
	private Integer redId;
	private Integer blackId;
	private Integer winId;
	private Timestamp startTime;
	private Timestamp endTime;

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Game other = (Game) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Game [id=" + id + ", redId=" + redId + ", blackId=" + blackId + ", winId=" + winId + ", startTime="
                + startTime + ", endTime=" + endTime + "]";
    }
	
}
