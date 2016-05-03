package net.nba.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.jasper.tagplugins.jstl.core.If;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.mysql.fabric.xmlrpc.base.Array;

import net.nba.dao.BaseDao;
import net.nba.data.spider.PlayerInfoSpider;
import net.nba.model.Match;
import net.nba.model.Player;
import net.nba.model.PlayerAdvancedStatistics;
import net.nba.model.PlayerDataRank;
import net.nba.model.PlayerInfoDetail;
import net.nba.model.PlayerMatchStatistics;
import net.nba.model.PlayerSeasonStatistics;
import net.nba.model.Team;
import net.nba.service.MatchService;
import net.nba.service.PlayerService;
import net.nba.util.CommonDataManager;
import net.nba.util.DoubleFormat;
import net.nba.util.FilePathManager;
import net.nba.util.MyFileWriter;
import net.nba.util.MyLog;

/**
 * @author sunfan314
 *
 */
@Service("playerService")
public class PlayerServiceImpl implements PlayerService {
	@Resource
	private BaseDao<Player> playerDao;
	
	@Resource
	private BaseDao<Team> teamDao;
	
	@Resource
	private BaseDao<Match> matchDao;

	@Resource
	private BaseDao<PlayerInfoDetail> playerInfoDetailDao;

	@Resource
	private BaseDao<PlayerMatchStatistics> playerMatchStatisticsDao;
	
	@Resource 
	private BaseDao<PlayerSeasonStatistics> playerSeasonStatisticsDao;
	
	@Resource
	private BaseDao<PlayerAdvancedStatistics> playerAdvancedStatisticsDao;

	@Resource
	private MatchService matchService;

	@Resource
	private PlayerInfoSpider playerInfoSpider;

	@Override
	public void updateTeamPlayers() {
		// TODO Auto-generated method stub
		List<Player> list = playerInfoSpider.getTeamPlayerList();
		for (Player player : list) {
			if (player.getId() == 0) {
				String dataError = "Can not get id of player: "
						+ player.getName() + " from team " + player.getTeamId();
				MyLog.e(dataError);// 在日志文件中记录无法获取id的球员信息

			} else {
				playerDao.saveOrUpdate(player);
			}

		}
	}

	@Override
	public void updatePlayerInfoDetail() {
		// TODO Auto-generated method stub
		List<Integer> list = new ArrayList<Integer>();
		List<Player> playerList = playerDao.find("from Player");
		for (Player player : playerList) {
			list.add(player.getId());
		}
		List<PlayerInfoDetail> playerInfoDetails = playerInfoSpider
				.getPlayerInfoDetail(list);
		for (PlayerInfoDetail playerInfoDetail : playerInfoDetails) {
			playerInfoDetailDao.saveOrUpdate(playerInfoDetail);
		}
	}
	
	@Override
	public void updatePlayerSeasonStatistics() {
		// TODO Auto-generated method stub
		List<PlayerSeasonStatistics> list=new ArrayList<PlayerSeasonStatistics>();
		List<Player> players=playerDao.find("from Player");
		for (Player player : players) {//更新每位球员赛季数据统计
			/*
			 * 获得球员赛季常规赛比赛统计数据列表
			 */
			Team team=teamDao.get(Team.class, player.getTeamId());
			List<PlayerMatchStatistics> dataList=getPlayerMatchStatistics(player.getId(), CommonDataManager.SEASON);
			int totMatches=dataList.size();
			if(totMatches==0){
				continue;//跳过未上场球员
			}
			PlayerSeasonStatistics statistics=new PlayerSeasonStatistics(player,team,dataList,CommonDataManager.SEASON);
			list.add(statistics);
		}
		for (PlayerSeasonStatistics pss : list) {
			playerSeasonStatisticsDao.saveOrUpdate(pss);
		}
		
	}
	
	@Override
	public void updatePlayerSeasonAdvancedStatistics() {
		// TODO Auto-generated method stub
		List<PlayerAdvancedStatistics> list=new ArrayList<PlayerAdvancedStatistics>();
		List<Player> players=playerDao.find("from Player");
		for (Player player : players) {
			/*
			 * 获得球员赛季常规赛比赛统计数据列表
			 */
			Team team=teamDao.get(Team.class, player.getTeamId());
			List<PlayerMatchStatistics> dataList=getPlayerMatchStatistics(player.getId(), CommonDataManager.SEASON);
			int totMatches=dataList.size();
			if(totMatches==0){
				continue;//跳过未上场球员
			}
			PlayerAdvancedStatistics statistics=new PlayerAdvancedStatistics(player,team,dataList,CommonDataManager.SEASON);
			list.add(statistics);
		}
		for (PlayerAdvancedStatistics statistics : list) {
			playerAdvancedStatisticsDao.saveOrUpdate(statistics);
		}
	}

	@Override
	public List<Player> getPlayers() {
		// TODO Auto-generated method stub
		return playerDao.find("from Player");
	}

	@Override
	public PlayerInfoDetail getPlayerInfoDetail(int playeId) {
		// TODO Auto-generated method stub
		return playerInfoDetailDao.get(PlayerInfoDetail.class, playeId);
	}
	
	@Override
	public List<PlayerMatchStatistics> getPlayerMatchStatistics(int playerId,
			String season) {
		// TODO Auto-generated method stub
		List<PlayerMatchStatistics> list=new ArrayList<PlayerMatchStatistics>();
		List<PlayerMatchStatistics> statistics=playerMatchStatisticsDao.find("from PlayerMatchStatistics where playerId = ?",playerId);
		for (PlayerMatchStatistics playerMatchStatistics : statistics) {
			Match match=matchDao.get(Match.class, playerMatchStatistics.getMatchId());
			if(match.getSeason().equals(season)&&match.getType()==0){
				list.add(playerMatchStatistics);//筛选球员某赛季常规赛比赛统计数据
			}
		}
		return list;
	}

	@Override
	public List<PlayerDataRank> getPlayerDataRanks(String date) {
		// TODO Auto-generated method stub
		List<PlayerMatchStatistics> statisticsList = new ArrayList<PlayerMatchStatistics>();
		List<PlayerDataRank> playerDataRanks = new ArrayList<PlayerDataRank>();
		/*
		 *	获取比赛数据
		 */
		try {
			String[] temp = date.split("-");
			int year = Integer.parseInt(temp[0]);
			String dateStr = temp[1] + "月" + temp[2] + "日";
			List<Match> matchList = matchService.getMatchList(year, dateStr);
			for (Match match : matchList) {
				List<PlayerMatchStatistics> list = playerMatchStatisticsDao
						.find("from PlayerMatchStatistics where matchId = ?",
								match.getId());
				statisticsList.addAll(list);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		for(int i=0;i<4;i++){
			for (PlayerMatchStatistics statistics : playerSortByData(statisticsList,5,i)) {
				PlayerDataRank rank = new PlayerDataRank();
				rank.setType(i);//得分：0;篮板：1；助攻：2；抢断：3
				rank.setData(statistics.getScore());
				rank.setId(statistics.getPlayerId());
				rank.setName(statistics.getPlayerName());
				Team team=teamDao.get(Team.class, statistics.getTeamId());
				rank.setTeamName(team.getName());
				playerDataRanks.add(rank);
			}
		}
		return playerDataRanks;
	}
	

	@Override
	public List<PlayerDataRank> getPlayerSeasonRanks() {
		// TODO Auto-generated method stub
		List<PlayerDataRank> playerDataRanks=new ArrayList<PlayerDataRank>();
		List<PlayerSeasonStatistics> statisticsList=playerSeasonStatisticsDao.find("from PlayerSeasonStatistics");
		for(int i=0;i<4;i++){
			for (PlayerSeasonStatistics statistics : playerSortBySeasonData(statisticsList,5,i)) {
				PlayerDataRank rank = new PlayerDataRank();
				rank.setType(i);//得分：0;篮板：1；助攻：2；抢断：3
				rank.setSeasonData(statistics.getScore());
				rank.setId(statistics.getPlayerId());
				rank.setName(statistics.getPlayerName());
				rank.setTeamName(statistics.getTeamName());
				playerDataRanks.add(rank);
			}
		}
		return playerDataRanks;
	}
	
	@Override
	public PlayerSeasonStatistics getPlayerSeasonStatistics(int playerId) {
		// TODO Auto-generated method stub
		return playerSeasonStatisticsDao.get(PlayerSeasonStatistics.class,playerId);
	}
	



	/**
	 * @param statisticsList
	 * @param num	获取前num条记录
	 * @param type	按type进行排名：0代表得分；1代表篮板；2代表助攻；3代表抢断
	 * @return
	 */
	private List<PlayerSeasonStatistics> playerSortBySeasonData(
			List<PlayerSeasonStatistics> statisticsList, int num, int type) {
		// TODO Auto-generated method stub
		List<PlayerSeasonStatistics> list=new ArrayList<PlayerSeasonStatistics>();
		switch (type) {
		case 0:
			for(int i=0;i<num;i++){
				int size=statisticsList.size();
				for(int j=0;j<size-1;j++){//冒泡排序
					if(statisticsList.get(j).getScore()>statisticsList.get(j+1).getScore()){
						PlayerSeasonStatistics temp1=statisticsList.get(j);
						PlayerSeasonStatistics temp2=statisticsList.get(j+1);
						statisticsList.set(j, temp2);
						statisticsList.set(j+1, temp1);
					}
				}	
				list.add(statisticsList.get(size-1));
				statisticsList.remove(size-1);
			}
			break;
		case 1:
			for(int i=0;i<num;i++){
				int size=statisticsList.size();
				for(int j=0;j<size-1;j++){//冒泡排序
					if(statisticsList.get(j).getTotReb()>statisticsList.get(j+1).getTotReb()){
						PlayerSeasonStatistics temp1=statisticsList.get(j);
						PlayerSeasonStatistics temp2=statisticsList.get(j+1);
						statisticsList.set(j, temp2);
						statisticsList.set(j+1, temp1);
					}
				}	
				list.add(statisticsList.get(size-1));
				statisticsList.remove(size-1);
			}
			break;
		case 2:
			for(int i=0;i<num;i++){
				int size=statisticsList.size();
				for(int j=0;j<size-1;j++){//冒泡排序
					if(statisticsList.get(j).getAss()>statisticsList.get(j+1).getAss()){
						PlayerSeasonStatistics temp1=statisticsList.get(j);
						PlayerSeasonStatistics temp2=statisticsList.get(j+1);
						statisticsList.set(j, temp2);
						statisticsList.set(j+1, temp1);
					}
				}	
				list.add(statisticsList.get(size-1));
				statisticsList.remove(size-1);
			}
			break;
		case 3:
			for(int i=0;i<num;i++){
				int size=statisticsList.size();
				for(int j=0;j<size-1;j++){//冒泡排序
					if(statisticsList.get(j).getSteal()>statisticsList.get(j+1).getSteal()){
						PlayerSeasonStatistics temp1=statisticsList.get(j);
						PlayerSeasonStatistics temp2=statisticsList.get(j+1);
						statisticsList.set(j, temp2);
						statisticsList.set(j+1, temp1);
					}
				}	
				list.add(statisticsList.get(size-1));
				statisticsList.remove(size-1);
			}
			break;
		default:
			break;
		}
		return list;
	}

	/**
	 * @param statisticsList
	 * @param num	获取前num条记录
	 * @param type	按type进行排名：0代表得分；1代表篮板；2代表助攻；3代表抢断
	 * @return
	 */
	private List<PlayerMatchStatistics> playerSortByData(List<PlayerMatchStatistics> statisticsList,
			int num, int type) {
		// TODO Auto-generated method stub
		List<PlayerMatchStatistics> list=new ArrayList<PlayerMatchStatistics>();
		switch (type) {
		case 0:
			for(int i=0;i<num;i++){
				int size=statisticsList.size();
				for(int j=0;j<size-1;j++){//冒泡排序
					if(statisticsList.get(j).getScore()>statisticsList.get(j+1).getScore()){
						PlayerMatchStatistics temp1=statisticsList.get(j);
						PlayerMatchStatistics temp2=statisticsList.get(j+1);
						statisticsList.set(j, temp2);
						statisticsList.set(j+1, temp1);
					}
				}	
				list.add(statisticsList.get(size-1));
				statisticsList.remove(size-1);
			}
			break;
		case 1:
			for(int i=0;i<num;i++){
				int size=statisticsList.size();
				for(int j=0;j<size-1;j++){//冒泡排序
					if(statisticsList.get(j).getTotReb()>statisticsList.get(j+1).getTotReb()){
						PlayerMatchStatistics temp1=statisticsList.get(j);
						PlayerMatchStatistics temp2=statisticsList.get(j+1);
						statisticsList.set(j, temp2);
						statisticsList.set(j+1, temp1);
					}
				}	
				list.add(statisticsList.get(size-1));
				statisticsList.remove(size-1);
			}
			break;
		case 2:
			for(int i=0;i<num;i++){
				int size=statisticsList.size();
				for(int j=0;j<size-1;j++){//冒泡排序
					if(statisticsList.get(j).getAss()>statisticsList.get(j+1).getAss()){
						PlayerMatchStatistics temp1=statisticsList.get(j);
						PlayerMatchStatistics temp2=statisticsList.get(j+1);
						statisticsList.set(j, temp2);
						statisticsList.set(j+1, temp1);
					}
				}	
				list.add(statisticsList.get(size-1));
				statisticsList.remove(size-1);
			}
			break;
		case 3:
			for(int i=0;i<num;i++){
				int size=statisticsList.size();
				for(int j=0;j<size-1;j++){//冒泡排序
					if(statisticsList.get(j).getSteal()>statisticsList.get(j+1).getSteal()){
						PlayerMatchStatistics temp1=statisticsList.get(j);
						PlayerMatchStatistics temp2=statisticsList.get(j+1);
						statisticsList.set(j, temp2);
						statisticsList.set(j+1, temp1);
					}
				}	
				list.add(statisticsList.get(size-1));
				statisticsList.remove(size-1);
			}
			break;
		default:
			break;
		}
		return list;
	}
	
	@Override
	public List<PlayerSeasonStatistics> getTotalPlayerSeasonStatistics() {
		// TODO Auto-generated method stub
		return playerSeasonStatisticsDao.find("from PlayerSeasonStatistics");
	}

	@Override
	public PlayerAdvancedStatistics getPlayerSeasonAdvancedStatistics(
			int playerId) {
		// TODO Auto-generated method stub
		Player player=playerDao.get(Player.class, playerId);
		Team team=teamDao.get(Team.class,player.getTeamId());
		List<PlayerMatchStatistics> dataList=getPlayerMatchStatistics(playerId, CommonDataManager.SEASON);
		int totMatches=dataList.size();
		if(totMatches==0){
			return null;//跳过未上场球员
		}
		PlayerAdvancedStatistics statistics=new PlayerAdvancedStatistics(player, team, dataList, CommonDataManager.SEASON);
		return statistics;
	}
	
	@Override
	public List<PlayerAdvancedStatistics> getTotalPlayerSeasonAdvancedStatistics() {
		// TODO Auto-generated method stub
		return playerAdvancedStatisticsDao.find("from PlayerAdvancedStatistics");
	}

	@Override
	public List<Integer> getPlayerSeasonPERValues(int playerId) {
		// TODO Auto-generated method stub
		List<Integer> list=new ArrayList<Integer>();
		List<PlayerMatchStatistics> dataList=getPlayerMatchStatistics(playerId, CommonDataManager.SEASON);
		for (PlayerMatchStatistics s : dataList) {
			int per=(s.getScore()+s.getTotReb()+s.getAss()+s.getSteal()+s.getBlockShot())-(s.getTwoShot()+s.getThreeShot()
					+s.getFreeThrowShot()-s.getTwoHit()-s.getThreeHit()-s.getFreeThrowHit())-s.getTurnOver();
			list.add(per);
		}
		return list;
	}

	

}
