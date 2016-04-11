package net.nba.dataSpider;

import java.util.List;

import net.nba.model.Player;
import net.nba.model.PlayerInfoDetail;

/**
 * @author sunfan314
 *
 */
public interface PlayerInfoSpider {
	
	/**
	 * @return	从网页获取的球队阵容列表
	 */
	public List<Player> getTeamPlayerList();
	
	/**
	 * @param idList	球员id列表
	 * 从网页下载球员图片
	 */
	public void downloadPlayerPic(List<Integer> idList);
	
	/**
	 * @param idList	球员id列表
	 * @return	从网页获取的球员详细信息列表
	 */
	public List<PlayerInfoDetail> getPlayerInfoDetail(List<Integer> idList);

}
