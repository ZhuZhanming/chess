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
        System.out.print(INFO + ".������Ϣ");
        System.out.print(MODIFY + ".�޸ĸ�����Ϣ");
        System.out.print(FINDENEMY + ".�������");
        System.out.print(GetEnemy + ".��˭��ս");
        System.out.print(ALLPLAYER + ".�������");
        System.out.println(EXIT + ".�˳�");
        System.out.print("ѡ��:");
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
            System.out.println("��Ų���ȷ");
        }
    }

    /**
     * util ��������Ĺ���(��Ϊ����ͬһ������,��Ϊ��һ������,Ҫ�½�������)
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
        System.out.println("��ʽ:@+�Է�id");
        String str = Get.getEnemyString();
        sendRequest(str);
    }

    private void findEnemy() throws IOException, ClassNotFoundException {
        sendRequest("findEnemy");
    }

    private void modity() throws IOException {
        System.out.print("1.����");
        System.out.print("2.�ǳ�");
        System.out.print("3.�ֻ���");
        System.out.print("4.email");
        System.out.println("����:ȡ��");
        System.out.print("ѡ��:");
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
     * �����޸Ĺ����player(����new һ������),
     */
    private void sendPlayer() throws IOException{
        Player player = new Player(p);
        Main.sendObject(player);
    }
    private void modifyEmail() {
        System.out.println("������������");
        String str = Get.getEmail();
        p.setEmail(str);
    }

    private void modifyTelephone() {
        System.out.println("���������ֻ���");
        String str = Get.getTelephone();
        p.setTelephone(str);
    }

    private void modifyName() {
        System.out.println("���������ǳ�");
        String str = Get.getString();
        System.out.println(p);
        p.setName(str);
    }

    private void modifyPwd() {
        System.out.println("������������");
        String str = Get.getString();
        p.setPwd(str);
    }
    /**
     * ��Ϊ�������������--������������Ϣ����Ҫ�Լ�doAction
     */
    private void info() throws ClassNotFoundException, IOException {
        sendRequest("info");
    }
}
