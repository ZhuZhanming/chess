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
     * 所有登录玩家,key改成Integer-playerId，保证数据的准确性和易操作性
     */
    public static Map<Integer, StreamBag> allPlayers;
    /**
     * 可以对战玩家
     */
    public static Map<Integer, StreamBag> availPlayers;
    /**
     * 对战游戏
     */
    public static Map<Integer,Game> games;
    private ServerSocket server;
    private ExecutorService threadPool;

    public Server() throws Exception {
        try {
            System.out.println("服务端正在初始化...");
            init();
            System.out.println("服务端初始化完毕...");
        } catch (Exception e) {
            System.out.println("初始化服务端失败!");
            throw e;
        }
    }

    private void init() throws IOException {
        Properties props = new Properties();
        try {
            ac = new ClassPathXmlApplicationContext("spring.xml");
            /**
             * 保证线程安全
             */
            allPlayers = Collections.synchronizedMap(new HashMap<Integer,StreamBag>());
            availPlayers = Collections.synchronizedMap(new HashMap<Integer,StreamBag>());
            games = Collections.synchronizedMap(new HashMap<Integer, Game>());
            props.load(Server.class.getClassLoader().getResourceAsStream("config.properties"));
            threadPool = Executors.newFixedThreadPool(Integer.parseInt(props.getProperty("sum")));
            server = new ServerSocket(Integer.parseInt(props.getProperty("port")));
        } catch (IOException e) {
            System.out.println("初始化属性失败!");
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
     * 用户登录管理
     */
    public static void login(Integer playerId,StreamBag bag){
        allPlayers.put(playerId, bag);
        availPlayers.put(playerId, bag);
    }
    /**
     * 用户退出管理
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
