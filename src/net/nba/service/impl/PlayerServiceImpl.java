package net.nba.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.jasper.tagplugins.jstl.core.If;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Array;

import net.nba.dao.BaseDao;
import net.nba.dataSpider.PlayerInfoSpider;
import net.nba.model.Match;
import net.nba.model.Player;
import net.nba.model.PlayerDataRank;
import net.nba.model.PlayerInfoDetail;
import net.nba.model.PlayerMatchStatistics;
import net.nba.model.Team;
import net.nba.service.MatchService;
import net.nba.service.PlayerService;
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
	private BaseDao<PlayerInfoDetail> playInfoDetailDao;

	@Resource
	private BaseDao<PlayerMatchStatistics> playerMatchStatisticsDao;

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
			playInfoDetailDao.saveOrUpdate(playerInfoDetail);
		}
	}

	@Override
	public List<Player> getTeamPlayerList(int teamId) {
		// TODO Auto-generated method stub
		return playerDao.find("from Player where teamId = ?", teamId);
	}

	@Override
	public List<Player> getPlayers() {
		// TODO Auto-generated method stub
		return playerDao.find("from Player");
	}

	@Override
	public PlayerInfoDetail getPlayerInfoDetail(int playeId) {
		// TODO Auto-generated method stub
		return playInfoDetailDao.get(PlayerInfoDetail.class, playeId);
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
		List<PlayerMatchStatistics> playerSortByScore=sortByScore(statisticsList,5);
		List<PlayerMatchStatistics> playerSortByReb=sortByReb(statisticsList,5);
		List<PlayerMatchStatistics> playerSortByAss=sortByAss(statisticsList,5);
		List<PlayerMatchStatistics> playerSortBySteal=sortBySteal(statisticsList,5);
		

		for (PlayerMatchStatistics statistics : playerSortByScore) {
			PlayerDataRank rank = new PlayerDataRank();
			rank.setType(0);//得分
			rank.setData(statistics.getScore());
			rank.setId(statistics.getPlayerId());
			rank.setName(statistics.getPlayerName());
			Team team=teamDao.get(Team.class, statistics.getTeamId());
			rank.setTeamName(team.getName());
			playerDataRanks.add(rank);
		}
		
		for (PlayerMatchStatistics statistics : playerSortByReb) {
			PlayerDataRank rank = new PlayerDataRank();
			rank.setType(1);//篮板
			rank.setData(statistics.getTotReb());
			rank.setId(statistics.getPlayerId());
			rank.setName(statistics.getPlayerName());
			Team team=teamDao.get(Team.class, statistics.getTeamId());
			rank.setTeamName(team.getName());
			playerDataRanks.add(rank);
		}
		
		for (PlayerMatchStatistics statistics : playerSortByAss) {
			PlayerDataRank rank = new PlayerDataRank();
			rank.setType(2);//助攻
			rank.setData(statistics.getAss());
			rank.setId(statistics.getPlayerId());
			rank.setName(statistics.getPlayerName());
			Team team=teamDao.get(Team.class, statistics.getTeamId());
			rank.setTeamName(team.getName());
			playerDataRanks.add(rank);
		}
		
		for (PlayerMatchStatistics statistics : playerSortBySteal) {
			PlayerDataRank rank = new PlayerDataRank();
			rank.setType(3);//抢断
			rank.setData(statistics.getSteal());
			rank.setId(statistics.getPlayerId());
			rank.setName(statistics.getPlayerName());
			Team team=teamDao.get(Team.class, statistics.getTeamId());
			rank.setTeamName(team.getName());
			playerDataRanks.add(rank);
		}

		return playerDataRanks;
	}
	
	@Override
	public List<PlayerDataRank> getPlayerSeasonRanks() {
		// TODO Auto-generated method stub
		
		return null;
	}


	/**
	 * @param statisticsList
	 * @param num
	 * @return	从数据列表中选取前num项返回
	 */
	private List<PlayerMatchStatistics> sortByScore(
			List<PlayerMatchStatistics> statisticsList,int num) {
		// TODO Auto-generated method stub
		List<PlayerMatchStatistics> list=new ArrayList<PlayerMatchStatistics>();
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
		return list;
	}
	
	/**
	 * @param statisticsList
	 * @param num
	 * @return	从数据列表中选取前num项返回
	 */
	private List<PlayerMatchStatistics> sortByReb(
			List<PlayerMatchStatistics> statisticsList,int num) {
		// TODO Auto-generated method stub
		List<PlayerMatchStatistics> list=new ArrayList<PlayerMatchStatistics>();
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
		return list;
	}
	
	/**
	 * @param statisticsList
	 * @param num
	 * @return	从数据列表中选取前num项返回
	 */
	private List<PlayerMatchStatistics> sortByAss(
			List<PlayerMatchStatistics> statisticsList,int num) {
		// TODO Auto-generated method stub
		List<PlayerMatchStatistics> list=new ArrayList<PlayerMatchStatistics>();
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
		return list;
	}
	

	/**
	 * @param statisticsList
	 * @param num
	 * @return	从数据列表中选取前num项返回
	 */
	private List<PlayerMatchStatistics> sortBySteal(
			List<PlayerMatchStatistics> statisticsList,int num) {
		// TODO Auto-generated method stub
		List<PlayerMatchStatistics> list=new ArrayList<PlayerMatchStatistics>();
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
		return list;
	}

	
}
