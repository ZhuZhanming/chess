package com.zzming.chess.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.zzming.chess.entity.Condition;
import com.zzming.chess.entity.Player;

@Repository("playerDao")
public interface PlayerDao {
	Player findByCode(String code);

	void save(Player player);

	void modifyInfo(Player player);
	
	List<Map<String,Object>> find(Condition cond);
}
