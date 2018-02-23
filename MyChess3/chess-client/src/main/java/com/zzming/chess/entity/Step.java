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
     * 游戏状态:开始状态
     */
    public transient static final int START = 0;
    /**
     * 游戏状态:进行
     */
    public transient static final int RUNNING = START + 1;
    /**
     * 游戏状态:将军
     */
    public transient static final int DANGER = RUNNING + 1;
    /**
     * 游戏状态:结束
     */
    public transient static final int GAMEOVER = DANGER + 1;
    /**
     * 通过字符串解析出map集合
     */
    public transient static Map<String, Piece> pieceMap;
    /**
     * 棋盘上棋子的分布状态
     */
    private transient int[][] flags;
    /**
     * 初始棋盘字符串码
     */
    private transient final String initStr;
    /**
     * key:"/"十位表示行,"%"各位表示列 value:对应的棋子
     */
    private Map<Integer, Piece> map = new HashMap<Integer, Piece>();
    private boolean redMove;// 红方走判断
    private int state;
    private int x, y;// 记录提示方块所在位置
    private transient Piece nowPiece;// 选中的棋子
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
     * 判断选择棋子是否合法. 是就更新选择参数(要移动的棋子,和方块位置)
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
     * 根据棋子分布更新flags数据
     */
    public void upDateFlags() {
        flags = new int[10][9];// 初始值全为零
        Set<Entry<Integer, Piece>> c = map.entrySet();
        for (Entry<Integer, Piece> e : c) {
            int key = e.getKey();
            flags[key / 10][key % 10] = e.getValue().getColor();
        }
    }

    /**
     * 移动前检查基本移动条件
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
     * 移动和吃棋子,合法这改变数据,并返回是否游戏结束和改变是否将军的状态
     * 
     * @param i
     *            要移动的行
     * @param j
     *            要移动的列
     * @return 返回是否游戏结束
     */
    public void move(int i, int j) throws IOException {
        upDateFlags();
        if (!checkMove(i, j)) {
            return;
        }
        // 记录是否能移动成功的开关
        boolean flag = false;
        if (flags[i][j] == 0) {
            // 移动
            flag = nowPiece.run(x, y, i, j);
        } else if (nowPiece instanceof J && map.get(i * 10 + j) instanceof J && betweenNumber(x, y, i, j) == 0) {
            // 老将见面
            flag = true;
        } else {
            // 正常吃棋子
            flag = nowPiece.eat(x, y, i, j);
        }
        if (flag) {
            moveAction(i * 10 + j);
        }
    }

    /**
     * 成功移动以后执行
     */
    private void moveAction(int aim) throws IOException {
        map.remove(x * 10 + y);
        if (map.get(aim) instanceof J) {
            // 游戏结束
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
     * 获得老将位置数组
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
     * 移动成功后判断是否能够将军
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
     * util:返回两位置间棋子个数(不在一条线上返回-1)
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
     * 解析出棋子的信息字符串数组
     */
    public static String[] parsePieceStrArr(String stepStr) {
        int index = stepStr.indexOf(",");
        String[] arrs = stepStr.substring(index + 1).split(",");
        return arrs;
    }

    /**
     * 解析出游戏状态 第二位
     */
    public static int parseState(String stepStr) {
//        System.out.println(stepStr);
        return Integer.parseInt(stepStr.substring(1, 2));
    }

    /**
     * 解析出redMove 第一位
     */
    public static int parseRedMove(String stepStr) {
        return Integer.parseInt(stepStr.substring(0, 1));
    }

    /**
     * 根据字符串给Step设置值,不改动原对象的指向
     */
    public void parseStepStr(String stepStr) {
        if (parseRedMove(stepStr) == Piece.RED) {
            this.redMove = true;
        } else {
            this.redMove = false;
        }
        this.state = parseState(stepStr);
        // 清除原有的数据
        this.map.clear();
        String[] arrs = parsePieceStrArr(stepStr);
        this.x = Integer.parseInt(arrs[0].substring(2, 3));
        this.y = Integer.parseInt(arrs[0].substring(3));
        for (int i = 1; i < arrs.length; i++) {
            this.map.put(Integer.parseInt(arrs[i].substring(2, 4)), pieceMap.get(arrs[i].substring(0, 2)));
        }
    }

    /**
     * 棋盘数据编码:第一位表示该谁走的颜色,第二位表示游戏状态(0:未开始,1:进行,2:将军,3:结束)
     * 后面每四位表示一个图片(前两位图片代码,后两位位置) ---无背景
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
            // 保证个位数显示两位
            sb.append(key / 10 + "" + key % 10);
        }
        return sb.toString();
    }

    /**
     * 测试棋盘的toString
     */
    public static void main(String[] args) throws Exception {
        // System.out.println(new Step());
    }
}
