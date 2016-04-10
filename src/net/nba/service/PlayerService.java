package net.nba.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.nba.model.Player;
import net.nba.model.PlayerInfoDetail;

public interface PlayerService {
	
	public void updateTeamPlayers();//写入球队阵容信息
	
	public void updatePlayerInfoDetail();//写入球员详细信息
	
	public List<Player> getTeamPlayerList(int teamId);//获取球队阵容列表
	
	public List<Player> getPlayers();//获取所有球员列表
	
	public PlayerInfoDetail getPlayerInfoDetail(int playeId);//获取球员详细信息

//	List<Map<String, Object>> getSeasonPlayerRanks();
//
//	List<Map<String, Object>> getPlayerRanks(Date date);
//
//	List<Map<String, Object>> getTeamPlayers(int teamId);

//	Map<String, Object> getPlayerSeasonStatistics(int playerId);

}
