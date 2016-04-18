package net.nba.controller;

import java.util.List;

import javax.annotation.Resource;

import net.nba.model.Player;
import net.nba.model.PlayerSeasonStatistics;
import net.nba.model.Team;
import net.nba.model.TeamSeasonRank;
import net.nba.service.TeamService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sunfan314
 *
 */
@Controller
public class TeamController {
	@Resource
	private TeamService teamService;

	/**
	 * @return	联盟球队列表
	 */
	@RequestMapping("/getTeams")
	public @ResponseBody List<Team> getTeams() {
		return teamService.getTeams();
	}

	/**
	 * @param teamId
	 * @return	某只球队基本信息
	 */
	@RequestMapping("/getTeamInfos")
	public @ResponseBody Team getTeamInfos(int teamId) {
		return teamService.getTeamInfos(teamId);
	}
	
	/**
	 * @param teamId
	 * @return	某只球队的球员列表
	 */
	@RequestMapping("/getTeamPlayerList")
	public @ResponseBody List<Player> getTeamPlayerList(int teamId){
		return teamService.getTeamPlayerList(teamId);
	}
	
	/**
	 * @param teamId
	 * @return	球队球员赛季数据统计
	 */
	@RequestMapping("/getTeamPlayerStatistics")
	public @ResponseBody List<PlayerSeasonStatistics> getPlayerStatistics(int teamId){		
		return teamService.getTeamPlayerStatistics(teamId);
		
	}
	
	/**
	 * @return	球队赛季排行列表
	 */
	@RequestMapping("/getTeamSeasonRanks")
	public @ResponseBody List<TeamSeasonRank> getTeamSeasonRanks(){
		return teamService.getTeamSeasonRanks();
	}
	
//	
//	@RequestMapping("/getTeamSeasonStatistics")
//	public @ResponseBody Map<String, Object> getTeamStatistics(int teamId){
//		//获取当前赛季球队比赛数据统计
//		return teamService.getTeamStatistics(teamId);
//	}
//	
//	@RequestMapping("/getTeamVsStatistics")
//	public @ResponseBody List<Map<String, Object>> getTeamVsStatistics(int teamId,int vsTeamId){
//		//获取球队与某只球队交锋数据统计
//		return teamService.getTeamVsStatistics(teamId,vsTeamId);
//	}
}
