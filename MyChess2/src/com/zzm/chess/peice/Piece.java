package com.zzm.chess.peice;

import java.io.Serializable;

public class Piece implements Serializable {
    /**
     * дɵ��
     */
    private static final long serialVersionUID = 1L;
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
     * ��дtoString()����,�����Ӧ���ӵ���ĸ+��ɫ����
     */
    public String toString() {
        String str = getClass().getName();
        return str.substring(str.lastIndexOf(".")+1) + color;
    }
}