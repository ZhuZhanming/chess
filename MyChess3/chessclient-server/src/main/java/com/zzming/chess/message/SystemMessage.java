package com.zzming.chess.message;

import java.io.Serializable;

public class SystemMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String str;
    public SystemMessage(String str) {
        super();
        this.str = str;
    }
    public String getStr() {
        return str;
    }
    @Override
    public String toString() {
        return "ϵͳ:"+str;
    }
    
}
