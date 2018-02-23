package com.zzming.chess.entity.piece;

import com.zzming.chess.Main;

public class C extends Piece {
    private static final long serialVersionUID = 1L;

    public C(int color) {
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
        return this.run(row, col, i, j);
    }
}