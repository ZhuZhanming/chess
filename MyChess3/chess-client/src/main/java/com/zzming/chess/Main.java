package com.zzming.chess;


import java.io.IOException;
import java.net.UnknownHostException;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zzming.chess.Handler.ClientHandler;
import com.zzming.chess.Handler.ServerHandler;
import com.zzming.chess.entity.ChessPanel;
import com.zzming.chess.entity.MouseManager;
import com.zzming.chess.entity.Player;
import com.zzming.chess.entity.Step;
import com.zzming.chess.entity.StreamBag;
import com.zzming.chess.entity.piece.Piece;
import com.zzming.chess.message.StartMessage;

public class Main {
    private static AbstractApplicationContext context;
    private static StreamBag bag;
    private static Player player;
    private static ClientHandler ch;
    private static ServerHandler sh;
    private static Step step;
    private static ChessPanel panel;
    private static MouseManager mouse;
    private static int color;
    /**
     * 判断游戏是否正在运行
     */
    private static boolean gameRunning = false;
    static {
        try {
            context = new ClassPathXmlApplicationContext("spring.xml");
            bag = context.getBean("bag", StreamBag.class);
            player = context.getBean("player", Player.class);
            ch = context.getBean("ch", ClientHandler.class);
            sh = new ServerHandler(ch);
        } catch (Exception e) {
            System.out.println("失败");
            e.printStackTrace();
        }
    }

    public static boolean isRed() {
        return color == Piece.RED;
    }

    public static int getColor() {
        return color;
    }

    public static boolean isGameRunning() {
        return gameRunning;
    }
    
    public static void setGameRunning(boolean gameRunning) {
        Main.gameRunning = gameRunning;
    }

    /**
     * 开始游戏
     */
    public static void gameStart(StartMessage start) {
        gameRunning = true;
        color = start.getInfo();
        if (color == Piece.RED) {
            System.out.println("游戏开始,你是红色方");
        } else {
            System.out.println("游戏开始,你是黑色方");
        }
        if (step == null) {
            //游戏框已打开
            step = context.getBean("step", Step.class);
            panel = context.getBean("panel", ChessPanel.class);
            mouse = context.getBean("mouse", MouseManager.class);
            panel.addMouseListener(mouse);
        }else{
            //未打开
            step.parseStepStr(step.getInitStr());
            panel.repaint();
        }
    }

    /**
     * 发送Object信息
     */
    public static void sendObject(Object obj) throws IOException {
        bag.writeObject(obj);
    }

    /**
     * 接收Object信息
     * 
     * @throws IOException
     *             与服务器断开点解
     * @throws ClassNotFoundException
     *             未知Class对象
     */
    public static Object getObject() throws ClassNotFoundException, IOException {
        return bag.readObject();
    }

    /**
     * 与服务器传来的数据同步和画面
     */
    public static void updateStep(String stepStr) {
        step.parseStepStr(stepStr);
        panel.repaint();
    }

    public static Player getPlayer() {
        return player;
    }

    public static ChessPanel getPanel() {
        return panel;
    }

    public static Step getStep() {
        return step;
    }

    public static void close() {
        bag.close();
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        ch.action();
        new Thread(sh).start();
    }
}
