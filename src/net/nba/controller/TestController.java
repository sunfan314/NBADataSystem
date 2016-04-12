package net.nba.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.nba.dataSpider.MatchInfoSpider;
import net.nba.dataSpider.PlayerInfoSpider;
import net.nba.dataSpider.TeamInfoSpider;
import net.nba.dataSpider.impl.MatchInfoSpiderImpl;
import net.nba.dataSpider.impl.PlayerInfoSpiderImpl;
import net.nba.model.Match;
import net.nba.model.Player;
import net.nba.model.PlayerInfoDetail;
import net.nba.model.PlayerMatchStatistics;
import net.nba.model.Team;
import net.nba.model.TeamSeasonRank;
import net.nba.service.MatchService;
import net.nba.service.PlayerService;
import net.nba.service.TeamService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sunfan314
 *
 */
@Controller
public class TestController {
	@Resource
	private TeamService teamService;

	@Resource
	private PlayerService playerService;
	
	@Resource 
	private MatchService matchService;

	@Resource
	private PlayerInfoSpider playerInfoSpider;
	
	@Resource
	private MatchInfoSpider matchInfoSpider;

	/**
	 * @return
	 * 更新球队基本信息
	 */
	@RequestMapping("/updateTeamInfos")
	public @ResponseBody String updateTeamInfos() {
		try {
			teamService.updateTeamInfo();
			return "Update TeamInfos Success!";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	/**
	 * @return	
	 * 更新球队赛季排行
	 */
	@RequestMapping("/updateTeamSeasonRanks")
	public @ResponseBody String updateTeamSeasonRanks() {
		try {
			teamService.updateTeamSeasonRanks();
			return "Update TeamSeasnoRanks Success!";
		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}
	}

	/**
	 * @return
	 * 更新球队阵容信息
	 */
	@RequestMapping("/updateTeamPlayerList")
	public @ResponseBody String getPlayerList() {
		try {
			playerService.updateTeamPlayers();
			return "Update TeamPlayers Success!";
		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}
	}

	/**
	 * @param teamId
	 * @return	
	 * 下载球员图像文件，由于下载量较大，一次性完成容易发生超时错误，最好分批进行，此处按球队获取球员图像信息
	 */
	@RequestMapping("/downloadPlayerImgs")
	public @ResponseBody String downloadPlayerImgs(int teamId) {
		List<Integer> list = new ArrayList<Integer>();
		List<Player> players = playerService.getTeamPlayerList(teamId);
		for (Player player : players) {
			list.add(player.getId());
		}
		try {
			playerInfoSpider.downloadPlayerPic(list);
			return "Download Player Imgs Success!";
		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}

	}

	/**
	 * @return
	 * 更新球员详细信息
	 */
	@RequestMapping("/updatePlayerInfoDetail")
	public @ResponseBody String updatePlayerPlayerInfoDetai() {
		try {
			playerService.updatePlayerInfoDetail();
			return "Update PlayerInfoDetail Success!";
		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}
	}
	
	/**
	 * @return
	 * 更新赛季比赛列表
	 */
	@RequestMapping("/updateSeasonMatchList")
	public @ResponseBody String updateSeasonMatchList(){
		try{
			matchService.updateSeasonMatchList();
			return "Update Season MatchList Success!";
		}catch(Exception e){
			return e.getMessage();
		}
	}
	
	/**
	 * @return
	 * 更新球员比赛统计数据
	 */
	@RequestMapping("/UpdatePlayerMatchStatistics")
	public @ResponseBody String updatePlayerMatchStatistics(){
		try {
			matchService.updatePlayerMatchStatistics();
			return "Update Player-Match-Statistics Success!";
		} catch (Exception e) {
			// TODO: handle exception
			return e.getMessage();
		}
	}
	
//	@RequestMapping("test")
//	public @ResponseBody List<PlayerMatchStatistics> getList(){
//		return matchInfoSpider.getPlayerMatchStatistics(null);
//	}

}
