package net.nba.controller;

import java.util.List;

import javax.annotation.Resource;

import net.nba.model.Match;
import net.nba.model.PlayerMatchStatistics;
import net.nba.service.MatchService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sunfan314
 *
 */
@Controller
public class MatchController {
	@Resource
	private MatchService matchService;
	
	/**
	 * @return	最近50场比赛的列表
	 */
	@RequestMapping("/getLatestMatchList")
	public @ResponseBody List<Match> getLatestMatchs(){
		return matchService.getLatestMatchs();
	}
	
	/**
	 * @param teamId
	 * @return	当前赛季某只球队的所有比赛列表
	 */
	@RequestMapping("/getTeamMatchList")
	public @ResponseBody List<Match> getTeamMatchList(int teamId){
		return matchService.getTeamMatchs(teamId);
	}
	
	/**
	 * @param matchId
	 * @return	某场比赛的球员数据统计信息
	 */
	@RequestMapping("/getPlayerMatchStatistics")
	public @ResponseBody List<PlayerMatchStatistics> getPlayerMatchStatistics(int matchId){
		return matchService.getPlayerMatchStatistics(matchId);
	}
	
	

}
