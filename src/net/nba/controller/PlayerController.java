package net.nba.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.nba.model.Player;
import net.nba.model.PlayerDataRank;
import net.nba.model.PlayerInfoDetail;
import net.nba.model.PlayerSeasonStatistics;
import net.nba.service.PlayerService;
import net.nba.util.FilePathManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sunfan314
 *
 */
@Controller
public class PlayerController {
	@Resource
	private PlayerService playerService;
	
	/**
	 * @param playerId
	 * @return	某位球员的详细信息
	 */
	@RequestMapping("/getPlayerInfoDetail")
	public @ResponseBody PlayerInfoDetail getPlayerInfoDetail(int playerId){
		return playerService.getPlayerInfoDetail(playerId);
	}
	

	/**
	 * @param date	例：2015-03-21
	 * @return	获取球员数据统计排行(得分、篮板、助攻、抢断)，每项排行选取前五名，传入日期参数时显示当日的排行，否则显示赛季排行
	 */
	@RequestMapping("/getPlayerRanks")
	public @ResponseBody List<PlayerDataRank> getPlayerRanks(@RequestParam(required=false)String date){
		if(date!=null){
			return playerService.getPlayerDataRanks(date);
		}
		return playerService.getPlayerSeasonRanks();
	}
	
	@RequestMapping("/getPlayerSeasonStatistics")
	public @ResponseBody PlayerSeasonStatistics getPlayerSeasonStatistics(int playerId){
		return playerService.getPlayerSeasonStatistics(playerId);
	}
	
}
