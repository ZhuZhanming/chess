package com.zzming.chess.service;

import java.util.List;
import java.util.Map;

import com.zzming.chess.entity.Player;

public interface PlayerService {
    String checkCode(String code);

    Player login(String code, String pwd);

    void save(Player player);
    
    Player getPlayer(String code);
    
    void modify(Player player);
    
    void setPwd(String code,String oldPwd,String newPwd);
    
    List<Map<String,Object>> findGame(String code,Integer status,Integer color,String begin,String end);
}
