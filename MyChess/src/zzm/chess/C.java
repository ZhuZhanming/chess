package zzm.chess;

public class C extends piece  {
    public C(int row, int col, int color) {
        this.row = row;
        this.col = col;
        this.image = Game.c;
        this.color = color;
        if (color == 1)
            this.image = Game.c_black;
    }

    @Override
    public boolean run(int i, int j) {
        boolean flag = false;
        if (i == row || j == col) 
            flag = true;
        if (row == i) {
            if (col > j) {
                for (int a = j + 1; a < col; a++)
                    if (Game.flags[row][a] != 0) {
                        flag = false;
                        break;
                    }
            } else {
                for (int a = col + 1; a < j; a++)
                    if (Game.flags[row][a] != 0) {
                        flag = false;
                        break;
                    }
            }
        } else if (col == j) {
            if (row > i) {
                for (int a = i + 1; a < row; a++)
                    if (Game.flags[a][col] != 0) {
                        flag = false;
                        break;
                    }
            } else {
                for (int a = row + 1; a < i; a++)
                    if (Game.flags[a][col] != 0) {
                        flag = false;
                        break;
                    }
            }
        }
        return flag;
    }

    @Override
    public boolean eat(int i, int j) {
        return this.run(i, j);
    }
}