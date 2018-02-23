package com.zzm.chess.peice;

import com.zzm.chess.game.Game;

public class C extends Piece  {

	public C(int color) {
        this.color = color;
    }

    @Override
    public boolean run(int row,int col,int i, int j) {
        if(Game.tl.get().getStep().betweenNumber(row, col, i, j)==0){
            return true;
        }
        return false;
    }

    @Override
    public boolean eat(int row,int col,int i, int j) {
        return this.run(row,col,i, j);
    }
}