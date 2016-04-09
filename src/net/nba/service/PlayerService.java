package net.nba.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.nba.model.Player;

public interface PlayerService {
	
	public void updateTeamPlayers();//写入球队阵容信息
	
	public List<Player> getTeamPlayerList(int teamId);//获取球队阵容列表
	
	public List<Player> getPlayers();//获取所有球员列表

//	List<Map<String, Object>> getSeasonPlayerRanks();
//
//	List<Map<String, Object>> getPlayerRanks(Date date);
//
//	List<Map<String, Object>> getTeamPlayers(int teamId);
//
//	Player getPlayerInfo(int playerId);
//
//	Map<String, Object> getPlayerSeasonStatistics(int playerId);

}
