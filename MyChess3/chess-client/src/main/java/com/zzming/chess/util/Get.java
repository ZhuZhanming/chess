package com.zzming.chess.util;

import java.util.Scanner;

public class Get {
    private static Scanner scan = new Scanner(System.in);

    /**
     * ���int
     */
    public static int getInt() {
        int i = -1;
        String str = scan.nextLine();
        try {
            i = Integer.parseInt(str);
        } catch (Exception e) {
            System.out.println("�������,����������");
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
            System.out.println("�������,����������");
        }
        return str;
    }

    /**
     * ����޿ո�,����>=3�ַ���(ֻ����Ӣ�Ļ����ֺ��»���)
     */
    public static String getString() {
        String str = null;
        while (true) {
            str = scan.nextLine();
            if (str.matches("\\w{3,}")) {
                break;
            }
            System.out.println("�������,����������");
        }
        return str;
    }

    /**
     * 1��ͷ��11Ϊ����
     */
    public static String getTelephone() {
        String tel = null;
        do {
            tel = scan.nextLine();
            if (tel.matches("1\\d{10}")) {
                break;
            }
            System.out.println("�������,����������");
        } while (true);
        return tel;
    }

    /**
     * �����ȷ��ʽ������
     */
    public static String getEmail() {
        String email = null;
        do {
            email = scan.nextLine();
            if (email.matches(".+@.+(\\..+)+")) {
                break;
            }
            System.out.println("�������,����������");
        } while (true);
        return email;
    }
}
