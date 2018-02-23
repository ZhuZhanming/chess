package com.zzming.chess.dao;

import org.springframework.stereotype.Repository;

import com.zzming.chess.entity.Player;
@Repository("playerDao")
public interface PlayerDao {
    Player findByCode(String code);
    void save(Player player);
    void modifyInfo(Player player);
}
