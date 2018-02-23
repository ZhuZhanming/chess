package com.zzming.chess.entity.piece;

public class S extends Piece  {
    private static final long serialVersionUID = 1L;

    public S(int color) {
        this.color = color;
    }

    @Override
    public boolean run(int row,int col,int i, int j) {
        if (j >= 3 && j <= 5) {
            if (Math.abs(i - row) == 1 && Math.abs(j - col) == 1) {
                if (color == RED && i >= 7)
                    return true;
                if (color == BLACK && i <= 2)
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
