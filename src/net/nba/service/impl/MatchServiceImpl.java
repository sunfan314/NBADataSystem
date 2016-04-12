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
		/*
		 * 初次写入数据需要获取所有比赛列表，后续的数据更新只需要提供最新的比赛数据
		 */
		for (Match match : matchList) {
			//为避免一次数据写入时间过长，分批进行
			int id=match.getId();
//			if(id<2015110724){//前200条数据
//				matchIdList.add(id);
//			}	
//			if((id>2015110723)&&(id<2015120514)){//数据200-400
//				matchIdList.add(id);
//			}
//			if((id>2015120513)&&(id<2016010114)){//数据400-600
//				matchIdList.add(id);
//			}
//			if((id>2016010113)&&(id<2016012726)){//数据600-800
//				matchIdList.add(id);
//			}			
//			if((id>2016012725)&&(id<2016022923)){//数据800-1000
//				matchIdList.add(id);
//			}
			
//			
//			if((id>2016022922)&&(id<2016032621)){//数据1000-1200
//				matchIdList.add(id);
//			}
			if(id>2016032620){//最新数据
				matchIdList.add(id);
			}
		}
		List<PlayerMatchStatistics> list=matchInfoSpider.getPlayerMatchStatistics(matchIdList);
		for (PlayerMatchStatistics statistics : list) {
			playerMatchStatisticsDao.saveOrUpdate(statistics);
		}
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

	

//	@Override
//	public MatchInfo getMatchInfo(int matchId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
