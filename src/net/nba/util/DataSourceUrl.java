package net.nba.util;

public class DataSourceUrl {

	private static String TEAMINFOURL = "http://nba.sports.sina.com.cn/team.php?id=";// 球队信息URL
	
	private static String TEAMSEASONRANKSURL = "http://nba.sports.sina.com.cn/league_order1.php?&dpc=1";//球队赛季联盟排行URL
	
	private static String PLAYERDETAILINFOURL = "http://nba.sports.sina.com.cn/star/";//球员信息页面URL

	public static String getTeamInfoURL(int teamId) {
		/*
		 * 根据id获取球队信息URL
		 * example:http://nba.sports.sina.com.cn/team.php?id=1
		 */
		return TEAMINFOURL + String.valueOf(teamId);
	}
	
	public static String getTeamSeasonRanks(){
		return TEAMSEASONRANKSURL;
	}
	
	public static String getPlayerDetailInfoURL(String nameInEn){
		/*
		 * 根据球员英文名获取球员信息URL
		 * example:http://nba.sports.sina.com.cn/star/Jeff-Teague.shtml
		 */
		return PLAYERDETAILINFOURL+nameInEn+".shtml";
	}
	

}
