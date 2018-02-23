package zzm.chess;

import java.awt.image.BufferedImage;

public class piece  {
    protected int row;
    protected int col;
    protected int color;
    public BufferedImage image;

    public boolean select(int x, int y) {
        return row == x && col == y;
    }

    public void moveto(int i, int j) {
        row = i;
        col = j;
        Game.flag=!Game.flag;
    }


    public boolean run(int i, int j) {
        return false;
    }

    public boolean eat(int i, int j) {
        return false;
    }
}