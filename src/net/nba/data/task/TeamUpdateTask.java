package net.nba.data.task;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.nba.dao.BaseDao;
import net.nba.data.spider.TeamInfoSpider;
import net.nba.model.Team;
import net.nba.model.TeamMatchStatistics;
import net.nba.model.TeamSeasonRank;
import net.nba.model.TeamSeasonStatistics;
import net.nba.service.TeamService;
import net.nba.util.CommonDataManager;
import net.nba.util.MyLog;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author sunfan314
 *
 */
@Component("teamUpdateTask")
public class TeamUpdateTask {
	@Resource
	private BaseDao<Team> teamDao;
	
	@Resource
	private BaseDao<TeamSeasonRank> teamRankDao;
	
	@Resource
	private BaseDao<TeamSeasonStatistics> teamSeasonStatisticsDao;
	
	@Resource
	private TeamService teamService;
	
	@Resource
	private TeamInfoSpider teamInfoSpider;
	
	/**
	 * 更新球队信息，更新频率较低
	 * 更新时间：在赛季期间（每年的10月至12月，一月至6月），每月的1日15日凌晨更新
	 */
	@Scheduled(cron = "0 0 0 1,15 1-6,10-12 ?")
	public void updateTeamInfo(){
		List<Team> list = teamInfoSpider.getTeamInfoList();
		for (Team team : list) {
			teamDao.saveOrUpdate(team);
		}
		String info="Update Team Success!";
		MyLog.log(info);
	}
	
	/**
	 * 每日更新球队赛季排行信息
	 * 在赛季期间（每年的10月至12月，一月至6月），每天0时至14时（由于在北京时间14点之后极少有比赛进行），从5分开始，每20分钟更新一次
	 */
	@Scheduled(cron = "0 5/20 0-14 * 1-6,10-12 ?")
	public void updateTeamSeasonRanks(){
		List<TeamSeasonRank> list=teamInfoSpider.getTeamSeasonRanks();
		for (TeamSeasonRank rank : list) {
			teamRankDao.saveOrUpdate(rank);
		}
		String info="Update TeamSeasonRanks Success!";
		MyLog.log(info);
	}
	
	/**
	 * 定时更新球队赛季数据统计
	 *  在赛季期间（每年的10月至12月，一月至6月），每天0时至14时（由于在北京时间14点之后极少有比赛进行），从10分开始，每20分钟更新一次
	 */
	@Scheduled(cron = "0 10/20 0-14 * 1-6,10-12 ?")
	public void updateTeamSeasonStatistics(){
		List<TeamSeasonStatistics> list=new ArrayList<TeamSeasonStatistics>();
		List<Team> teams=teamDao.find("from Team");
		for (Team team : teams) {
			List<TeamMatchStatistics> dataList=teamService.getTeamMatchStatistics(team.getId(),CommonDataManager.SEASON );
			TeamSeasonStatistics statistics=new TeamSeasonStatistics(team,dataList,CommonDataManager.SEASON);
			list.add(statistics);
		}
		for (TeamSeasonStatistics tss : list) {
			teamSeasonStatisticsDao.saveOrUpdate(tss);
		}
		String info="Update TeamSeasonStatictics Success! ";
		MyLog.log(info);
	}
	

}
