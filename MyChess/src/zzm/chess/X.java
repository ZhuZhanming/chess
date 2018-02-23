package zzm.chess;

public class X extends piece  {

    public X(int row, int col, int color) {
        this.row = row;
        this.col = col;
        this.image = Game.x;
        this.color = color;
        if (color == 1)
            this.image = Game.x_black;
    }

    @Override
    public boolean run(int i, int j) {
        if ((color == 2 && i > 4) || (color == 1 && i < 5)) {
            if (i - row == 2 && j - col == 2) {
                if (Game.flags[row + 1][col + 1] == 0)
                    return true;
            }
            if (i - row == 2 && col - j == 2) {
                if (Game.flags[row + 1][col - 1] == 0)
                    return true;
            }
            if (row - i == 2 && j - col == 2) {
                if (Game.flags[row - 1][col + 1] == 0)
                    return true;
            }
            if (row - i == 2 && col - j == 2) {
                if (Game.flags[row - 1][col - 1] == 0)
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean eat(int i, int j) {
        return this.run(i, j);
    }

}
