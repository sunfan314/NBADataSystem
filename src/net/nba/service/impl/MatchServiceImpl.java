package net.nba.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.nba.dao.BaseDao;
import net.nba.dataSpider.MatchInfoSpider;
import net.nba.model.Match;
import net.nba.model.PlayerMatchStatistics;
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
	private MatchInfoSpider matchInfoSpider;

	@Override
	public void updateSeasonMatchList() {
		// TODO Auto-generated method stub
		List<Match> matchs=matchInfoSpider.getSeasonMatchList();
		for (Match match : matchs) {
			matchDao.saveOrUpdate(match);
		}
	}
	
	@Override
	public void updatePlayerMatchStatistics() {
		// TODO Auto-generated method stub
		List<Integer> matchIdList=new ArrayList<Integer>();
		List<Match> matchList=matchDao.find("from Match");
		for (Match match : matchList) {
			matchIdList.add(match.gethId());
		}
		//List<PlayerMatchStatistics> list=
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

	

//	@Override
//	public MatchInfo getMatchInfo(int matchId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
