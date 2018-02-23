package com.zzming.chess;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zzming.chess.entity.Game;
import com.zzming.chess.entity.StreamBag;
import com.zzming.chess.thread.MainThread;

public class Server {
    public static ApplicationContext ac;
    /**
     * ���е�¼���,key�ĳ�Integer-playerId����֤���ݵ�׼ȷ�Ժ��ײ�����
     */
    public static Map<Integer, StreamBag> allPlayers;
    /**
     * ���Զ�ս���
     */
    public static Map<Integer, StreamBag> availPlayers;
    /**
     * ��ս��Ϸ
     */
    public static Map<Integer,Game> games;
    private ServerSocket server;
    private ExecutorService threadPool;

    public Server() throws Exception {
        try {
            System.out.println("��������ڳ�ʼ��...");
            init();
            System.out.println("����˳�ʼ�����...");
        } catch (Exception e) {
            System.out.println("��ʼ�������ʧ��!");
            throw e;
        }
    }

    private void init() throws IOException {
        Properties props = new Properties();
        try {
            ac = new ClassPathXmlApplicationContext("spring.xml");
            /**
             * ��֤�̰߳�ȫ
             */
            allPlayers = Collections.synchronizedMap(new HashMap<Integer,StreamBag>());
            availPlayers = Collections.synchronizedMap(new HashMap<Integer,StreamBag>());
            games = Collections.synchronizedMap(new HashMap<Integer, Game>());
            props.load(Server.class.getClassLoader().getResourceAsStream("config.properties"));
            threadPool = Executors.newFixedThreadPool(Integer.parseInt(props.getProperty("sum")));
            server = new ServerSocket(Integer.parseInt(props.getProperty("port")));
        } catch (IOException e) {
            System.out.println("��ʼ������ʧ��!");
            e.printStackTrace();
            throw e;
        }
    }

    public void start() throws Exception {
        try {
            while (true) {
                Socket socket = server.accept();
                socket.setTcpNoDelay(true);
                StreamBag bag = new StreamBag(socket);
                MainThread main = new MainThread(bag);
                threadPool.execute(main);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    /**
     * �û���¼����
     */
    public static void login(Integer playerId,StreamBag bag){
        allPlayers.put(playerId, bag);
        availPlayers.put(playerId, bag);
    }
    /**
     * �û��˳�����
     */
    public static void exit(Integer playerId){
        allPlayers.remove(playerId);
        availPlayers.remove(playerId);
    }

    @Override
    public String toString() {
        return "ChessServer [server=" + server + ", threadPool=" + threadPool + "]";
    }
    public static void main(String[] args) throws Exception {
        new Server().start();
    }
}
