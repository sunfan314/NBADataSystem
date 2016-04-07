package net.nba.service;

import java.util.List;
import java.util.Map;

import net.nba.model.Conference;
import net.nba.model.Team;

public interface TeamService {
	
	public List<Team> getTeams();
	
	public Team getTeamInfos(int teamId);

	public List<Map<String, Object>> getTeamRanks();

	public Map<String, Object> getTeamStatistics(int teamId);

	public List<Map<String, Object>> getTeamVsStatistics(int teamId,
			int vsTeamId);

	
	

}
