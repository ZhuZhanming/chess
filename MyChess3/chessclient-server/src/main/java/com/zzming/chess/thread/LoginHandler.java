package com.zzming.chess.thread;

import java.io.IOException;

import com.zzming.chess.Server;
import com.zzming.chess.dao.PlayerDao;
import com.zzming.chess.entity.Player;
import com.zzming.chess.entity.StreamBag;
import com.zzming.chess.message.LoginMessage;
import com.zzming.chess.message.SystemMessage;

public class LoginHandler {
    private PlayerDao dao;
    private StreamBag bag;

    public LoginHandler() throws IOException {
        this.dao = Server.ac.getBean("playerDao", PlayerDao.class);
        this.bag = MainThread.tl.get();
    }

    public void action() throws ClassNotFoundException, IOException, Exception {
            Object msg = null;
            while ((msg = bag.readObject()) != null) {
                if (msg instanceof LoginMessage) {
                    loginAction(msg);
                } else if (msg instanceof Player) {
                    enrollAction(msg);
                } else {
                    //���ݴ���?
                    System.out.println(msg);
                    bag.writeObject(new SystemMessage("ϵͳ�쳣"));
                }
                // �����ѵ�¼
                if (Server.allPlayers.containsValue(bag)) {
                    break;
                }
            }
 
    }

    private void enrollAction(Object msg) throws IOException {
        Player p = (Player) msg;
        String code = p.getCode();
        if (dao.findByCode(code) != null) {
            bag.writeObject(new SystemMessage("�˺��Ѵ���"));
        } else {
            dao.save(p);
            p = dao.findByCode(code);
            doLogin(p);
        }
    }

    private void loginAction(Object msg) throws IOException {
        LoginMessage login = (LoginMessage) msg;
        String code = login.getCode();
        Player p = dao.findByCode(code);
        if (p == null) {
            bag.writeObject(new SystemMessage("�˺Ų�����"));
        } else if (!p.getPwd().equals(login.getPwd())) {
            bag.writeObject(new SystemMessage("���벻��ȷ"));
        } else {
            doLogin(p);
        }
    }
    /**
     * ���������û���¼����Ϊ
     */
    private void doLogin(Player p) throws IOException{
        if(Server.allPlayers.containsKey(p)){
            bag.writeObject(new SystemMessage("�û�������"));
            return;
        }
        bag.setPlayerId(p.getId());
        bag.writeObject(p);
//        player.setPlayer(p);
        Server.login(bag.getPlayerId(), bag);
//        System.out.println(player.getName()+"�ѵ�¼");
    }
}
