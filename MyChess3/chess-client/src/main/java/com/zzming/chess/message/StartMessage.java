package com.zzming.chess.message;

import java.io.Serializable;
/**
 * 游戏开始信息
 */
public class StartMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    public transient static final int RED = 2;
    public transient static final int BLACK = 1;
    private int info;
    public StartMessage(int info){
        this.info = info;
    }
    public int getInfo() {
        return info;
    }
}
