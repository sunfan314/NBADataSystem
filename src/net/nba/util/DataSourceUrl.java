package net.nba.util;

public class DataSourceUrl {

	private static String TEAMINFOURL = "http://nba.sports.sina.com.cn/team.php?id=";// 球队信息URL
	
	private static String TEAMSEASONRANKS = "http://nba.sports.sina.com.cn/league_order1.php?&dpc=1";//球队赛季联盟排行URL

	public static String getTeamInfoURL(int teamId) {
		//根据id获取球队信息URL
		return TEAMINFOURL + String.valueOf(teamId);
	}
	
	public static String getTeamSeasonRanks(){
		return TEAMSEASONRANKS;
	}
	

}
