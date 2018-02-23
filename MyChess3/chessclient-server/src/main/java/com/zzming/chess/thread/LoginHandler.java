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
                    //数据错误?
                    System.out.println(msg);
                    bag.writeObject(new SystemMessage("系统异常"));
                }
                // 用于已登录
                if (Server.allPlayers.containsValue(bag)) {
                    break;
                }
            }
 
    }

    private void enrollAction(Object msg) throws IOException {
        Player p = (Player) msg;
        String code = p.getCode();
        if (dao.findByCode(code) != null) {
            bag.writeObject(new SystemMessage("账号已存在"));
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
            bag.writeObject(new SystemMessage("账号不存在"));
        } else if (!p.getPwd().equals(login.getPwd())) {
            bag.writeObject(new SystemMessage("密码不正确"));
        } else {
            doLogin(p);
        }
    }
    /**
     * 用来处理用户登录的行为
     */
    private void doLogin(Player p) throws IOException{
        if(Server.allPlayers.containsKey(p)){
            bag.writeObject(new SystemMessage("用户已在线"));
            return;
        }
        bag.setPlayerId(p.getId());
        bag.writeObject(p);
//        player.setPlayer(p);
        Server.login(bag.getPlayerId(), bag);
//        System.out.println(player.getName()+"已登录");
    }
}
