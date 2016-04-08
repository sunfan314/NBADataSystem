package net.nba.dataSpider;

import java.util.List;

import net.nba.model.Team;
import net.nba.model.TeamSeasonRank;

public interface TeamInfoSpider {
	
	public List<Team> getTeamInfoList();//从网页获取球队基本信息,基本数据的更新周期较长，一般一个赛季更新一次
	
	public List<TeamSeasonRank> getTeamSeasonRanks();//从网页获取球队赛季联盟排行，球队赛季排行更新周期较短，在一场比赛过后就要进行更新

}
