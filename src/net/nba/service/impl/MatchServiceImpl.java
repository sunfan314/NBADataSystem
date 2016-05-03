package net.nba.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.nba.dao.BaseDao;
import net.nba.data.spider.MatchInfoSpider;
import net.nba.model.Match;
import net.nba.model.PlayerMatchStatistics;
import net.nba.model.TeamMatchStatistics;
import net.nba.service.MatchService;

import org.springframework.stereotype.Service;

/**
 * @author sunfan314
 *
 */
@Service("matchService")
public class MatchServiceImpl implements MatchService{
	
	@Resource
	private BaseDao<Match> matchDao;
	
	@Resource
	private BaseDao<PlayerMatchStatistics> playerMatchStatisticsDao;
	
	@Resource
	private BaseDao<TeamMatchStatistics> teamMatchStatisticsDao;
	
	@Resource
	private MatchInfoSpider matchInfoSpider;

	@Override
	public void updateSeasonMatchList() {
		// TODO Auto-generated method stub
		List<Match> matchs=matchInfoSpider.getSeasonMatchList();
		for (Match match : matchs) {
			if((match.gethId()!=0)&&(match.getvId()!=0)){//去除比赛列表中的异常数据
				matchDao.saveOrUpdate(match);
			}
		}
	}
	

	@Override
	public List<Match> getMatchListOfDay(String date) {
		// TODO Auto-generated method stub
		try {
			String[] temp = date.split("-");
			int year = Integer.parseInt(temp[0]);
			String dateStr = temp[1] + "月" + temp[2] + "日";
			List<Object> params=new ArrayList<Object>();
			params.add(year);
			params.add(dateStr);
			return matchDao.find("from Match where year = ? and date = ?", params);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}


	
	@Override
	public void updatePlayerMatchStatistics() {
		// TODO Auto-generated method stub
		List<Integer> matchIdList=new ArrayList<Integer>();
		List<Match> matchList=matchDao.find("from Match");
		/*
		 * 初次写入数据需要获取所有比赛列表，后续的数据更新只需要提供最新的比赛数据
		 */
		for (Match match : matchList) {
			//为避免一次数据写入时间过长，可分批进行
//			matchIdList.add(match.getId());
			if(match.getId()>2016040101){//四月最新赛事
				matchIdList.add(match.getId());
			}
		}
		List<PlayerMatchStatistics> list=matchInfoSpider.getPlayerMatchStatistics(matchIdList);
		for (PlayerMatchStatistics statistics : list) {
			playerMatchStatisticsDao.saveOrUpdate(statistics);
		}
	}
	

	@Override
	public void updateTeamMatchStatistics() {
		// TODO Auto-generated method stub
		List<Integer> matchIdList=new ArrayList<Integer>();
		List<Match> matchList=matchDao.find("from Match");
		for (Match match : matchList) {
			//为避免一次数据写入时间过长，可分批进行
//			matchIdList.add(match.getId());
			if(match.getId()>2016040101){//四月最新赛事
				matchIdList.add(match.getId());
			}
		}
		List<TeamMatchStatistics> list=matchInfoSpider.getTeamMatchStatistics(matchIdList);
		for (TeamMatchStatistics statistics : list) {
			teamMatchStatisticsDao.saveOrUpdate(statistics);
		}
	}
	
	@Override
	public List<Match> getMatchList(int year, String date) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		params.add(year);
		params.add(date);
		return matchDao.find("from Match where year= ? and date = ?", params);
	}

	@Override
	public List<Match> getLatestMatchs() {
		// TODO Auto-generated method stub
		List<Match> matchs=matchDao.find("from Match");
		List<Match> list=new ArrayList<Match>();
		for(int i=50;i>0;i--){
			list.add(matchs.get(matchs.size()-i));
		}
		return list;
	}

	@Override
	public List<Match> getTeamMatchs(int teamId) {
		// TODO Auto-generated method stub
		List<Object> params=new ArrayList<Object>();
		params.add(teamId);
		params.add(teamId);
		List<Match> list=matchDao.find("from Match where vId = ? or hId = ?",params);
		return list;
	}

	@Override
	public List<PlayerMatchStatistics> getPlayerMatchStatistics(int matchId) {
		// TODO Auto-generated method stub
		return playerMatchStatisticsDao.find("from PlayerMatchStatistics where matchId = ?",matchId);
	}

	@Override
	public List<TeamMatchStatistics> getTeamMatchStatistics(int matchId) {
		// TODO Auto-generated method stub
		return teamMatchStatisticsDao.find("from TeamMatchStatistics where matchId = ?", matchId);
	}

}
