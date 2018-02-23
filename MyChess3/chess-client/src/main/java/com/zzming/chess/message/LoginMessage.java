package com.zzming.chess.message;

import java.io.Serializable;

public class LoginMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String pwd;

    public LoginMessage(String code, String pwd) {
        super();
        this.code = code;
        this.pwd = pwd;
    }

    public String getCode() {
        return code;
    }

    public String getPwd() {
        return pwd;
    }

    @Override
    public String toString() {
        return "LoginMessage [code=" + code + ", pwd=" + pwd + "]";
    }

}
