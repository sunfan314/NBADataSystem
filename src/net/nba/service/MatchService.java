package net.nba.service;

import java.util.List;

import net.nba.model.Match;

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
	 * @return	最近50场比赛的记录列表
	 */
	public List<Match> getLatestMatchs();

	/**
	 * @param teamId
	 * @return	球队当前赛季所有比赛
	 */
	public List<Match> getTeamMatchs(int teamId);//获取某支球队当前赛季比赛信息

	

//	MatchInfo getMatchInfo(int matchId);

}
