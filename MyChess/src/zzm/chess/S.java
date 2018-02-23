package zzm.chess;

public class S extends piece  {

    public S(int row, int col, int color) {
        this.row = row;
        this.col = col;
        this.image = Game.s;
        this.color = color;
        if (color == 1)
            this.image = Game.s_black;
    }

    @Override
    public boolean run(int i, int j) {
        if (j >= 3 && j <= 5) {
            if (Math.abs(i - row) == 1 && Math.abs(j - col) == 1) {
                if (color == 2 && i >= 7)
                    return true;
                if (color == 1 && i <= 2)
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
