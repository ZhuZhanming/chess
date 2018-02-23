package com.zzming.chess.thread;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import com.zzming.chess.Server;
import com.zzming.chess.dao.GameDao;
import com.zzming.chess.dao.GameDaoImpl;
import com.zzming.chess.dao.PlayerDao;
import com.zzming.chess.entity.Game;
import com.zzming.chess.entity.Player;
import com.zzming.chess.entity.StreamBag;
import com.zzming.chess.message.GameOverMessage;
import com.zzming.chess.message.PlayersMessage;
import com.zzming.chess.message.RequestMessage;
import com.zzming.chess.message.StepMessage;
import com.zzming.chess.message.SystemMessage;

public class IndexHandler {
	private PlayerDao playerDao;
	private GameDao gameDao;
	private StreamBag bag;

	public IndexHandler() throws IOException {
		this.playerDao = Server.ac.getBean("playerDao", PlayerDao.class);
		this.gameDao = new GameDaoImpl();
		
		this.bag = MainThread.tl.get();
	}

	public void action() throws ClassNotFoundException, IOException, Exception {
		Object msg = null;
		while ((msg = bag.readObject()) != null) {
			if (msg instanceof RequestMessage) {
				// 请求信息
				RequestMessage rm = (RequestMessage) msg;
				String info = rm.getRequest();
//				System.out.println(info);
				if ("findEnemy".equals(info)) {
					findEnemy();
				} else if ("info".equals(info)) {
					Player player = playerDao.findById(bag.getPlayerId());
					bag.writeObject(new SystemMessage(player.toString()));
				} else if (info.matches("@\\d{8,}")) {
					getEnemy(info);
				} else if ("allPlayers".equals(info)) {
					allPlayers();
				} else if ("exit".equals(info)) {
					Server.exit(bag.getPlayerId());
					bag.setPlayerId(null);
					bag.writeObject(new SystemMessage("退出成功"));
				}
			} else if (msg instanceof Player) {
				// 修改信息请求
				modify(msg);
			} else if (msg instanceof StepMessage) {
				// System.out.println("服务端--------接收到step");
				// String str = ((StepMessage)msg).getStepStr();
				// System.out.println(str);
				Game game = Server.games.get(bag.getPlayerId());
				game.send(msg);
			} else if (msg instanceof GameOverMessage) {
				// 胜利方发送结束信息
				Game game = Server.games.get(bag.getPlayerId());
				Server.games.remove(bag.getPlayerId());
				Server.games.remove(bag.getEnemyId());
				Server.availPlayers.put(bag.getPlayerId(), bag);
				Server.availPlayers.put(bag.getEnemyId(), Server.allPlayers.get(bag.getEnemyId()));
				// TODO:
				game.setWinId(bag.getPlayerId());
				game.setEndTime(new Timestamp(System.currentTimeMillis()));
				gameDao.save(game);
				System.out.println(bag.getPlayerId()+":"+bag.getEnemyId()+"游戏结束！");
			} else {
				// 发的不是请求信息--错误
				System.out.println(msg);
				throw new Exception();
			}
			/**
			 * 退出判断
			 */
			if (!Server.allPlayers.containsKey(bag.getPlayerId())) {
				break;
			}
		}
	}

	private void modify(Object msg) throws IOException {
		Player p = (Player) msg;
		playerDao.modify(p);
		bag.writeObject(new SystemMessage("修改成功"));
	}

	private void allPlayers() throws IOException {
		List<Player> list = playerDao.allPlayers();
		sendPlayers(list);
	}

	/**
	 * p的equals相等根据id判断的
	 */
	private void getEnemy(String info) throws IOException {
		String idStr = info.substring(1);
		bag.setEnemyId(Integer.parseInt(idStr));
		StreamBag enemyBag = Server.availPlayers.get(bag.getEnemyId());
		if (enemyBag == null || enemyBag.equals(bag)) {
			bag.writeObject(new SystemMessage("玩家不存在或未在线或正在忙碌中"));
		} else {
			bag.setEnemyId(enemyBag.getPlayerId());
			enemyBag.setEnemyId(bag.getPlayerId());
			Game game = new Game(bag, enemyBag);
			Server.availPlayers.remove(bag.getPlayerId());
			Server.availPlayers.remove(bag.getEnemyId());
			Server.games.put(bag.getPlayerId(), game);
			Server.games.put(enemyBag.getPlayerId(), game);
			game.start();
		}
	}

	private void findEnemy() throws IOException {
		Collection<Integer> c = Server.availPlayers.keySet();
		List<Player> list = formList(c);
		sendPlayers(list);
	}

	/**
	 * util,查找对应的list<player>集合
	 */
	private List<Player> formList(Collection<Integer> c) {
		List<Player> list = new ArrayList<Player>();
		Player p = null;
		for (Integer i : c) {
			p = playerDao.findById(i);
			list.add(p);
		}
		// 排序
		list.sort(new Comparator<Player>() {

			public int compare(Player o1, Player o2) {
				return o1.getId() - o2.getId();
			}
		});
		return list;
	}

	/**
	 * util,输出Players集合
	 */
	private void sendPlayers(List<Player> c) throws IOException {
		PlayersMessage players = new PlayersMessage(c);
		bag.writeObject(players);
	}

}
