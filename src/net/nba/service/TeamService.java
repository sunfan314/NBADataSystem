package net.nba.service;

import java.util.List;
import java.util.Map;

import net.nba.model.Team;
import net.nba.model.TeamSeasonRank;

public interface TeamService {

	public void updateTeamInfo();//写入球队信息（如果已经写入则更新变化部分信息）
	
	public void updateTeamSeasonRanks();//更新球队赛季排行数据（在每场比赛数据提交之后需要更新）
	
	public List<Team> getTeams();//获取球队基本信息列表
	
	public Team getTeamInfos(int teamId);//获取某只球队基本信息

	public List<TeamSeasonRank> getTeamSeasonRanks();//获取球队赛季排行信息
//
//	public Map<String, Object> getTeamStatistics(int teamId);
//
//	public List<Map<String, Object>> getTeamVsStatistics(int teamId,
//			int vsTeamId);

	
	

}
