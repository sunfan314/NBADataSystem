package net.nba.dataSpider;

import java.util.List;

import net.nba.model.Team;
import net.nba.model.TeamSeasonRank;

/**
 * @author sunfan314
 *
 */
public interface TeamInfoSpider {

	/**
	 * @return 从网页获得的球队基本信息
	 */
	public List<Team> getTeamInfoList();

	/**
	 * @return 从网页获取的联盟赛季排行
	 */
	public List<TeamSeasonRank> getTeamSeasonRanks();

}
