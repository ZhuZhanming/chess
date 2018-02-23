package com.zzming.chess.entity.piece;

import java.io.Serializable;

public class Piece implements Serializable {
    private static final long serialVersionUID = 1L;
    public transient static final int RED = 2;
    public transient static final int BLACK = 1;
    protected int color;

    public int getColor() {
        return color;
    }

    public boolean run(int row, int col, int i, int j) {
        return false;
    }

    public boolean eat(int row, int col, int i, int j) {
        return false;
    }
    /**
     * 重写toString()方法,输出对应棋子的字母+颜色序列
     */
    public String toString() {
        String str = getClass().getName();
        return str.substring(str.lastIndexOf(".")+1) + color;
    }
}