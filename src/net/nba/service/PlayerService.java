package net.nba.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.nba.model.Player;
import net.nba.model.PlayerAdvancedStatistics;
import net.nba.model.PlayerDataRank;
import net.nba.model.PlayerInfoDetail;
import net.nba.model.PlayerMatchStatistics;
import net.nba.model.PlayerSeasonStatistics;

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
	 * 更新球员赛季数据统计
	 */
	public void updatePlayerSeasonStatistics();
	
	/**
	 * @usage 更新球员赛季进阶数据统计
	 */
	public void updatePlayerSeasonAdvancedStatistics();
	
	
	/**
	 * @return	所有球员列表
	 */
	public List<Player> getPlayers();
	
	/**
	 * @param playeId	球员id
	 * @return	球员详细信息
	 */
	public PlayerInfoDetail getPlayerInfoDetail(int playeId);
	
	/**
	 * @param playerId
	 * @param season	例：2015-2016
	 * @return	球员某赛季常规赛比赛数据统计
	 */
	public List<PlayerMatchStatistics> getPlayerMatchStatistics(int playerId,String season);
	
	/**
	 * @param	date	格式：年-月-日 例：2016-04-10(月份和日期都为两位，不足两位用0补足)
	 * @return	每日球员数据王（得分、篮板、助攻、抢断四类）
	 */
	public List<PlayerDataRank> getPlayerDataRanks(String date);

	/**
	 * @return	赛季球员数据王（得分、篮板、助攻、抢断四类）
	 */
	public List<PlayerDataRank> getPlayerSeasonRanks();


	/**
	 * @param playerId
	 * @return	球员赛季数据统计
	 */
	public PlayerSeasonStatistics getPlayerSeasonStatistics(int playerId);
	
	/**
	 * @return 所有球员赛季统计数据
	 */
	public List<PlayerSeasonStatistics> getTotalPlayerSeasonStatistics();

	
	/**
	 * @param playerId
	 * @return	球员赛季进阶数据统计
	 */
	public PlayerAdvancedStatistics getPlayerSeasonAdvancedStatistics(int playerId);
	
	/**
	 * @return	所有球员赛季进阶数据统计
	 */
	public List<PlayerAdvancedStatistics> getTotalPlayerSeasonAdvancedStatistics();

	/**
	 * @param playerId
	 * @return	球员赛季效率值列表
	 */
	public List<Integer> getPlayerSeasonPERValues(int playerId);

	



}
