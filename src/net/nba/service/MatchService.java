package net.nba.service;

import java.util.List;

import net.nba.model.Match;
import net.nba.model.PlayerMatchStatistics;
import net.nba.model.TeamMatchStatistics;

/**
 * @author sunfan314
 *
 */
public interface MatchService {
	
	/**
	 * 更新赛季比赛数据
	 */
	public void updateSeasonMatchList();
	
	/**
	 *更新球员比赛统计数据 
	 */
	public void updatePlayerMatchStatistics();
	
	/**
	 * 更新球队比赛统计数据 
	 */
	public void updateTeamMatchStatistics();
	
	/**
	 * @param year
	 * @param date	格式：03-23（月份与日期都以两位数表示）
	 * @return
	 */
	public List<Match> getMatchList(int year,String date);

	/**
	 * @return	最近50场比赛的记录列表
	 */
	public List<Match> getLatestMatchs();

	/**
	 * @param teamId
	 * @return	球队当前赛季所有比赛
	 */
	public List<Match> getTeamMatchs(int teamId);

	/**
	 * @param matchId
	 * @return	某场比赛球员数据统计信息
	 */
	public List<PlayerMatchStatistics> getPlayerMatchStatistics(int matchId);

	/**
	 * @param matchId
	 * @return	某场比赛的球队统计数据
	 */
	public List<TeamMatchStatistics> getTeamMatchStatistics(int matchId);



}
