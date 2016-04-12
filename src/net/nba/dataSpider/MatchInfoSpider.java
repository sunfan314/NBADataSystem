package net.nba.dataSpider;

import java.util.List;

import net.nba.model.Match;
import net.nba.model.PlayerMatchStatistics;
import net.nba.model.TeamMatchStatistics;

/**
 * @author sunfan314
 *
 */
public interface MatchInfoSpider {
	
	/**
	 * @return	赛季比赛列表
	 */
	public List<Match> getSeasonMatchList();
	
	
	/**
	 * @param matchIdList	需要获取球员比赛统计数据的比赛id列表
	 * @return	球员比赛统计数据比赛列表
	 */
	public List<PlayerMatchStatistics> getPlayerMatchStatistics(List<Integer> matchIdList);
	
	/**
	 * @param matchIdList	需要获取球员比赛统计数据的比赛id列表
	 * @return	球队比赛统计数据列表
	 */
	public List<TeamMatchStatistics> getTeamMatchStatistics(List<Integer> matchIdList);
	

	

}
