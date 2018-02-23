package com.zzming.chess.Handler;

import java.util.List;

import com.zzming.chess.Main;
import com.zzming.chess.entity.Player;
import com.zzming.chess.message.PlayersMessage;
import com.zzming.chess.message.StartMessage;
import com.zzming.chess.message.StepMessage;
import com.zzming.chess.message.SystemMessage;
public class ServerHandler implements Runnable {
    private ClientHandler ch;
    public ServerHandler(ClientHandler ch){
        this.ch = ch;
    }
    public void run() {
        try {
            Object obj = null;
            while ((obj = Main.getObject()) != null) {
                if (obj instanceof Player) {
                    if (Main.getPlayer().getId()==null) {
                        Player player = (Player) obj;
                        Main.getPlayer().copyPlayer(player);
                        System.out.println("登录成功\n" + player.toString());
                    } else {
                        System.out.println("请勿重复登录");
                    }
                } else if (obj instanceof SystemMessage) {
                    System.out.println((SystemMessage) obj);
                } else if (obj instanceof PlayersMessage) {
                    printPlayers(obj);
                } else if (obj instanceof StepMessage) {
                    StepMessage stepMsg = (StepMessage) obj;
                    Main.updateStep(stepMsg.getStepStr());
                } else if (obj instanceof StartMessage) {
                    StartMessage sm = (StartMessage) obj;
                    Main.gameStart(sm);
                } else if (obj instanceof Exception) {
                    System.out.println(((Exception) obj).getMessage());
                }
//                System.out.println(obj);
                ch.action();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("与服务器断开连接");
        } finally {
            Main.close();
        }
    }

    /**
     * 在控制台打印Player的List集合
     */
    private void printPlayers(Object msg) {
        PlayersMessage players = (PlayersMessage) msg;
        List<Player> list = players.getPlayers();
        if(list==null || list.size()==0){
        	System.out.println("未找到玩家");
        }
        for (int i = 1; i <= list.size(); i++) {
            if (i % 3 == 0) {
                System.out.println();
            }
            Player p = list.get(i - 1);
            System.out.print(p.getId() + ":" + p.getName() + "\t");
        }
        System.out.println();
    }

}
