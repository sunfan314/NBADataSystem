package net.nba.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.jasper.tagplugins.jstl.core.If;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Array;

import net.nba.dao.BaseDao;
import net.nba.dataSpider.PlayerInfoSpider;
import net.nba.model.Player;
import net.nba.model.PlayerInfoDetail;
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
	private BaseDao<PlayerInfoDetail> playInfoDetailDao;
	
	@Resource
	private PlayerInfoSpider playerInfoSpider;

	@Override
	public void updateTeamPlayers() {
		// TODO Auto-generated method stub
		List<Player> list=playerInfoSpider.getTeamPlayerList();
		for (Player player : list) {
			if(player.getId()==0){
				String dataError="Can not get id of player: "+player.getName()+" from team "+player.getTeamId();
				MyLog.e(dataError);//在日志文件中记录无法获取id的球员信息
				
			}else{
				playerDao.saveOrUpdate(player);
			}
			
		}
	}
	
	@Override
	public void updatePlayerInfoDetail() {
		// TODO Auto-generated method stub
		List<Integer> list=new ArrayList<Integer>();
		List<Player> playerList=playerDao.find("from Player");
		for (Player player : playerList) {
			list.add(player.getId());
		}
		List<PlayerInfoDetail> playerInfoDetails=playerInfoSpider.getPlayerInfoDetail(list);
		for (PlayerInfoDetail playerInfoDetail : playerInfoDetails) {
			playInfoDetailDao.saveOrUpdate(playerInfoDetail);
		}
	}

	@Override
	public List<Player> getTeamPlayerList(int teamId) {
		// TODO Auto-generated method stub
		return playerDao.find("from Player where teamId = ?",teamId);
	}

	@Override
	public List<Player> getPlayers() {
		// TODO Auto-generated method stub
		return playerDao.find("from Player");
	}

	@Override
	public PlayerInfoDetail getPlayerInfoDetail(int playeId) {
		// TODO Auto-generated method stub
		return playInfoDetailDao.get(PlayerInfoDetail.class,playeId);
	}


//	
//	@Resource 
//	private BaseDao<PlayerMatchInfo> playerMatchInfoDao;
//
//	@Override
//	public List<Map<String, Object>> getSeasonPlayerRanks() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Map<String, Object>> getPlayerRanks(Date date) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public List<Map<String, Object>> getTeamPlayers(int teamId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public Map<String, Object> getPlayerSeasonStatistics(int playerId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	

}
