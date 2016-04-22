package net.nba.data.task;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.nba.dao.BaseDao;
import net.nba.data.spider.MatchInfoSpider;
import net.nba.model.Match;
import net.nba.model.PlayerMatchStatistics;
import net.nba.model.PlayerSeasonStatistics;
import net.nba.model.TeamMatchStatistics;
import net.nba.model.TeamMatchStatisticsPK;
import net.nba.model.TeamSeasonStatistics;
import net.nba.util.CommonDataManager;
import net.nba.util.MyLog;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author sunfan314
 *
 */
@Component("matchUpdateTask")
public class MatchUpdateTask {
	@Resource
	private BaseDao<Match> matchDao;
	
	@Resource
	private BaseDao<TeamMatchStatistics> teamMatchStatisticsDao;
	
	@Resource
	private BaseDao<TeamSeasonStatistics> teamSeasonStatisticsDao;
	
	@Resource
	private BaseDao<PlayerMatchStatistics> playerMatchStatisticsDao;
	
	@Resource
	private BaseDao<PlayerSeasonStatistics> playerSeasonStatisticsDao;
	
	@Resource
	private MatchInfoSpider matchInfoSpider;
	
	
	
	/**
	 * 定时更新赛季比赛信息
	 * 更新时间：在赛季期间（每年的10月至12月，一月至6月），每天0时至14时（由于在北京时间14点之后极少有比赛进行），每20分钟更新一次
	 */
	@Scheduled(cron = "0 0/20 0-14 * 1-6,10-12 ?")
	public void updateMatchList(){
		Match lastMatch=matchDao.find("from Match where id = (select max(id) from Match)").get(0);
		int lastMatchId=lastMatch.getId();
		List<Match> updateList=new ArrayList<Match>();
		/*
		 * 赛季从10月进入季前赛，4月进入季后赛，6月决出总冠军
		 */
		boolean endUpdate=false;
		for (int i = CommonDataManager.SEASONENDMONTH; i >0; i--) {
			if(endUpdate){
				break;
			}
			List<Match> list=matchInfoSpider.getMonthMatchList(CommonDataManager.SEASONENDYEAR, i);
			for (Match match : list) {
				if(match.getId()>lastMatchId){//尚未记录比赛数据
					match.setSeason(CommonDataManager.SEASONSTARTYEAR + "-" + CommonDataManager.SEASONENDYEAR);
					match.setYear(CommonDataManager.SEASONENDYEAR);
					updateList.add(match);
				}else{
					endUpdate=true;//上月数据已爬取
				}
			}
		}
		for (int i = 12; i >(CommonDataManager.SEASONSTARTMONTH-1); i--) {
			if(endUpdate){
				break;
			}
			List<Match> list=matchInfoSpider.getMonthMatchList(CommonDataManager.SEASONSTARTYEAR, i);
			for (Match match : list) {
				if(match.getId()>lastMatchId){//尚未记录比赛数据
					match.setSeason(CommonDataManager.SEASONSTARTYEAR + "-" + CommonDataManager.SEASONENDYEAR);
					match.setYear(CommonDataManager.SEASONSTARTYEAR);
					updateList.add(match);
				}else{
					endUpdate=true;//上月数据已爬取
				}
			}
		}
		if(updateList.size()>0){
			for (Match match : updateList) {
				matchDao.saveOrUpdate(match);
			}
			String info="Update Match Success! Add "+updateList.size()+" Records.";
			MyLog.log(info);
		}
		
	
	}
	
	/**
	 * 定时更新球队比赛信息
	 * 更新时间：在赛季期间（每年的10月至12月，一月至6月），每天0时至14时（由于在北京时间14点之后极少有比赛进行），从5分开始每20分钟更新一次
	 */
	@Scheduled(cron = "0 5/20 0-14 * 1-6,10-12 ?")
	public void updateTeamMatchList(){
		TeamMatchStatistics lastTeamMatchStatistics=teamMatchStatisticsDao.find("from TeamMatchStatistics where matchId = (select max(matchId) from TeamMatchStatistics)").get(0);
		int lastMatchId=lastTeamMatchStatistics.getMatchId();
		List<Match> matchList=matchDao.find("from Match");
		List<Integer> matchIdList=new ArrayList<Integer>();
		for (Match match : matchList) {
			if(match.getId()>lastMatchId){
				matchIdList.add(match.getId());
			}
		}
		List<TeamMatchStatistics> updateList=matchInfoSpider.getTeamMatchStatistics(matchIdList);
		if(updateList.size()>0){
			for (TeamMatchStatistics statistics : updateList) {
				teamMatchStatisticsDao.saveOrUpdate(statistics);
			}
			String info="Update TeamMatchStatictics Success! Add "+updateList.size()+" Records.";
			MyLog.log(info);	
		}		
	}
	
	/**
	 * 定时更新球员比赛数据列表
	 * 在赛季期间（每年的10月至12月，一月至6月），每天0时至14时（由于在北京时间14点之后极少有比赛进行），从5分开始，每20分钟更新一次
	 */
	@Scheduled(cron = "0 5/20 0-14 * 1-6,10-12 ?")
	public void updatePlayerMatchStatistics(){
		PlayerMatchStatistics lastPlayerMatchStatistics=playerMatchStatisticsDao.find("from PlayerMatchStatistics where matchId = (select max(matchId) from PlayerMatchStatistics)").get(0);
		int lastMatchId=lastPlayerMatchStatistics.getMatchId();
		List<Match> matchList=matchDao.find("from Match");
		List<Integer> matchIdList=new ArrayList<Integer>();
		for (Match match : matchList) {
			if(match.getId()>lastMatchId){
				matchIdList.add(match.getId());
			}
		}
		List<PlayerMatchStatistics> updateList=matchInfoSpider.getPlayerMatchStatistics(matchIdList);
		if(updateList.size()>0){
			for (PlayerMatchStatistics statistics : updateList) {
				playerMatchStatisticsDao.saveOrUpdate(statistics);
			}
			String info="Update PlayerMatchStatictics Success! Add "+updateList.size()+" Records.";
			MyLog.log(info);
		}
		
	}
	
	
	
}
