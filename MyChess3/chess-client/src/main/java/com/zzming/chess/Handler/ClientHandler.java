package com.zzming.chess.Handler;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zzming.chess.Main;
import com.zzming.chess.Handler.client.IndexActivity;
import com.zzming.chess.Handler.client.LoginOrEnrollActivity;
@Component("ch")
/**
 * �ͻ��˳���
 */
public class ClientHandler {
    @Resource(name="login")
    private LoginOrEnrollActivity loginOrEnroll;
    @Resource(name="index")
    private IndexActivity index;

    public void action() {
        try {
            if (Main.getPlayer().getId() == null) {
                loginOrEnroll.doAction();
            } else if (!Main.isGameRunning()) {
                index.doAction();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("�ͻ����쳣,���˳�");
        }
    }
    @Override
    public String toString() {
        return "ClientHandler [loginOrEnroll=" + loginOrEnroll + ", index=" + index + "]";
    }
}
