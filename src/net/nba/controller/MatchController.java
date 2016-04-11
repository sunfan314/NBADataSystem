package net.nba.controller;

import java.util.List;

import javax.annotation.Resource;

import net.nba.model.Match;
import net.nba.service.MatchService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MatchController {
	@Resource
	private MatchService matchService;
	
	@RequestMapping("/getLatestMatchList")
	public @ResponseBody List<Match> getLatestMatchs(){
		//获取最近50场比赛的基本信息
		return matchService.getLatestMatchs();
	}
	
	@RequestMapping("/getTeamMatchList")
	public @ResponseBody List<Match> getTeamMatchLists(int teamId){
		//获取当前赛季某只球队的所有比赛信息
		return matchService.getTeamMatchs(teamId);
	}
	
//	@RequestMapping("/getMatchInfo")
//	public @ResponseBody MatchInfo getMatchInfo(int matchId){
//		//获取某场比赛的详细信息
//		return matchService.getMatchInfo(matchId);
//	}
//	
	

}
