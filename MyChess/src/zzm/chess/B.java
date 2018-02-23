package zzm.chess;

public class B extends piece {

    public B(int row, int col, int color) {
        this.row = row;
        this.col = col;
        this.image = Game.b;
        this.color = color;
        if (color == 1)
            this.image = Game.b_black;
    }

    @Override
    public boolean run(int i, int j) {
        if (color == 2) {
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
    public boolean eat(int i, int j) {
        return this.run(i, j);
    }

}
