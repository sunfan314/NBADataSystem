package net.nba.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.nba.model.Player;
import net.nba.model.PlayerInfoDetail;

/**
 * @author sunfan314
 *
 */
public interface PlayerService {
	
	/**
	 * 写入球队阵容信息
	 */
	public void updateTeamPlayers();
	
	/**
	 * 写入球员详细信息
	 */
	public void updatePlayerInfoDetail();
	
	/**
	 * @param teamId
	 * @return	球队阵容列表
	 */
	public List<Player> getTeamPlayerList(int teamId);
	
	/**
	 * @return	所有球员列表
	 */
	public List<Player> getPlayers();
	
	/**
	 * @param playeId	球员id
	 * @return	球员详细信息
	 */
	public PlayerInfoDetail getPlayerInfoDetail(int playeId);

//	List<Map<String, Object>> getSeasonPlayerRanks();
//
//	List<Map<String, Object>> getPlayerRanks(Date date);
//
//	List<Map<String, Object>> getTeamPlayers(int teamId);

//	Map<String, Object> getPlayerSeasonStatistics(int playerId);

}
