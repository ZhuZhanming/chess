package com.zzming.chess.util;

import java.util.Scanner;

public class Get {
    private static Scanner scan = new Scanner(System.in);

    /**
     * 获得int
     */
    public static int getInt() {
        int i = -1;
        String str = scan.nextLine();
        try {
            i = Integer.parseInt(str);
        } catch (Exception e) {
            System.out.println("输入错误,请重新输入");
            i = getInt();
        }
        return i;
    }

    public static String getEnemyString() {
        String str = null;
        while (true) {
            str = scan.nextLine();
            if (str.matches("@\\d{8,}")) {
                break;
            }
            System.out.println("输入错误,请重新输入");
        }
        return str;
    }

    /**
     * 获得无空格,长度>=3字符串(只含有英文或数字和下划线)
     */
    public static String getString() {
        String str = null;
        while (true) {
            str = scan.nextLine();
            if (str.matches("\\w{3,}")) {
                break;
            }
            System.out.println("输入错误,请重新输入");
        }
        return str;
    }

    /**
     * 1开头的11为数字
     */
    public static String getTelephone() {
        String tel = null;
        do {
            tel = scan.nextLine();
            if (tel.matches("1\\d{10}")) {
                break;
            }
            System.out.println("输入错误,请重新输入");
        } while (true);
        return tel;
    }

    /**
     * 获得正确格式的邮箱
     */
    public static String getEmail() {
        String email = null;
        do {
            email = scan.nextLine();
            if (email.matches(".+@.+(\\..+)+")) {
                break;
            }
            System.out.println("输入错误,请重新输入");
        } while (true);
        return email;
    }
}
