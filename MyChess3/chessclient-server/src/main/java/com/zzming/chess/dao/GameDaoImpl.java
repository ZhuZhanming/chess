package com.zzming.chess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zzming.chess.Server;
import com.zzming.chess.entity.Game;

public class GameDaoImpl implements GameDao {
	private static DataSource ds;
	static {
		ds = Server.ac.getBean("ds", DataSource.class);
	}

	public void save(Game game) {
		Connection conn = null;
		try {
			conn = ds.getConnection();
			// 开启事务
			Integer redId = game.getRedId();
			Integer blackId = game.getBlackId();
			Integer winId = game.getWinId();
			conn.setAutoCommit(false);
			String sql = "INSERT INTO game(id,redId,blackId,winId,startTime,endTime) "
					+ " VALUES(seq_game_id.NEXTVAL,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql, new String[] { "id" });
			ps.setInt(1, redId);
			ps.setInt(2, blackId);
			ps.setInt(3, winId);
			ps.setTimestamp(4, game.getStartTime());
			ps.setTimestamp(5, game.getEndTime());
			ps.executeUpdate();
			// 获得主建
			Integer gameId = null;
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			gameId = rs.getInt(1);
			// 改player中的数据
			if (game.getWinId() == game.getRedId()) {
				updatePlayer(conn, redId, blackId);
			} else {
				updatePlayer(conn, blackId, redId);
			}
			System.out.println(gameId + "保存成功");
			conn.commit();
		} catch (Exception e) {
			try {
				e.printStackTrace();
				conn.rollback();
				System.out.println("事务失败");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void updatePlayer(Connection conn, Integer winId, Integer blackId) throws SQLException {
		String sql;
		PreparedStatement ps;
		sql = "UPDATE player SET win=win+1 WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, winId);
		ps.executeUpdate();
		sql = "UPDATE player SET lose=lose+1 WHERE id=?";
		ps = conn.prepareStatement(sql);
		ps.setInt(1, blackId);
		ps.executeUpdate();
	}

}
