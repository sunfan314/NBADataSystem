package net.nba.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.nba.service.PlayerService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PlayerController {
//	@Resource
//	private PlayerService playerService;
//	
//	@RequestMapping("/getPlayerRanks")
//	public @ResponseBody List<Map<String,Object>> getPlayerRanks(@RequestParam(required=false)Date date){
//		//获取球员数据统计排行(得分、篮板、助攻、抢断)，每项排行选取前五名，传入日期参数时显示当日的排行，否则显示赛季排行
//		if(date!=null){
//			return playerService.getPlayerRanks(date);
//		}
//		return playerService.getSeasonPlayerRanks();
//	}
//	
//	@RequestMapping("/getTeamPlayers")
//	public @ResponseBody List<Map<String,Object>> getTeamPlayers(int teamId){
//		//获取球队球员列表（包含球员各项基本数据统计：得分、篮板、助攻、抢断、盖帽等）
//		return playerService.getTeamPlayers(teamId);
//	}
//	
//	@RequestMapping("/getPlayerInfo")
//	public @ResponseBody Player getPlayerInfo(int playerId){
//		//获取球员基本信息
//		return playerService.getPlayerInfo(playerId);
//	}
//	
//	@RequestMapping("/getPlayerSeasonStatistics")
//	public @ResponseBody Map<String,Object> getPlayerSeasonStatistics(int playerId){
//		//获取球员赛季数据统计
//		return playerService.getPlayerSeasonStatistics(playerId);
//	}
//	
}
