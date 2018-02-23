package com.zzm.chess.peice;

import com.zzm.chess.game.Game;

public class X extends Piece {
	
	public X(int color) {
		this.color = color;
	}

	@Override
	public boolean run(int row, int col, int i, int j) {
		int[][] flags = Game.tl.get().getStep().getFlags();
		if ((color == Game.RED && i > 4) || (color == Game.BLACK && i < 5)) {
			if (i - row == 2 && j - col == 2) {
				if (flags[row + 1][col + 1] == 0)
					return true;
			}
			if (i - row == 2 && col - j == 2) {
				if (flags[row + 1][col - 1] == 0)
					return true;
			}
			if (row - i == 2 && j - col == 2) {
				if (flags[row - 1][col + 1] == 0)
					return true;
			}
			if (row - i == 2 && col - j == 2) {
				if (flags[row - 1][col - 1] == 0)
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean eat(int row, int col, int i, int j) {
		return this.run(row, col, i, j);
	}

}
