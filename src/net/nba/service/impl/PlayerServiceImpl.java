package net.nba.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.nba.dao.BaseDao;
import net.nba.model.MatchInfo;
import net.nba.model.Player;
import net.nba.model.PlayerMatchInfo;
import net.nba.service.PlayerService;

@Service("playerService")
public class PlayerServiceImpl implements PlayerService {
	@Resource
	private BaseDao<Player> playerDao;
	
	@Resource 
	private BaseDao<PlayerMatchInfo> playerMatchInfoDao;

	@Override
	public List<Map<String, Object>> getSeasonPlayerRanks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getPlayerRanks(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getTeamPlayers(int teamId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player getPlayerInfo(int playerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getPlayerSeasonStatistics(int playerId) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
