package com.zzming.chess.entity;

import java.io.Serializable;

public class JsonResult implements Serializable {

    private static final long serialVersionUID = 812376774103405857L;
    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    /**
     * �����Ƿ�ɹ���״̬ 0:��ʾ�ɹ� 1������ֵ��ʾʧ��
     */
    private int state;
    /**
     * �ɹ�ʱ,���ص�����
     */
    private Object data;
    /**
     * �洢��ʾ��Ϣ
     */
    private String message;

    public JsonResult() {
    }

    public JsonResult(int state, Object data, String message) {
        this.state = state;
        this.data = data;
        this.message = message;
    }

    public JsonResult(Throwable e) {
        this(ERROR,"",e.getMessage());
    }
    public JsonResult(int state,Throwable e){
        this(state,"",e.getMessage());
    }
    public JsonResult(Object data){
        this(SUCCESS,data,"");
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "JsonResult [state=" + state + ", data=" + data + ", message=" + message + "]";
    }
    
}
