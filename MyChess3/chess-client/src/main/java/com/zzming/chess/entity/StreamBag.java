package com.zzming.chess.entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * ��������������û����ӵ���,�����ظ�����������
 */
@Component("bag")
public class StreamBag {
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    @Autowired
    public StreamBag(@Value("#{config.ip}") String ip, @Value("#{config.port}") int port)
            throws UnknownHostException, IOException {
        socket = new Socket(ip, port);
        System.out.println("���������ӳɹ�");
        socket.setTcpNoDelay(true);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public Socket getSocket() {
        return socket;
    }
    

	/**
     * �ر�����
     */
    public void close() {
        try {
            if (ois != null)
                ois.close();
            if (oos != null)
                oos.close();
            if (socket != null)
                socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeObject(Object obj) throws IOException {
        oos.writeObject(obj);
    }

    public Object readObject() throws ClassNotFoundException, IOException {
        return ois.readObject();
    }
}
