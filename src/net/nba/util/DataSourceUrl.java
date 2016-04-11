package net.nba.util;

public class DataSourceUrl {

	private static String TEAMINFOURL = "http://nba.sports.sina.com.cn/team.php?id=";// 球队信息URL

	private static String PLAYERDETAILINFOURL = "http://nba.sports.sina.com.cn/player.php?id=";// 球员信息页面URL

	public static String TEAMSEASONRANKSURL = "http://nba.sports.sina.com.cn/league_order1.php?&dpc=1";// 球队赛季联盟排行URL

	public static String PLAYERLISTURL = "http://nba.sports.sina.com.cn/players.php?dpc=1";// 球员列表信息页面，用于获取球员id信息

	public static String MATCHLISTURL = "http://nba.sports.sina.com.cn/match_result.php?day=all";// 比赛列表信息页面

	public static String getTeamInfoURL(int teamId) {
		/*
		 * 根据id获取球队信息URL example:http://nba.sports.sina.com.cn/team.php?id=1
		 */
		return TEAMINFOURL + String.valueOf(teamId);
	}

	public static String getPlayerDetailInfoURL(int id) {
		/*
		 * 根据球员id获取球员信息URL
		 * example:http://nba.sports.sina.com.cn/player.php?id=4624
		 */
		return PLAYERDETAILINFOURL + String.valueOf(id);
	}

}
