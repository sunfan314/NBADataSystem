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
	
	private static List<Integer> matchIdList = new ArrayList<Integer>();
	
	/**
	 * 定时更新赛季比赛信息
	 */
	@Scheduled(cron = "*/30 * * * * ?")
	public void updateMatchList(){
		List<Match> matchList=matchDao.find("from Match");
		int lastMatchId=matchList.get(matchList.size()-1).getId();
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
				matchIdList.add(match.getId());
				matchDao.saveOrUpdate(match);
			}
			String info="Update Match Success! Add "+updateList.size()+" Records.";
			MyLog.log(info);
			Thread thread1=new Thread(updateTeamMatchStatistics);
			thread1.run();//更新球队比赛数据统计
			Thread thread2=new Thread(updatePlayerMatchStatistics);
			thread2.run();//更新球员比赛数据统计
		}
		
	
	}
	
	/**
	 * 更新球队比赛数据统计
	 */
	private Runnable updateTeamMatchStatistics = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			List<TeamMatchStatistics> list=matchInfoSpider.getTeamMatchStatistics(matchIdList);
			for (TeamMatchStatistics statistics : list) {
				teamMatchStatisticsDao.saveOrUpdate(statistics);
			}
			String info="Update TeamMatchStatictics Success! Add "+list.size()+" Records.";
			MyLog.log(info);
		}
	};
	
	/**
	 * 更新球员比赛数据统计
	 */
	private Runnable updatePlayerMatchStatistics = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			List<PlayerMatchStatistics> list=matchInfoSpider.getPlayerMatchStatistics(matchIdList);
			for (PlayerMatchStatistics statistics : list) {
				playerMatchStatisticsDao.saveOrUpdate(statistics);
			}
			String info="Update PlayerMatchStatictics Success! Add "+list.size()+" Records.";
			MyLog.log(info);
		}
	};
	
	
	
}
