package daotest;

import org.junit.Before;
import org.junit.Test;

import com.zzming.chess.dao.PlayerDao;
import com.zzming.chess.entity.Player;

import base.Testbase;

public class PlayerDaoTest extends Testbase {
    private PlayerDao playerDao;
    @Before
    public void init(){
        playerDao = super.getContext().getBean("playerDao",PlayerDao.class);
    }
    @Test
    public void findByCodeTest(){
        Player player = playerDao.findByCode("caocao");
        System.out.println(player);
    }
    @Test
    public void findByIdTest(){
    	Player player = playerDao.findById(10000020);
    	System.out.println(player);
    }
}
