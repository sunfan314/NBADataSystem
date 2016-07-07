package net.nba.controller;

import java.util.List;

import javax.annotation.Resource;

import net.nba.model.Match;
import net.nba.model.Player;
import net.nba.model.PlayerAdvancedStatistics;
import net.nba.model.PlayerSeasonStatistics;
import net.nba.model.Team;
import net.nba.model.TeamSeasonRank;
import net.nba.model.TeamSeasonStatistics;
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
	 * @return	球队赛季排行列表
	 */
	@RequestMapping("/getTeamSeasonRanks")
	public @ResponseBody List<TeamSeasonRank> getTeamSeasonRanks(){
		return teamService.getTeamSeasonRanks();
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
	 * @param teamId
	 * @return	球队球员赛季进阶数据统计
	 */
	@RequestMapping("/getTeamPlayerAdvancedStatistics")
	public @ResponseBody List<PlayerAdvancedStatistics> getPlayerAdvancedStatistics(int teamId){
		return teamService.getTeamPlayerAdvancedStatistics(teamId);
	}
	
	/**
	 * @param teamId
	 * @return	获取当前赛季球队比赛数据统计
	 */
	@RequestMapping("/getTeamSeasonStatistics")
	public @ResponseBody TeamSeasonStatistics getTeamSeasonStatistics(int teamId){
				return teamService.getTeamSeasonStatistics(teamId);
	}
	
	/**
	 * @param teamId
	 * @param vsTeamId
	 * @return	获取球队与某只球队交锋数据统计
	 */
	@RequestMapping("/getTeamVsStatistics")
	public @ResponseBody List<TeamSeasonStatistics> getTeamVsStatistics(int teamId,int vsTeamId){
		return teamService.getTeamVsStatistics(teamId,vsTeamId);
	}
	
	/**
	 * @param teamId
	 * @param vsTeamId
	 * @return	获取球队与某只球队交锋列表
	 */
	@RequestMapping("/getTeamVsMatchList")
	public @ResponseBody List<Match> getTeamVsMatchList(int teamId,int vsTeamId){
		return teamService.getTeamVsMatchList(teamId,vsTeamId);
	}
	
}
