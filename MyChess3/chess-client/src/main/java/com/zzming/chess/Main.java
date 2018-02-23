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
     * �ж���Ϸ�Ƿ���������
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
            System.out.println("ʧ��");
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
     * ��ʼ��Ϸ
     */
    public static void gameStart(StartMessage start) {
        gameRunning = true;
        color = start.getInfo();
        if (color == Piece.RED) {
            System.out.println("��Ϸ��ʼ,���Ǻ�ɫ��");
        } else {
            System.out.println("��Ϸ��ʼ,���Ǻ�ɫ��");
        }
        if (step == null) {
            //��Ϸ���Ѵ�
            step = context.getBean("step", Step.class);
            panel = context.getBean("panel", ChessPanel.class);
            mouse = context.getBean("mouse", MouseManager.class);
            panel.addMouseListener(mouse);
        }else{
            //δ��
            step.parseStepStr(step.getInitStr());
            panel.repaint();
        }
    }

    /**
     * ����Object��Ϣ
     */
    public static void sendObject(Object obj) throws IOException {
        bag.writeObject(obj);
    }

    /**
     * ����Object��Ϣ
     * 
     * @throws IOException
     *             ��������Ͽ����
     * @throws ClassNotFoundException
     *             δ֪Class����
     */
    public static Object getObject() throws ClassNotFoundException, IOException {
        return bag.readObject();
    }

    /**
     * �����������������ͬ���ͻ���
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
