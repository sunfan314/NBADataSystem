package net.nba.service;

import java.util.List;
import java.util.Map;

import net.nba.model.Match;
import net.nba.model.Player;
import net.nba.model.PlayerAdvancedStatistics;
import net.nba.model.PlayerSeasonStatistics;
import net.nba.model.Team;
import net.nba.model.TeamMatchStatistics;
import net.nba.model.TeamSeasonRank;
import net.nba.model.TeamSeasonStatistics;

/**
 * @author sunfan314
 *
 */
public interface TeamService {

	/**
	 * 更新球队信息
	 */
	public void updateTeamInfo();
	
	/**
	 * 更新球队赛季排行数据（在每场比赛数据提交之后需要更新）
	 */
	public void updateTeamSeasonRanks();
	
	/**
	 * 更新球队赛季比赛数据统计
	 */
	public void updateTeamSeasonStatistics();
	
	/**
	 * @return	球队基本信息列表
	 */
	public List<Team> getTeams();
	
	/**
	 * @param teamId	球队id
	 * @return	某只球队基本信息
	 */
	public Team getTeamInfos(int teamId);
	
	/**
	 * @param teamId
	 * @return	球队阵容列表
	 */
	public List<Player> getTeamPlayerList(int teamId);

	/**
	 * @return	球队赛季排行信息
	 */
	public List<TeamSeasonRank> getTeamSeasonRanks();
	
	/**
	 * @param teamId
	 * @return	球队球员赛季数据统计
	 */
	public List<PlayerSeasonStatistics> getTeamPlayerStatistics(int teamId);
	

	/**
	 * @param teamId
	 * @return	球队球员进阶数据统计
	 */
	public List<PlayerAdvancedStatistics> getTeamPlayerAdvancedStatistics(int teamId);

	
	/**
	 * @param teamId
	 * @return	获得球队赛季比赛统计
	 */
	public List<TeamMatchStatistics> getTeamMatchStatistics(int teamId,String season);

	/**
	 * @param teamId
	 * @return	获取球队赛季统计数据
	 */
	public TeamSeasonStatistics getTeamSeasonStatistics(int teamId);
	
	/**
	 * @param teamId
	 * @param vsTeamId
	 * @return	球队交锋数据对比
	 */
	public List<TeamSeasonStatistics> getTeamVsStatistics(int teamId,int vsTeamId);

	/**
	 * @param teamId
	 * @param vsTeamId
	 * @return	球队交锋比赛信息
	 */
	public List<Match> getTeamVsMatchList(int teamId,int vsTeamId);

	
	

}
