package com.zzm.chess.peice;

import com.zzm.chess.game.Game;

public class M extends Piece  {

	public M(int color) {
//        this.row = row;
//        this.col = col;
        this.color = color;
    }

    @Override
    public boolean run(int row,int col,int i, int j) {
        int flags[][] = Game.tl.get().getStep().getFlags();
        if (i - row == 2 && Math.abs(j - col) == 1) {
            if (flags[row + 1][col] == 0)
                return true;
        }
        if (row - i == 2 && Math.abs(j - col) == 1) {
            if (flags[row - 1][col] == 0)
                return true;
        }
        if (j - col == 2 && Math.abs(row - i) == 1) {
            if (flags[row][col + 1] == 0)
                return true;
        }
        if (col - j == 2 && Math.abs(row - i) == 1) {
            if (flags[row][col - 1] == 0)
                return true;
        }
        return false;
    }

    @Override
    public boolean eat(int row,int col,int i, int j) {
        return this.run(row,col,i, j);
    }

}
