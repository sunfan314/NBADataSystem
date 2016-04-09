package net.nba.controller;

import java.util.ArrayList;
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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	@Resource
	private TeamService teamService;
	
	@Resource
	private PlayerService playerService;

	@Resource
	private PlayerInfoSpider playerInfoSpider;

	@RequestMapping("/updateTeamInfos")
	public @ResponseBody String updateTeamInfos(){
		//更新球队基本信息
		try{
			teamService.updateTeamInfo();
			return "Update TeamInfos Success!";
		}catch(Exception e){
			return e.getMessage();
		}
	}
	
	@RequestMapping("/updateTeamSeasonRanks")
	public @ResponseBody String updateTeamSeasonRanks(){
		//更新球队赛季排行
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
		//更新球队阵容信息
		try {
			playerService.updateTeamPlayers();
			return "Update TeamPlayers Success!";
		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}
	}
	
	@RequestMapping("/downloadPlayerImgs")
	public @ResponseBody String downloadPlayerImgs(){
		List<String> list=new ArrayList<String>();
		List<Player> players=playerService.getPlayers();
		for (Player player : players) {
			list.add(player.getNameInEn());
		}
		try {
			playerInfoSpider.downloadPlayerPic(list);
			return "Download Player Imgs Success!";
		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}
		
	}

}
