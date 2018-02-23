package com.zzming.chess.entity.piece;

import com.zzming.chess.Main;

public class X extends Piece {
    private static final long serialVersionUID = 1L;

    public X(int color) {
        this.color = color;
    }

    @Override
    public boolean run(int row, int col, int i, int j) {
        int[][] flags = Main.getStep().getFlags();
        if ((color == RED && i > 4) || (color == BLACK && i < 5)) {
            if (i - row == 2 && j - col == 2) {
                if (flags[row + 1][col + 1] == 0)
                    return true;
            }
            if (i - row == 2 && col - j == 2) {
                if (flags[row + 1][col - 1] == 0)
                    return true;
            }
            if (row - i == 2 && j - col == 2) {
                if (flags[row - 1][col + 1] == 0)
                    return true;
            }
            if (row - i == 2 && col - j == 2) {
                if (flags[row - 1][col - 1] == 0)
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean eat(int row, int col, int i, int j) {
        return this.run(row, col, i, j);
    }

}
