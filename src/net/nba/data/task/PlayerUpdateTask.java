package net.nba.data.task;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.nba.dao.BaseDao;
import net.nba.data.spider.PlayerInfoSpider;
import net.nba.model.Player;
import net.nba.model.PlayerInfoDetail;
import net.nba.model.PlayerMatchStatistics;
import net.nba.model.PlayerSeasonStatistics;
import net.nba.model.Team;
import net.nba.service.PlayerService;
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
	private PlayerService playerService;
	
	@Resource
	private PlayerInfoSpider playerInfoSpider;
	
	/**
	 * 定时更新球队阵容列表
	 */
	@Scheduled(cron = "*/30 * * * * ?")
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
	 * 定时更新球员详细信息
	 */
	@Scheduled(cron = "*/30 * * * * ?")
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
	 * 定时更新球员赛季数据统计
	 */
	@Scheduled(cron = "*/30 * * * * ?")
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
	
	

}
