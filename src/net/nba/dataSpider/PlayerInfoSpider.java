package net.nba.dataSpider;

import java.util.List;

import net.nba.model.Player;
import net.nba.model.PlayerInfoDetail;

public interface PlayerInfoSpider {
	
	public List<Player> getTeamPlayerList();//从网页获取球队球员列表
	
	public void downloadPlayerPic(List<Integer> idList);//根据球员英文名访问球员信息页面下载球员图片
	
	public List<PlayerInfoDetail> getPlayerInfoDetail(List<Integer> idList);//根据球员id从网页获取球员详细信息列表

}
