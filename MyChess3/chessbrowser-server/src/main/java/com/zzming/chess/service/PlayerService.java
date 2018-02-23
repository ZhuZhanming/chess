package com.zzming.chess.service;

import com.zzming.chess.entity.Player;

public interface PlayerService {
    String checkCode(String code);

    Player login(String code, String pwd);

    void save(Player player);
    
    Player getPlayer(String code);
    
    void modify(Player player);
}
