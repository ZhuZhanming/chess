package com.zzming.chess.message;

import java.io.Serializable;

public class RequestMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String request;
    
    public RequestMessage(String request) {
        super();
        this.request = request;
    }

    public String getRequest() {
        return request;
    }
}
