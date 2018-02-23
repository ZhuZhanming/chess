package com.zzm.chess.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * ��Ҳ��һ���߳�Thread[AWT-EventQueue-0,6,main],����ֱ��ʹ��main����Thread[main,5,main]
 * �а󶨵�Step----Graphics��JFrame����
 */
public class ChessPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 450;
    public static final int HEIGHT = 550;
    private JFrame frame;
    private String stepStr;
    private static Map<String, BufferedImage> imageMap = new HashMap<String, BufferedImage>();;
    static {
        String[] imgNames = { "C2", "C1", "M2", "M1", "X2", "X1", "S2", "S1", "J2", "J1", "P2", "P1", "B2", "B1", "BG",
                "SL" };
        for (String image : imgNames) {
            try {
                imageMap.put(image, ImageIO.read(new File("photo" + File.separator + image + ".png")));
            } catch (IOException e) {
                System.out.println("ͼƬ:" + image + "ʧ��");
            }
        }
    }

    public ChessPanel(String str) {
        frame = new JFrame(str);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(this);
        // ���������Ϸ�
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);// �Զ�����paint(g)
    }

    public void paint(Graphics g) {
        if (stepStr == null) {
            stepStr = "200";
        }
        paintBackground(g);
        if (Step.parseState(stepStr) != Step.START) {
            paintDirection(g);
            paintAllPiece(g);
        }
        paintState(g);
        if (Step.parseWillOver(stepStr) == 1) {
            paintDanger(g);
        }
    }

    /**
     * ����滭�������ַ���
     */
    public void paintStep(String stepStr) {
        this.stepStr = stepStr;
        repaint();
    }

    /**
     * ������Ϣ
     */
    private void paintDanger(Graphics g) {
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        g.setColor(new Color(0xff0000));
        g.drawString("����", WIDTH / 2 - 185, HEIGHT / 2 - 20);
    }

    /**
     * ��Ϸ״̬
     */
    private void paintState(Graphics g) {
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        switch (Step.parseState(stepStr)) {
        case Step.START:
            g.setColor(new Color(0xff0000));
            g.drawString("�����ʼ��Ϸ", WIDTH / 2 - 110, HEIGHT / 2 - 70);
            break;
        case Step.GAMEOVER:
            g.setColor(new Color(0x000000));
            g.drawString("����,��Ϸ����", WIDTH / 2 - 120, HEIGHT / 2 - 70);
            break;
        }
    }

    /**
     * �������Ӻ���ʾ����
     */
    private void paintAllPiece(Graphics g) {
        String[] arrs = Step.parsePieceStrArr(stepStr);
        String img = null;
        int x, y;
        for (int i = 0; i < arrs.length; i++) {
            img = arrs[i].substring(0, 2);
            x = Integer.parseInt(arrs[i].substring(2, 3));
            y = Integer.parseInt(arrs[i].substring(3));
            if (i != 0) {
                g.drawImage(imageMap.get(img), y * 50, x * 50, null);
            } else {
                g.drawImage(imageMap.get(img), y * 50 - 5, x * 50 - 5, null);
            }
        }
    }

    /**
     * ��ʾ��Ϣ˭��
     */
    private void paintDirection(Graphics g) {
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        if (Game.RED == Step.parseRedMove(stepStr)) {
            g.setColor(new Color(0xff0000));
            g.drawString("�췽��", WIDTH / 2 - 85, HEIGHT / 2 - 20);
        } else {
            g.setColor(new Color(0x000000));
            g.drawString("�ڷ���", WIDTH / 2 - 85, HEIGHT / 2 - 20);
        }
    }

    /**
     * �װ汳��������
     */
    private void paintBackground(Graphics g) {
        g.drawImage(imageMap.get("BG"), 0, 0, null);
        for (int i = 0; i < 11; i++)
            g.drawLine(20, 20 + 50 * i, 420, 20 + 50 * i);
        for (int i = 0; i < 9; i++)
            g.drawLine(20 + 50 * i, 20, 20 + 50 * i, 220);
        for (int i = 0; i < 9; i++)
            g.drawLine(20 + 50 * i, 270, 20 + 50 * i, 470);
        g.drawLine(20, 220, 20, 270);
        g.drawLine(420, 220, 420, 270);
        g.drawLine(170, 20, 270, 120);
        g.drawLine(270, 20, 170, 120);
        g.drawLine(170, 370, 270, 470);
        g.drawLine(270, 370, 170, 470);
    }

    public static void main(String[] args) {
        new ChessPanel("��Ҫ������");
    }
}
