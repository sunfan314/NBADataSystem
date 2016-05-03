package net.nba.data.task;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.nba.dao.BaseDao;
import net.nba.data.spider.PlayerInfoSpider;
import net.nba.model.Player;
import net.nba.model.PlayerAdvancedStatistics;
import net.nba.model.PlayerInfoDetail;
import net.nba.model.PlayerMatchStatistics;
import net.nba.model.PlayerSeasonStatistics;
import net.nba.model.Team;
import net.nba.service.PlayerService;
import net.nba.service.TeamService;
import net.nba.util.CommonDataManager;
import net.nba.util.MyLog;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author sunfan314
 *
 */
@Component("playerUpdateTask")
public class PlayerUpdateTask {
	@Resource
	private BaseDao<Team> teamDao;
	
	@Resource
	private BaseDao<Player> playerDao;
	
	@Resource
	private BaseDao<PlayerInfoDetail> playerInfoDetailDao;
	
	@Resource
	private BaseDao<PlayerSeasonStatistics> playerSeasonStatisticsDao;
	
	@Resource
	private BaseDao<PlayerAdvancedStatistics> playerAdvancedStatisticsDao;
	
	@Resource
	private TeamService teamService;
	
	@Resource
	private PlayerService playerService;
	
	@Resource
	private PlayerInfoSpider playerInfoSpider;
	
	
	/**
	 * 定时更新球队阵容列表
	 * 更新时间：在赛季期间（每年的10月至12月，一月至6月），每月的1日15日凌晨更新
	 */
	@Scheduled(cron = "0 0 0 1,15 1-6,10-12 ?")
	public void updateTeamPlayerList(){
		List<Player> list = playerInfoSpider.getTeamPlayerList();
		for (Player player : list) {
			if (player.getId() == 0) {
				String dataError = "Can not get id of player: "
						+ player.getName() + " from team " + player.getTeamId();
				MyLog.e(dataError);// 在日志文件中记录无法获取id的球员信息

			} else {
				playerDao.saveOrUpdate(player);
			}

		}
		String info="Update Player Success!";
		MyLog.log(info);
	}
	
	/**
	 * 定时更新球员详细信息，更新频率较低
	 * 更新时间：在赛季期间（每年的10月至12月，一月至6月），每月的1日15日凌晨更新
	 */
	@Scheduled(cron = "0 0 0 1,15 1-6,10-12 ?")
	public void updatePlayerInfoDetail(){
		List<Integer> list = new ArrayList<Integer>();
		List<Player> playerList = playerDao.find("from Player");
		for (Player player : playerList) {
			list.add(player.getId());
		}
		List<PlayerInfoDetail> playerInfoDetails = playerInfoSpider
				.getPlayerInfoDetail(list);
		for (PlayerInfoDetail playerInfoDetail : playerInfoDetails) {
			playerInfoDetailDao.saveOrUpdate(playerInfoDetail);
		}
		String info="Update PlayerInfoDetail Success! ";
		MyLog.log(info);
	}
	
	/**
	 * 下载球员图片并存储
	 *  更新时间：在赛季期间（每年的10月至12月，一月至6月），每月的1日凌晨更新
	 */
	@Scheduled(cron = "0 0 0 1 1-6,10-12 ?")
	public void downloadPlayerPic(){
		List<Team> teams=teamDao.find("from Team");
		for (Team team : teams) {
			List<Integer> list = new ArrayList<Integer>();
			List<Player> players = teamService.getTeamPlayerList(team.getId());
			for (Player player : players) {
				list.add(player.getId());
			}
			try {
				playerInfoSpider.downloadPlayerPic(list);
			} catch (Exception e) {
				// TODO: handle exception
				MyLog.e(e.getMessage());
			}
		}
	}
	
	/**
	 * 定时更新球员赛季数据统计
	 * 在赛季期间（每年的10月至12月，一月至6月），每天0时至14时（由于在北京时间14点之后极少有比赛进行），从10分开始，每20分钟更新一次
	 */
	@Scheduled(cron = "0 10/20 0-14 * 1-6,10-12 ?")
	public void updatePlayerSeasonStatistics(){
		List<PlayerSeasonStatistics> list=new ArrayList<PlayerSeasonStatistics>();
		List<Player> players=playerDao.find("from Player");
		for (Player player : players) {//更新每位球员赛季数据统计
			/*
			 * 获得球员赛季常规赛比赛统计数据列表
			 */
			Team team=teamDao.get(Team.class, player.getTeamId());
			List<PlayerMatchStatistics> dataList=playerService.getPlayerMatchStatistics(player.getId(), CommonDataManager.SEASON);
			int totMatches=dataList.size();
			if(totMatches==0){
				continue;//跳过未上场球员
			}
			PlayerSeasonStatistics statistics=new PlayerSeasonStatistics(player,team,dataList,CommonDataManager.SEASON);
			list.add(statistics);
		}
		for (PlayerSeasonStatistics pss : list) {
			playerSeasonStatisticsDao.saveOrUpdate(pss);
		}
		String info="Update PlayerSeasonStatictics Success! ";
		MyLog.log(info);
	}
	
	/**
	 * 定时更新球员赛季进阶数据统计
	 * 在赛季期间（每年的10月至12月，一月至6月），每天0时至14时（由于在北京时间14点之后极少有比赛进行），从10分开始，每20分钟更新一次
	 */
	@Scheduled(cron = "0 10/20 0-14 * 1-6,10-12 ?")
	public void updatePlayerAdvancedStatistics(){
		List<PlayerAdvancedStatistics> list=new ArrayList<PlayerAdvancedStatistics>();
		List<Player> players=playerDao.find("from Player");
		for (Player player : players) {
			/*
			 * 获得球员赛季常规赛比赛统计数据列表
			 */
			Team team=teamDao.get(Team.class, player.getTeamId());
			List<PlayerMatchStatistics> dataList=playerService.getPlayerMatchStatistics(player.getId(), CommonDataManager.SEASON);
			int totMatches=dataList.size();
			if(totMatches==0){
				continue;//跳过未上场球员
			}
			PlayerAdvancedStatistics statistics=new PlayerAdvancedStatistics(player,team,dataList,CommonDataManager.SEASON);
			list.add(statistics);
		}
		for (PlayerAdvancedStatistics statistics : list) {
			playerAdvancedStatisticsDao.saveOrUpdate(statistics);
		}
		String info="Update PlayerAdvancedStatictics Success! ";
		MyLog.log(info);
	}

}
