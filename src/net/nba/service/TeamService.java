package net.nba.service;

import java.util.List;
import java.util.Map;

import net.nba.model.Team;
import net.nba.model.TeamSeasonRank;

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
	 * @return	球队基本信息列表
	 */
	public List<Team> getTeams();
	
	/**
	 * @param teamId	球队id
	 * @return	某只球队基本信息
	 */
	public Team getTeamInfos(int teamId);

	/**
	 * @return	球队赛季排行信息
	 */
	public List<TeamSeasonRank> getTeamSeasonRanks();
//
//	public Map<String, Object> getTeamStatistics(int teamId);
//
//	public List<Map<String, Object>> getTeamVsStatistics(int teamId,
//			int vsTeamId);

	
	

}
