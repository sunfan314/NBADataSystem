package net.nba.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.nba.model.Player;
import net.nba.model.Team;
import net.nba.model.TeamSeasonRank;
import net.nba.service.TeamService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TeamController {
	@Resource
	private TeamService teamService;

	@RequestMapping("/getTeams")
	public @ResponseBody List<Team> getTeams() {
		// 获取所有球队基本信息列表
		return teamService.getTeams();
	}

	@RequestMapping("/getTeamInfos")
	public @ResponseBody Team getTeamInfos(int teamId) {
		// 获取某只球队基本信息
		return teamService.getTeamInfos(teamId);
	}
	
	@RequestMapping("/getTeamSeasonRanks")
	public @ResponseBody List<TeamSeasonRank> getTeamSeasonRanks(){
		//获取球队赛季排行，包含胜场、负场及胜场差
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
