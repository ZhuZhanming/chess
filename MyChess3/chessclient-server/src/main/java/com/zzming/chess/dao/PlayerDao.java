package com.zzming.chess.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zzming.chess.annotation.MyBatisRepository;
import com.zzming.chess.entity.Player;
@Repository("playerDao")
@MyBatisRepository
public interface PlayerDao {
    void save(Player player);
    Player findByCode(String code);
    Player findById(int id);
    List<Player> allPlayers();
    void modify(Player player);
}
