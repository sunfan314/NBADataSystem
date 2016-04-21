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
	 * 更新球队信息
	 */
	@Scheduled(cron = "*/30 * * * * ?")
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
	 */
	@Scheduled(cron = "*/30 * * * * ?")
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
	 */
	@Scheduled(cron = "*/30 * * * * ?")
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
