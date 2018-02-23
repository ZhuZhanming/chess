package com.zzm.chess.peice;

import com.zzm.chess.game.Game;

public class B extends Piece {

	public B(int color) {
        this.color = color;
    }

    @Override
    public boolean run(int row, int col, int i, int j) {
        if (color == Game.RED) {
            if (row - i == 1 && j == col)
                return true;
            if (row <= 4 && row == i && Math.abs(j - col) == 1) {
                return true;
            }
        } else {
            if (i - row == 1 && j == col)
                return true;
            if (row >= 5 && row == i && Math.abs(j - col) == 1)
                return true;
        }
        return false;
    }

    @Override
    public boolean eat(int row, int col, int i, int j) {
        return this.run(row, col, i, j);
    }
    /**
     * ����toString����
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(new B(2));
    }
}
