package net.nba.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.nba.dao.BaseDao;
import net.nba.model.Conference;
import net.nba.model.MatchInfo;
import net.nba.model.Player;
import net.nba.model.Team;
import net.nba.model.TeamMatchInfo;
import net.nba.service.TeamService;

@Service("teamService")
public class TeamServiceImpl implements TeamService {
	
	@Resource
	private BaseDao<Team> teamDao;
	
	@Resource 
	private BaseDao<TeamMatchInfo> teamMatchInfoDao;
	

	@Override
	public List<Team> getTeams() {
		// TODO Auto-generated method stub
		return teamDao.find("from Team");
	}

	@Override
	public Team getTeamInfos(int teamId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getTeamRanks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getTeamStatistics(int teamId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getTeamVsStatistics(int teamId,
			int vsTeamId) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
