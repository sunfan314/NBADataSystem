package net.nba.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.nba.dao.BaseDao;
import net.nba.dataSpider.TeamInfoSpider;
import net.nba.model.Team;
import net.nba.model.TeamSeasonRank;
import net.nba.service.TeamService;

@Service("teamService")
public class TeamServiceImpl implements TeamService {
	@Resource
	private BaseDao<Team> teamDao;
	
	@Resource
	private BaseDao<TeamSeasonRank> teamRankDao;

	@Resource
	private TeamInfoSpider teamInfoSpider;

	@Override
	public void updateTeamInfo() {
		// TODO Auto-generated method stub
		// 写入球队信息（如果已经写入则更新变化部分信息）
		List<Team> list = teamInfoSpider.getTeamInfoList();
		for (Team team : list) {
			teamDao.saveOrUpdate(team);
		}
	}
	
	@Override
	public void updateTeamSeasonRanks() {
		// TODO Auto-generated method stub
		//更新球队赛季排行信息
		List<TeamSeasonRank> list=teamInfoSpider.getTeamSeasonRanks();
		for (TeamSeasonRank rank : list) {
			teamRankDao.saveOrUpdate(rank);
		}
	}

	
	@Override
	public List<Team> getTeams() {
		// TODO Auto-generated method stub
		//获取球队信息列表
		return teamDao.find("from Team");
	}

	@Override
	public Team getTeamInfos(int teamId) {
		// TODO Auto-generated method stub
		//根据id获取某只球队的信息
		return teamDao.get(Team.class, teamId);
	}

	@Override
	public List<TeamSeasonRank> getTeamSeasonRanks() {
		// TODO Auto-generated method stub
		return teamRankDao.find("from TeamSeasonRank");
	}

	
	
	

}
