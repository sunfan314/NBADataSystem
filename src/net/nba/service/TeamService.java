package net.nba.service;

import java.util.List;
import java.util.Map;

import net.nba.model.Player;
import net.nba.model.PlayerSeasonStatistics;
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
//
//	public Map<String, Object> getTeamStatistics(int teamId);
//
//	public List<Map<String, Object>> getTeamVsStatistics(int teamId,
//			int vsTeamId);

	
	

}
