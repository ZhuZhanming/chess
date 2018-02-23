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
 * 这个类用来管理用户连接的流,避免重复创建对象流
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
        System.out.println("服务器连接成功");
        socket.setTcpNoDelay(true);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public Socket getSocket() {
        return socket;
    }
    

	/**
     * 关闭连接
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
