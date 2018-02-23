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
            return "�˺Ų���Ϊ��";
        }
        if (playerDao.findByCode(code) != null) {
            return "�˺��Ѵ���";
        }
        if (!code.matches("\\w{3,}")) {
            return "�˺Ÿ�ʽ���Ϸ�";
        }
        return "����ʹ��";
    }

    public Player login(String code, String pwd) {
        if (code == null || code.trim().isEmpty()) {
            throw new NameException("�˺Ų���Ϊ��");
        }
        if (pwd == null || pwd.trim().isEmpty()) {
            throw new PasswordException("���벻��Ϊ��");
        }
        // 1.�����û��������û���Ϣ
        Player player = playerDao.findByCode(code);
        if (player == null) {
            throw new NameException("�û�������");
        }
        // 2.�����û������Ƿ���ȷ
        if (!pwd.equals(player.getPwd())) {
            throw new PasswordException("�������");
        }
        return player;
    }

    public void save(Player player) {
        if (playerDao.findByCode(player.getCode()) != null) {
            throw new NameException("�û��ѱ�ռ��");
        }
        if (!player.getPwd().matches("\\w{3,}")) {
            throw new PasswordException("�����ʽ����");
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
            throw new NameException("codeΪ��");
        }
        playerDao.modifyInfo(player);
    }

}
