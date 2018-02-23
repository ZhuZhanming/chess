package com.zzming.chess.entity.piece;

import com.zzming.chess.Main;

public class P extends Piece {

    private static final long serialVersionUID = 1323840607699242865L;

    public P(int color) {
        this.color = color;
    }

    @Override
    public boolean run(int row, int col, int i, int j) {
        if (Main.getStep().betweenNumber(row, col, i, j) == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean eat(int row, int col, int i, int j) {
        if (Main.getStep().betweenNumber(row, col, i, j) == 1) {
            return true;
        }
        return false;
    }
}
