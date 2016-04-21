package net.nba.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.nba.dao.BaseDao;
import net.nba.data.spider.TeamInfoSpider;
import net.nba.model.Match;
import net.nba.model.Player;
import net.nba.model.PlayerAdvancedStatistics;
import net.nba.model.PlayerMatchStatistics;
import net.nba.model.PlayerSeasonStatistics;
import net.nba.model.Team;
import net.nba.model.TeamMatchStatistics;
import net.nba.model.TeamMatchStatisticsPK;
import net.nba.model.TeamSeasonRank;
import net.nba.model.TeamSeasonStatistics;
import net.nba.service.PlayerService;
import net.nba.service.TeamService;
import net.nba.util.CommonDataManager;
import net.nba.util.DoubleFormat;

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
	private BaseDao<Match> matchDao;
	
	@Resource
	private BaseDao<TeamSeasonRank> teamRankDao;
	
	@Resource
	private BaseDao<TeamMatchStatistics> teamMatchStatisticsDao;
	
	@Resource
	private BaseDao<TeamSeasonStatistics> teamSeasonStatisticsDao;
	
	@Resource
	private BaseDao<PlayerSeasonStatistics> playerSeasonStatisticsDao;
	
	@Resource
	private PlayerService playerService;

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
	public void updateTeamSeasonStatistics() {
		// TODO Auto-generated method stub
		List<TeamSeasonStatistics> list=new ArrayList<TeamSeasonStatistics>();
		List<Team> teams=teamDao.find("from Team");
		for (Team team : teams) {
			List<TeamMatchStatistics> dataList=getTeamMatchStatistics(team.getId(),CommonDataManager.SEASON );
			TeamSeasonStatistics statistics=new TeamSeasonStatistics(team,dataList,CommonDataManager.SEASON);
			list.add(statistics);
		}
		for (TeamSeasonStatistics tss : list) {
			teamSeasonStatisticsDao.saveOrUpdate(tss);
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
	
	@Override
	public List<PlayerAdvancedStatistics> getTeamPlayerAdvancedStatistics(
			int teamId) {
		// TODO Auto-generated method stub
		List<PlayerAdvancedStatistics> list=new ArrayList<PlayerAdvancedStatistics>();
		List<Player> playerList=getTeamPlayerList(teamId);
		for (Player player : playerList) {
			Team team=teamDao.get(Team.class,teamId);
			List<PlayerMatchStatistics> dataList=playerService.getPlayerMatchStatistics(player.getId(), CommonDataManager.SEASON);
			int totMatches=dataList.size();
			if(totMatches==0){
				continue;//跳过未上场球员
			}
			PlayerAdvancedStatistics statistics=new PlayerAdvancedStatistics(player, team, dataList, CommonDataManager.SEASON);
			list.add(statistics);
		}
		return list;
	}


	@Override
	public List<TeamMatchStatistics> getTeamMatchStatistics(int teamId,
			String season) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		params.add(teamId);
		params.add(teamId);
		params.add(season);
		List<Match> matches=matchDao.find("from Match where (vId = ? or hId = ?) and season = ?",params);
		List<TeamMatchStatistics> list=new ArrayList<TeamMatchStatistics>();
		for (Match match : matches) {
			if(match.getType()==0){
				TeamMatchStatisticsPK pk=new TeamMatchStatisticsPK(match.getId(), teamId);
				list.add(teamMatchStatisticsDao.get(TeamMatchStatistics.class, pk));
			}
		}
		return list;
	}

	@Override
	public TeamSeasonStatistics getTeamSeasonStatistics(int teamId) {
		// TODO Auto-generated method stub
		return teamSeasonStatisticsDao.get(TeamSeasonStatistics.class, teamId);
	}

	@Override
	public List<TeamSeasonStatistics> getTeamVsStatistics(int teamId,
			int vsTeamId) {
		// TODO Auto-generated method stub
		List<Match> vsMatchList=getTeamVsMatchList(teamId, vsTeamId);
		List<TeamMatchStatistics> list1=new ArrayList<TeamMatchStatistics>();
		List<TeamMatchStatistics> list2=new ArrayList<TeamMatchStatistics>();
		for (Match match : vsMatchList) {
			TeamMatchStatisticsPK pk1=new TeamMatchStatisticsPK(match.getId(), teamId);
			list1.add(teamMatchStatisticsDao.get(TeamMatchStatistics.class, pk1));
			TeamMatchStatisticsPK pk2=new TeamMatchStatisticsPK(match.getId(), vsTeamId);
			list2.add(teamMatchStatisticsDao.get(TeamMatchStatistics.class, pk2));
		}
		List<TeamSeasonStatistics> list=new ArrayList<TeamSeasonStatistics>();
		Team team=teamDao.get(Team.class, teamId);
		Team vsTeam=teamDao.get(Team.class, vsTeamId);
		list.add(new TeamSeasonStatistics(team, list1, CommonDataManager.SEASON));
		list.add(new TeamSeasonStatistics(vsTeam, list2, CommonDataManager.SEASON));
		return list;
	}

	@Override
	public List<Match> getTeamVsMatchList(int teamId,
			int vsTeamId) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		params.add(teamId);
		params.add(vsTeamId);
		params.add(teamId);
		params.add(vsTeamId);
		List<Match> vsMatchList=matchDao.find("from Match where (vId = ? and hId = ?) or (hId = ? and vId = ?)",params);
		return vsMatchList;
	}


}
