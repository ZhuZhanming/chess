package com.zzming.chess.Handler.client;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.zzming.chess.Main;
import com.zzming.chess.entity.Player;
import com.zzming.chess.message.LoginMessage;
import com.zzming.chess.util.Get;
@Component("login")
public class LoginOrEnrollActivity {
    private static final int LOGIN = 1;
    private static final int ENROLL = LOGIN + 1;

    public LoginOrEnrollActivity() {
    }

    public void doAction() throws IOException {
        System.out.println(LOGIN+".��¼");
        System.out.println(ENROLL+".ע��");
        int n = Get.getInt();
        switch (n) {
        case LOGIN:
            login();
            break;
        case ENROLL:
            enroll();
            break;
        default:
            System.out.println("��Ų���ȷ");
        }
    }

    private void enroll() throws IOException {
        Player player = new Player();
        System.out.println("�������˺�");
        player.setCode(Get.getString());
        System.out.println("����������");
        player.setPwd(Get.getString());
        System.out.println("�������ǳ�");
        player.setName(Get.getString());
        System.out.println("�������ֻ���");
        player.setTelephone(Get.getTelephone());
        System.out.println("������Email");
        player.setEmail(Get.getEmail());
        Main.sendObject(player);
    }

    private void login() throws IOException {
        System.out.println("�˺�");
        String code = Get.getString();
        System.out.println("����");
        String pwd = Get.getString();
        Main.sendObject(new LoginMessage(code, pwd));
    }
}
