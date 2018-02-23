package com.zzming.chess.Handler.client;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zzming.chess.Main;
import com.zzming.chess.entity.Player;
import com.zzming.chess.message.RequestMessage;
import com.zzming.chess.util.Get;
@Component("index")
public class IndexActivity {
    private static final int INFO = 1;
    private static final int MODIFY = INFO + 1;
    private static final int FINDENEMY = MODIFY + 1;
    private static final int GetEnemy = FINDENEMY + 1;
    private static final int ALLPLAYER = GetEnemy + 1;
    private static final int EXIT = ALLPLAYER + 1;
    @Resource(name="player")
    private Player p;

    public IndexActivity() {
    }

    public void doAction() throws IOException, ClassNotFoundException {
        System.out.print(INFO + ".个人信息");
        System.out.print(MODIFY + ".修改个人信息");
        System.out.print(FINDENEMY + ".大厅玩家");
        System.out.print(GetEnemy + ".与谁对战");
        System.out.print(ALLPLAYER + ".所有玩家");
        System.out.println(EXIT + ".退出");
        System.out.print("选择:");
        int n = Get.getInt();
        switch (n) {
        case INFO:
            info();
            break;
        case MODIFY:
            modity();
            break;
        case FINDENEMY:
            findEnemy();
            break;
        case GetEnemy:
            getEnemy();
            break;
        case ALLPLAYER:
            allPlayer();
            break;
        case EXIT:
            exit();
            break;
        default:
            System.out.println("序号不正确");
        }
    }

    /**
     * util 发送请求的工具(因为发送同一个对象,认为是一个请求,要新建对象发送)
     */
    private void sendRequest(String str) throws IOException {
        RequestMessage rm = new RequestMessage(str);
        Main.sendObject(rm);
    }

    private void exit() throws IOException {
        Main.getPlayer().setId(null);
        sendRequest("exit");
    }

    private void allPlayer() throws IOException {
        sendRequest("allPlayers");
    }

    private void getEnemy() throws IOException {
        System.out.println("格式:@+对方id");
        String str = Get.getEnemyString();
        sendRequest(str);
    }

    private void findEnemy() throws IOException, ClassNotFoundException {
        sendRequest("findEnemy");
    }

    private void modity() throws IOException {
        System.out.print("1.密码");
        System.out.print("2.昵称");
        System.out.print("3.手机号");
        System.out.print("4.email");
        System.out.println("其他:取消");
        System.out.print("选择:");
        int n = Get.getInt();
        switch (n) {
        case 1:
            modifyPwd();
            break;
        case 2:
            modifyName();
            break;
        case 3:
            modifyTelephone();
            break;
        case 4:
            modifyEmail();
            break;
        default:
            return;
        }
        sendPlayer();
    }
    /**
     * 发送修改过后的player(重新new 一个对象),
     */
    private void sendPlayer() throws IOException{
        Player player = new Player(p);
        Main.sendObject(player);
    }
    private void modifyEmail() {
        System.out.println("请输入新邮箱");
        String str = Get.getEmail();
        p.setEmail(str);
    }

    private void modifyTelephone() {
        System.out.println("请输入新手机号");
        String str = Get.getTelephone();
        p.setTelephone(str);
    }

    private void modifyName() {
        System.out.println("请输入新昵称");
        String str = Get.getString();
        System.out.println(p);
        p.setName(str);
    }

    private void modifyPwd() {
        System.out.println("请输入新密码");
        String str = Get.getString();
        p.setPwd(str);
    }
    /**
     * 因为不与服务器交互--服务器不会信息。需要自己doAction
     */
    private void info() throws ClassNotFoundException, IOException {
        sendRequest("info");
    }
}
