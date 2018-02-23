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
        System.out.println(LOGIN+".µ«¬º");
        System.out.println(ENROLL+".◊¢≤·");
        int n = Get.getInt();
        switch (n) {
        case LOGIN:
            login();
            break;
        case ENROLL:
            enroll();
            break;
        default:
            System.out.println("–Ú∫≈≤ª’˝»∑");
        }
    }

    private void enroll() throws IOException {
        Player player = new Player();
        System.out.println("«Î ‰»Î’À∫≈");
        player.setCode(Get.getString());
        System.out.println("«Î ‰»Î√‹¬Î");
        player.setPwd(Get.getString());
        System.out.println("«Î ‰»ÎÍ«≥∆");
        player.setName(Get.getString());
        System.out.println("«Î ‰»Î ÷ª˙∫≈");
        player.setTelephone(Get.getTelephone());
        System.out.println("«Î ‰»ÎEmail");
        player.setEmail(Get.getEmail());
        Main.sendObject(player);
    }

    private void login() throws IOException {
        System.out.println("’À∫≈");
        String code = Get.getString();
        System.out.println("√‹¬Î");
        String pwd = Get.getString();
        Main.sendObject(new LoginMessage(code, pwd));
    }
}
