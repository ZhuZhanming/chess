package com.zzming.chess.thread;

import java.io.IOException;

import com.zzming.chess.Server;
import com.zzming.chess.entity.StreamBag;

public class MainThread implements Runnable {
    /**
     * 因为与后续action行为必须指向同一个对象,所以设置为不变
     * 改用id区分
     */
    public static ThreadLocal<StreamBag> tl = new ThreadLocal<StreamBag>();
    private StreamBag bag;
    public MainThread(StreamBag bag) throws IOException {
    	this.bag = bag;
    }

    public void run() {
    	tl.set(bag);
        System.out.println(tl.get().getSocket().getInetAddress() + "已连接");
        try {
            while (true) {
                if (!Server.allPlayers.containsValue(tl.get())) {
                    // 用户未登录
                    new LoginHandler().action();
                } else {
                    new IndexHandler().action();
                }

            }
        } catch (ClassNotFoundException e) {
            // serialVersionUID不同
            e.printStackTrace();
        } catch (IOException e) {
            // TODO:断开连接
            System.out.println(tl.get().getSocket().getInetAddress() + "已断开连接");
        } catch (Exception e) {
            // 未知错误
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
