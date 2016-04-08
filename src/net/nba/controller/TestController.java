package net.nba.controller;

import java.util.List;

import javax.annotation.Resource;

import net.nba.dataSpider.TeamInfoSpider;
import net.nba.model.Team;
import net.nba.model.TeamSeasonRank;
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
	private TeamInfoSpider teamInfoSpider;
	

	@RequestMapping("/test")
	public void getTestData(){
		//测试接口
		
		teamService.updateTeamSeasonRanks();
	}

}
