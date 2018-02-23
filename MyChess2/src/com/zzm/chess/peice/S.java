package com.zzm.chess.peice;

import com.zzm.chess.game.Game;

public class S extends Piece  {

	public S(int color) {
        this.color = color;
    }

    @Override
    public boolean run(int row,int col,int i, int j) {
        if (j >= 3 && j <= 5) {
            if (Math.abs(i - row) == 1 && Math.abs(j - col) == 1) {
                if (color == Game.RED && i >= 7)
                    return true;
                if (color == Game.BLACK && i <= 2)
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean eat(int row,int col,int i, int j) {
        return this.run(row,col,i, j);
    }

}
