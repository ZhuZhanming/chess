package com.zzming.chess.entity.piece;

public class J extends Piece {
    private static final long serialVersionUID = 1L;

    public J(int color) {
        this.color = color;
    }

    @Override
    public boolean run(int row, int col, int i, int j) {
        if (j >= 3 && j <= 5) {
            if ((Math.abs(i - row) == 1 && j == col) || (Math.abs(j - col) == 1 && i == row)) {
                if (color == RED && i >= 7)
                    return true;
                if (color == BLACK && i <= 2)
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean eat(int row, int col, int i, int j) {
        // for (int k = 0; k < Game.pieceBoss.length; k++) {
        // Piece p = new Piece();
        // p = Game.pieceBoss[k];
        // if (p.color != this.color && p.col == this.col) {
        // boolean flag = true;
        // if (i < row) {
        // for (int a = i + 1; a < row - 1; a++)
        // if (Step.flags[a][this.col] != 0) {
        // flag = false;
        // break;
        // }
        // } else {
        // for (int a = row + 1; a < i-1; a++)
        // if (Step.flags[a][this.col] != 0) {
        // flag = false;
        // break;
        // }
        // }
        // if(flag)
        // return flag;
        // }
        // }

        return this.run(row, col, i, j);
    }

}
