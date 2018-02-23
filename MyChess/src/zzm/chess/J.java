package zzm.chess;

public class J extends piece {

	public J(int row, int col, int color) {
		this.row = row;
		this.col = col;
		this.image = Game.j;
		this.color = color;
		if (color == 1)
			this.image = Game.j_black;
	}

	@Override
	public boolean run(int i, int j) {
		if (j >= 3 && j <= 5) {
			if ((Math.abs(i - row) == 1 && j == col) || (Math.abs(j - col) == 1 && i == row)) {
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
		for (int k = 0; k < Game.pieceBoss.length; k++) {
			piece p = new piece();
			p = Game.pieceBoss[k];
			if (p.color != this.color && p.col == this.col) {
				boolean flag = true;
				if (i < row) {
					for (int a = i + 1; a < row - 1; a++)
						if (Game.flags[a][this.col] != 0) {
							flag = false;
							break;
						}
				} else {
					for (int a = row + 1; a < i-1; a++)
						if (Game.flags[a][this.col] != 0) {
							flag = false;
							break;
						}
				}
				if(flag)
					return flag;
			}
		}

		return this.run(i, j);
	}
}
