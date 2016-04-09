package net.nba.controller;

import java.util.List;

import javax.annotation.Resource;

import net.nba.dataSpider.PlayerInfoSpider;
import net.nba.dataSpider.TeamInfoSpider;
import net.nba.dataSpider.impl.PlayerInfoSpiderImpl;
import net.nba.model.Player;
import net.nba.model.Team;
import net.nba.model.TeamSeasonRank;
import net.nba.service.PlayerService;
import net.nba.service.TeamService;
import net.nba.service.TestService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	@Resource
	private TeamService teamService;
	
	@Resource
	private PlayerService playerService;

	@RequestMapping("/updateTeamInfos")
	public @ResponseBody String updateTeamInfos(){
		try{
			teamService.updateTeamInfo();
			return "Update TeamInfos Success!";
		}catch(Exception e){
			return e.getMessage();
		}
	}
	
	@RequestMapping("/updateTeamSeasonRanks")
	public @ResponseBody String updateTeamSeasonRanks(){
		try {
			teamService.updateTeamSeasonRanks();
			return "Update TeamSeasnoRanks Success!";
		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}
	}
	
	@RequestMapping("/updateTeamPlayerList")
	public @ResponseBody String getPlayerList(){
		try {
			playerService.updateTeamPlayers();
			return "Update TeamPlayers Success!";
		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}
	}

}
