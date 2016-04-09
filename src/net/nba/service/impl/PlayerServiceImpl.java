package net.nba.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.nba.dao.BaseDao;
import net.nba.dataSpider.PlayerInfoSpider;
import net.nba.model.Player;
import net.nba.service.PlayerService;

@Service("playerService")
public class PlayerServiceImpl implements PlayerService {
	@Resource
	private BaseDao<Player> playerDao;
	
	@Resource
	private PlayerInfoSpider playerInfoSpider;

	@Override
	public void updateTeamPlayers() {
		// TODO Auto-generated method stub
		List<Player> players=playerDao.find("from Player");
		for (Player player : players) {//由于球员id自动生成无法追踪，所以更新数据前删除原有数据
			playerDao.delete(player);
		}
		List<Player> list=playerInfoSpider.getTeamPlayerList();
		for (Player player : list) {
			playerDao.save(player);
		}
	}

	@Override
	public List<Player> getTeamPlayerList(int teamId) {
		// TODO Auto-generated method stub
		return playerDao.find("from Player where teamId = ?",teamId);
	}
	

//	
//	@Resource 
//	private BaseDao<PlayerMatchInfo> playerMatchInfoDao;
//
//	@Override
//	public List<Map<String, Object>> getSeasonPlayerRanks() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Map<String, Object>> getPlayerRanks(Date date) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Map<String, Object>> getTeamPlayers(int teamId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Player getPlayerInfo(int playerId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Map<String, Object> getPlayerSeasonStatistics(int playerId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	

}
