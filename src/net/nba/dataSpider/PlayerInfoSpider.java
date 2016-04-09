package net.nba.dataSpider;

import java.util.List;

import net.nba.model.Player;

public interface PlayerInfoSpider {
	
	public List<Player> getTeamPlayerList();//从网页获取球队球员列表
	
	public void downloadPlayerPic(List<String> nameInEnList);//根据球员英文名访问球员信息页面下载球员图片

}
