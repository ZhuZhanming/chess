package com.zzming.chess.entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * ��������������û����ӵ���,�����ظ�����������
 */
public class StreamBag {
	/**
	 * ����game�����ʱ����
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
     * �ر�����
     */
    public void close() throws IOException{
        socket.close();
    }
}
