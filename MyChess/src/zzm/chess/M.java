package zzm.chess;

public class M extends piece  {

    public M(int row, int col, int color) {
        this.row = row;
        this.col = col;
        this.image = Game.m;
        this.color = color;
        if (color == 1)
            this.image = Game.m_black;
    }

    @Override
    public boolean run(int i, int j) {
        if (i - row == 2 && Math.abs(j - col) == 1) {
            if (Game.flags[row + 1][col] == 0)
                return true;
        }
        if (row - i == 2 && Math.abs(j - col) == 1) {
            if (Game.flags[row - 1][col] == 0)
                return true;
        }
        if (j - col == 2 && Math.abs(row - i) == 1) {
            if (Game.flags[row][col + 1] == 0)
                return true;
        }
        if (col - j == 2 && Math.abs(row - i) == 1) {
            if (Game.flags[row][col - 1] == 0)
                return true;
        }
        return false;
    }

    @Override
    public boolean eat(int i, int j) {
        return this.run(i, j);
    }

}
