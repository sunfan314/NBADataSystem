package net.nba.util;

/**
 * @author sunfan314
 *数据源URL配置信息
 */
public class DataSourceUrl {

	private static String TEAMINFOURL = "http://nba.sports.sina.com.cn/team.php?id=";// 球队信息URL

	private static String PLAYERDETAILINFOURL = "http://nba.sports.sina.com.cn/player.php?id=";// 球员信息页面URL
	
	private static String MATCHSTATISTICSURL = "http://nba.sports.sina.com.cn/look_scores.php?id=";//比赛数据统计页面

	public static String TEAMSEASONRANKSURL = "http://nba.sports.sina.com.cn/league_order1.php?&dpc=1";// 球队赛季联盟排行URL

	public static String PLAYERLISTURL = "http://nba.sports.sina.com.cn/players.php?dpc=1";// 球员列表信息页面，用于获取球员id信息

	/*
	 * 比赛列表信息页面，example:http://nba.sports.sina.com.cn/match_result.php?day=0&years=2015&months=10&teams=
	 */
	private static String SEASONMATCHLISTURL = "http://nba.sports.sina.com.cn/match_result.php?day=0&years=PARAM1&months=PARAM2&teams=";

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
	
	public static String getMatchListURL(int year,int month){
		/*
		 * 获取某月比赛信息页面URL
		 */
		String str=SEASONMATCHLISTURL.replaceAll("PARAM1", String.valueOf(year));
		if(month<10){
			return str.replaceAll("PARAM2", "0"+String.valueOf(month));
		}else{
			return str.replaceAll("PARAM2", String.valueOf(month));
		}
	}
	
	public static String getMatchStatisticsURL(int matchId){
		return MATCHSTATISTICSURL+String.valueOf(matchId);
	}

}
