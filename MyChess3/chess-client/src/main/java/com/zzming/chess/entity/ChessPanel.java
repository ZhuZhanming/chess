package com.zzming.chess.entity;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.zzming.chess.entity.piece.Piece;

/**
 * 画也是一个线程Thread[AWT-EventQueue-0,6,main],不能直接使用main方法Thread[main,5,main]
 * 中绑定的Step----Graphics在JFrame类中
 */
@Component("panel")
@Lazy(true)
public class ChessPanel extends JPanel {

    private static final long serialVersionUID = 7833838190896189863L;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 550;
    private JFrame frame;
    private Step step;
    private static Map<String, BufferedImage> imageMap = new HashMap<String, BufferedImage>();;
    static {
        String[] imgNames = { "C2", "C1", "M2", "M1", "X2", "X1", "S2", "S1", "J2", "J1", "P2", "P1", "B2", "B1", "BG",
                "SL" };
        for (String image : imgNames) {
            try {
                imageMap.put(image, ImageIO.read(ChessPanel.class.getClassLoader()
                        .getResourceAsStream("photo" + File.separator + image + ".png")));
            } catch (IOException e) {
                System.out.println("图片:" + image + "失败");
            }
        }
    }

    @Autowired
    public ChessPanel(@Qualifier("step") Step step, @Value("#{config.name}") String name) {
        this.step = step;
        frame = new JFrame(name);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(this);
        // 总是在最上方
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);// 自动调用paint(g)
    }

    public void paint(Graphics g) {
        int state = Step.parseState(step.toString());
        if (state == 0)
            System.out.println(state);
        paintBackground(g, state);
        if (state != Step.START) {
            paintDirection(g);
            paintAllPiece(g);
        }
        paintState(g, state);
    }

    /**
     * 游戏状态
     */
    private void paintState(Graphics g, int state) {
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        switch (state) {
        case Step.START:
            g.setColor(new Color(0xff0000));
            g.drawString("点击开始游戏", WIDTH / 2 - 110, HEIGHT / 2 - 70);
            break;
        case Step.DANGER:
            g.setColor(new Color(0xff0000));
            g.drawString("将军", WIDTH / 2 - 185, HEIGHT / 2 - 20);
            break;
        case Step.GAMEOVER:
            g.setColor(new Color(0x000000));
            g.drawString("将军,游戏结束", WIDTH / 2 - 120, HEIGHT / 2 - 70);
            break;
        }
    }

    /**
     * 所有棋子和提示方块
     */
    private void paintAllPiece(Graphics g) {
        String[] arrs = Step.parsePieceStrArr(step.toString());
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
     * 提示信息谁走
     */
    private void paintDirection(Graphics g) {
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        if (Piece.RED == Step.parseRedMove(step.toString())) {
            g.setColor(new Color(0xff0000));
            g.drawString("红方走", WIDTH / 2 - 85, HEIGHT / 2 - 20);
        } else {
            g.setColor(new Color(0x000000));
            g.drawString("黑方走", WIDTH / 2 - 85, HEIGHT / 2 - 20);
        }
    }

    /**
     * 白版背景和线条
     */
    private void paintBackground(Graphics g, int state) {
        g.drawImage(imageMap.get("BG"), 0, 0, null);
        if (state != Step.START) {
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
    }

    public static void main(String[] args) {
        // new ChessPanel();
    }
}
