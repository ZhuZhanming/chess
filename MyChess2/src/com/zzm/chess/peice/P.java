package com.zzm.chess.peice;

import com.zzm.chess.game.Game;

public class P extends Piece {

	public P(int color) {
        this.color = color;
    }

    @Override
    public boolean run(int row, int col, int i, int j) {
        if (Game.tl.get().getStep().betweenNumber(row, col, i, j) == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean eat(int row, int col, int i, int j) {
        if (Game.tl.get().getStep().betweenNumber(row, col, i, j) == 1) {
            return true;
        }
        return false;
    }
}
