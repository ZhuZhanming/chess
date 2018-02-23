package com.zzming.chess.entity;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.zzming.chess.Main;
import com.zzming.chess.entity.piece.B;
import com.zzming.chess.entity.piece.C;
import com.zzming.chess.entity.piece.J;
import com.zzming.chess.entity.piece.M;
import com.zzming.chess.entity.piece.P;
import com.zzming.chess.entity.piece.Piece;
import com.zzming.chess.entity.piece.S;
import com.zzming.chess.entity.piece.X;
import com.zzming.chess.message.GameOverMessage;
import com.zzming.chess.message.StepMessage;

@Controller("step")
@Lazy(true)
public class Step implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * ��Ϸ״̬:��ʼ״̬
     */
    public transient static final int START = 0;
    /**
     * ��Ϸ״̬:����
     */
    public transient static final int RUNNING = START + 1;
    /**
     * ��Ϸ״̬:����
     */
    public transient static final int DANGER = RUNNING + 1;
    /**
     * ��Ϸ״̬:����
     */
    public transient static final int GAMEOVER = DANGER + 1;
    /**
     * ͨ���ַ���������map����
     */
    public transient static Map<String, Piece> pieceMap;
    /**
     * ���������ӵķֲ�״̬
     */
    private transient int[][] flags;
    /**
     * ��ʼ�����ַ�����
     */
    private transient final String initStr;
    /**
     * key:"/"ʮλ��ʾ��,"%"��λ��ʾ�� value:��Ӧ������
     */
    private Map<Integer, Piece> map = new HashMap<Integer, Piece>();
    private boolean redMove;// �췽���ж�
    private int state;
    private int x, y;// ��¼��ʾ��������λ��
    private transient Piece nowPiece;// ѡ�е�����
    static {
        pieceMap = new HashMap<String, Piece>();
        pieceMap.put("C1", new C(Piece.BLACK));
        pieceMap.put("C2", new C(Piece.RED));
        pieceMap.put("M1", new M(Piece.BLACK));
        pieceMap.put("M2", new M(Piece.RED));
        pieceMap.put("X1", new X(Piece.BLACK));
        pieceMap.put("X2", new X(Piece.RED));
        pieceMap.put("S1", new S(Piece.BLACK));
        pieceMap.put("S2", new S(Piece.RED));
        pieceMap.put("J1", new J(Piece.BLACK));
        pieceMap.put("J2", new J(Piece.RED));
        pieceMap.put("P1", new P(Piece.BLACK));
        pieceMap.put("P2", new P(Piece.RED));
        pieceMap.put("B1", new B(Piece.BLACK));
        pieceMap.put("B2", new B(Piece.RED));
    }

    @Autowired
    public Step(@Value("#{config.step}") String stepStr) {
        this.parseStepStr(stepStr);
        this.initStr = stepStr;
    }
    
    public String getInitStr() {
        return initStr;
    }

    public int[][] getFlags() {
        return flags;
    }

    public boolean isRedMove() {
        return redMove;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    /**
     * �ж�ѡ�������Ƿ�Ϸ�. �Ǿ͸���ѡ�����(Ҫ�ƶ�������,�ͷ���λ��)
     */
    public void selectPiece(int x, int y) {
        int str = x * 10 + y;
        if (map.get(str) != null) {
            boolean flag = false;
            Piece p = map.get(str);
            // +
            if (redMove && p.getColor() == Piece.RED && Main.isRed()) {
                flag = true;
            }
            if (!redMove && p.getColor() == Piece.BLACK && !Main.isRed()) {
                flag = true;
            }
            if (flag) {
                nowPiece = p;
                this.x = x;
                this.y = y;
                Main.getPanel().repaint();
            }
        }
    }

    /**
     * �������ӷֲ�����flags����
     */
    public void upDateFlags() {
        flags = new int[10][9];// ��ʼֵȫΪ��
        Set<Entry<Integer, Piece>> c = map.entrySet();
        for (Entry<Integer, Piece> e : c) {
            int key = e.getKey();
            flags[key / 10][key % 10] = e.getValue().getColor();
        }
    }

    /**
     * �ƶ�ǰ�������ƶ�����
     */
    private boolean checkMove(int i, int j) {
        if (nowPiece == null) {
            return false;
        }
        // +
        if (nowPiece.getColor() != Main.getColor()) {
            return false;
        }
        if (nowPiece.getColor() == Piece.RED && !redMove) {
            return false;
        }
        if (nowPiece.getColor() == Piece.BLACK && redMove) {
            return false;
        }
        if (flags[i][j] == nowPiece.getColor()) {
            return false;
        }
        return true;
    }

    /**
     * �ƶ��ͳ�����,�Ϸ���ı�����,�������Ƿ���Ϸ�����͸ı��Ƿ񽫾���״̬
     * 
     * @param i
     *            Ҫ�ƶ�����
     * @param j
     *            Ҫ�ƶ�����
     * @return �����Ƿ���Ϸ����
     */
    public void move(int i, int j) throws IOException {
        upDateFlags();
        if (!checkMove(i, j)) {
            return;
        }
        // ��¼�Ƿ����ƶ��ɹ��Ŀ���
        boolean flag = false;
        if (flags[i][j] == 0) {
            // �ƶ�
            flag = nowPiece.run(x, y, i, j);
        } else if (nowPiece instanceof J && map.get(i * 10 + j) instanceof J && betweenNumber(x, y, i, j) == 0) {
            // �Ͻ�����
            flag = true;
        } else {
            // ����������
            flag = nowPiece.eat(x, y, i, j);
        }
        if (flag) {
            moveAction(i * 10 + j);
        }
    }

    /**
     * �ɹ��ƶ��Ժ�ִ��
     */
    private void moveAction(int aim) throws IOException {
        map.remove(x * 10 + y);
        if (map.get(aim) instanceof J) {
            // ��Ϸ����
            map.put(aim, nowPiece);
            state = GAMEOVER;
        } else {
            map.put(aim, nowPiece);
            redMove = !redMove;
            if (willOver())
                this.state = Step.DANGER;
        }
        Main.sendObject(new StepMessage(this.toString()));
        if(state == GAMEOVER){
            Main.sendObject(new GameOverMessage());
        }
    }

    /**
     * ����Ͻ�λ������
     */
    public int[] Js() {
        int[] js = new int[2];
        Set<Integer> c = map.keySet();
        int i = 0;
        for (Integer n : c) {
            if (map.get(n) instanceof J) {
                js[i++] = n;
            }
            if (i == 2) {
                break;
            }
        }
        return js;
    }

    /**
     * �ƶ��ɹ����ж��Ƿ��ܹ�����
     */
    public boolean willOver() {
        upDateFlags();
        int[] js = Js();
        for (int i = 0; i < js.length; i++) {
            for (Entry<Integer, Piece> p : map.entrySet()) {
                if (p.getValue().getColor() != map.get(js[i]).getColor()) {
                    int sit = p.getKey();
                    if (p.getValue().eat(sit / 10, sit % 10, js[i] / 10, js[i] % 10)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * util:������λ�ü����Ӹ���(����һ�����Ϸ���-1)
     */
    public int betweenNumber(int row, int col, int i, int j) {
        if (row != i && col != j) {
            return -1;
        }
        int max, min, index = 0;
        if (row == i) {
            max = col > j ? col : j;
            min = col > j ? j : col;
            for (min = min + 1; min < max; min++) {
                if (flags[row][min] != 0)
                    index++;
            }
        } else {
            max = row > i ? row : i;
            min = row > i ? i : row;
            for (min = min + 1; min < max; min++) {
                if (flags[min][col] != 0) {
                    index++;
                }
            }
        }
        return index;
    }

    /**
     * ���������ӵ���Ϣ�ַ�������
     */
    public static String[] parsePieceStrArr(String stepStr) {
        int index = stepStr.indexOf(",");
        String[] arrs = stepStr.substring(index + 1).split(",");
        return arrs;
    }

    /**
     * ��������Ϸ״̬ �ڶ�λ
     */
    public static int parseState(String stepStr) {
//        System.out.println(stepStr);
        return Integer.parseInt(stepStr.substring(1, 2));
    }

    /**
     * ������redMove ��һλ
     */
    public static int parseRedMove(String stepStr) {
        return Integer.parseInt(stepStr.substring(0, 1));
    }

    /**
     * �����ַ�����Step����ֵ,���Ķ�ԭ�����ָ��
     */
    public void parseStepStr(String stepStr) {
        if (parseRedMove(stepStr) == Piece.RED) {
            this.redMove = true;
        } else {
            this.redMove = false;
        }
        this.state = parseState(stepStr);
        // ���ԭ�е�����
        this.map.clear();
        String[] arrs = parsePieceStrArr(stepStr);
        this.x = Integer.parseInt(arrs[0].substring(2, 3));
        this.y = Integer.parseInt(arrs[0].substring(3));
        for (int i = 1; i < arrs.length; i++) {
            this.map.put(Integer.parseInt(arrs[i].substring(2, 4)), pieceMap.get(arrs[i].substring(0, 2)));
        }
    }

    /**
     * �������ݱ���:��һλ��ʾ��˭�ߵ���ɫ,�ڶ�λ��ʾ��Ϸ״̬(0:δ��ʼ,1:����,2:����,3:����)
     * ����ÿ��λ��ʾһ��ͼƬ(ǰ��λͼƬ����,����λλ��) ---�ޱ���
     * 21,SL64,C100,B264,M101,X102,B266,S103,J104,B268,S105,
     * X106,M107,P271,C108,P277,P121,C290,M291,P127,X292,S293,
     * J294,B130,S295,X296,B132,M297,C298,B134,B136,B138,B260,B262
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (redMove) {
            sb.append(Piece.RED);
        } else {
            sb.append(Piece.BLACK);
        }
        sb.append(state);
        sb.append(",SL" + x + y);
        Set<Integer> keys = map.keySet();
        for (Integer key : keys) {
            sb.append("," + map.get(key).toString());
            // ��֤��λ����ʾ��λ
            sb.append(key / 10 + "" + key % 10);
        }
        return sb.toString();
    }

    /**
     * �������̵�toString
     */
    public static void main(String[] args) throws Exception {
        // System.out.println(new Step());
    }
}
