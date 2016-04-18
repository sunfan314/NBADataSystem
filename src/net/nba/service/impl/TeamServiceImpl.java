package net.nba.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.nba.dao.BaseDao;
import net.nba.dataSpider.TeamInfoSpider;
import net.nba.model.Player;
import net.nba.model.PlayerSeasonStatistics;
import net.nba.model.Team;
import net.nba.model.TeamSeasonRank;
import net.nba.service.TeamService;

/**
 * @author sunfan314
 *
 */
@Service("teamService")
public class TeamServiceImpl implements TeamService {
	@Resource
	private BaseDao<Team> teamDao;
	
	@Resource
	private BaseDao<Player> playerDao;
	
	@Resource
	private BaseDao<TeamSeasonRank> teamRankDao;
	
	@Resource
	private BaseDao<PlayerSeasonStatistics> playerSeasonStatisticsDao;

	@Resource
	private TeamInfoSpider teamInfoSpider;

	@Override
	public void updateTeamInfo() {
		// TODO Auto-generated method stub
		List<Team> list = teamInfoSpider.getTeamInfoList();
		for (Team team : list) {
			teamDao.saveOrUpdate(team);
		}
	}
	
	@Override
	public void updateTeamSeasonRanks() {
		// TODO Auto-generated method stub
		List<TeamSeasonRank> list=teamInfoSpider.getTeamSeasonRanks();
		for (TeamSeasonRank rank : list) {
			teamRankDao.saveOrUpdate(rank);
		}
	}

	
	@Override
	public List<Team> getTeams() {
		// TODO Auto-generated method stub
		return teamDao.find("from Team");
	}

	@Override
	public Team getTeamInfos(int teamId) {
		// TODO Auto-generated method stub
		return teamDao.get(Team.class, teamId);
	}
	
	@Override
	public List<Player> getTeamPlayerList(int teamId) {
		// TODO Auto-generated method stub
		return playerDao.find("from Player where teamId = ?",teamId);
	}

	@Override
	public List<TeamSeasonRank> getTeamSeasonRanks() {
		// TODO Auto-generated method stub
		return teamRankDao.find("from TeamSeasonRank");
	}

	@Override
	public List<PlayerSeasonStatistics> getTeamPlayerStatistics(int teamId) {
		// TODO Auto-generated method stub
		List<PlayerSeasonStatistics> list=new ArrayList<PlayerSeasonStatistics>();
		List<Player> playerList=getTeamPlayerList(teamId);
		for (Player player : playerList) {
			list.add(playerSeasonStatisticsDao.get(PlayerSeasonStatistics.class, player.getId()));
		}
		return list;
	}
	

}
