package com.zzming.chess.thread;

import java.io.IOException;

import com.zzming.chess.Server;
import com.zzming.chess.entity.StreamBag;

public class MainThread implements Runnable {
    /**
     * ��Ϊ�����action��Ϊ����ָ��ͬһ������,��������Ϊ����
     * ����id����
     */
    public static ThreadLocal<StreamBag> tl = new ThreadLocal<StreamBag>();
    private StreamBag bag;
    public MainThread(StreamBag bag) throws IOException {
    	this.bag = bag;
    }

    public void run() {
    	tl.set(bag);
        System.out.println(tl.get().getSocket().getInetAddress() + "������");
        try {
            while (true) {
                if (!Server.allPlayers.containsValue(tl.get())) {
                    // �û�δ��¼
                    new LoginHandler().action();
                } else {
                    new IndexHandler().action();
                }

            }
        } catch (ClassNotFoundException e) {
            // serialVersionUID��ͬ
            e.printStackTrace();
        } catch (IOException e) {
            // TODO:�Ͽ�����
            System.out.println(tl.get().getSocket().getInetAddress() + "�ѶϿ�����");
        } catch (Exception e) {
            // δ֪����
            e.printStackTrace();
        } finally {
            if (tl.get() != null) {
                try {
                	Server.exit(tl.get().getPlayerId());
                    tl.get().close();
                    tl.remove();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
