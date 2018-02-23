package com.zzming.chess.entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 这个类用来管理用户连接的流,避免重复创建对象流
 */
public class StreamBag {
	/**
	 * 创建game对象的时候用
	 */
	private Integer playerId;
	private Integer enemyId;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public StreamBag(Socket socket) throws IOException {
        this.socket = socket; 
        ois = new ObjectInputStream(socket.getInputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
    }
    
    public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	public Integer getEnemyId() {
		return enemyId;
	}

	public void setEnemyId(Integer enemyId) {
		this.enemyId = enemyId;
	}

	public Socket getSocket() {
        return socket;
    }
    public void writeObject(Object obj) throws IOException{
    	oos.writeObject(obj);
    }
    public Object readObject() throws ClassNotFoundException, IOException{
    	return ois.readObject();
    }
    /**
     * 关闭连接
     */
    public void close() throws IOException{
        socket.close();
    }
}
