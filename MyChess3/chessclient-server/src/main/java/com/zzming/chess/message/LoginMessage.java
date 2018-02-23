package com.zzming.chess.message;

import java.io.Serializable;

public class LoginMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String pwd;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "LoginMessage [code=" + code + ", pwd=" + pwd + "]";
    }

}
