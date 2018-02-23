package com.zzming.chess.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzming.chess.dao.PlayerDao;
import com.zzming.chess.entity.Player;
import com.zzming.chess.service.PlayerService;
import com.zzming.chess.service.exception.NameException;
import com.zzming.chess.service.exception.PasswordException;

@Service("playerService")
public class PlayerServiceImpl implements PlayerService {
    @Resource(name = "playerDao")
    private PlayerDao playerDao;

    public String checkCode(String code) {
        if (code == null || code.isEmpty()) {
            return "账号不能为空";
        }
        if (playerDao.findByCode(code) != null) {
            return "账号已存在";
        }
        if (!code.matches("\\w{3,}")) {
            return "账号格式不合法";
        }
        return "可以使用";
    }

    public Player login(String code, String pwd) {
        if (code == null || code.trim().isEmpty()) {
            throw new NameException("账号不能为空");
        }
        if (pwd == null || pwd.trim().isEmpty()) {
            throw new PasswordException("密码不能为空");
        }
        // 1.利用用户名查找用户信息
        Player player = playerDao.findByCode(code);
        if (player == null) {
            throw new NameException("用户名错误");
        }
        // 2.检验用户密码是否正确
        if (!pwd.equals(player.getPwd())) {
            throw new PasswordException("密码错误");
        }
        return player;
    }

    public void save(Player player) {
        if (playerDao.findByCode(player.getCode()) != null) {
            throw new NameException("用户已被占用");
        }
        if (!player.getPwd().matches("\\w{3,}")) {
            throw new PasswordException("密码格式不对");
        }
        String name = player.getName();
        if (name == null || name.trim().isEmpty()) {
            player.setName(player.getCode());
        }
        playerDao.save(player);
    }

    public Player getPlayer(String code) {
        if(code==null||code.isEmpty()){
            return null;
        }
        return playerDao.findByCode(code);
    }

    public void modify(Player player) {
        if(player.getCode()==null){
            throw new NameException("code为空");
        }
        playerDao.modifyInfo(player);
    }

}
