package com.zzming.chess.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;
/**
 * ��ҵ�ʵ����
 */
@Component("player")
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String code;
    private String pwd;
    private String name;
    private Integer win;
    private Integer lose;
    private String telephone;
    private String email;
    private Timestamp enrolldate;

    public Player() {

    }

    public Player(Integer id) {
        this.id = id;
    }

    public Player(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * ָ��һ��,�ͻ�������
     */
    public Player(Player p) {
        this.id = p.id;
        this.code = p.code;
        this.pwd = p.pwd;
        this.name = p.name;
        this.win = p.win;
        this.lose = p.lose;
        this.telephone = p.telephone;
        this.email = p.email;
        this.enrolldate = p.enrolldate;
    }
    /**
     * �ڲ��ı�ԭ������ָ��������,����p,�ͻ�������
     */
    public void copyPlayer(Player p){
        this.id = p.id;
        this.code = p.code;
        this.pwd = p.pwd;
        this.name = p.name;
        this.win = p.win;
        this.lose = p.lose;
        this.telephone = p.telephone;
        this.email = p.email;
        this.enrolldate = p.enrolldate;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getLose() {
        return lose;
    }

    public void setLose(Integer lose) {
        this.lose = lose;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getEnrolldate() {
        return enrolldate;
    }

    public void setEnrolldate(Timestamp enrolldate) {
        this.enrolldate = enrolldate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "id=" + id + ", code=" + code + ", pwd=" + pwd + ", name=" + name + ", win=" + win + ", lose="
                + lose + ", telephone=" + telephone + ", email=" + email + ", enrolldate=" + enrolldate;
    }

}
