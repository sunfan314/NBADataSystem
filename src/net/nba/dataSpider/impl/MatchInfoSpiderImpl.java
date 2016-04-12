package net.nba.dataSpider.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.stereotype.Service;

import sun.print.resources.serviceui;
import net.nba.dataSpider.MatchInfoSpider;
import net.nba.model.Match;
import net.nba.model.PlayerMatchStatistics;
import net.nba.model.TeamMatchStatistics;
import net.nba.util.CommonDataManager;
import net.nba.util.DataSourceUrl;
import net.nba.util.MyLog;
import net.nba.util.WebPageReader;

/**
 * @author sunfan314 
 * 从网页爬取比赛信息（比赛基本信息、球队比赛数据统计、球员比赛数据统计等）的爬虫工具
 */
@Service("matchInfoSpider")
public class MatchInfoSpiderImpl implements MatchInfoSpider {
	private Pattern pattern;
	private Matcher matcher;

	@Override
	public List<Match> getSeasonMatchList() {
		// TODO Auto-generated method stub
		List<Match> list = new ArrayList<Match>();
		int seasonStartYear = CommonDataManager.SEASONSTARTYEAR;
		int seasonEndYear = CommonDataManager.SEASONENDYEAR;
		/*
		 * 获取赛季比赛列表 赛季：一般赛季从10月开始进入季前赛，4月进入季后赛，6月决出总冠军
		 */
		for (int i = 10; i < 13; i++) {
			for (Match match : getMonthMatchList(seasonStartYear, i)) {
				match.setSeason(seasonStartYear + "-" + seasonEndYear);
				match.setYear(seasonStartYear);
				list.add(match);
			}
		}
		for (int i = 1; i < 7; i++) {
			for (Match match : getMonthMatchList(seasonEndYear, i)) {
				match.setSeason(seasonStartYear + "-" + seasonEndYear);
				match.setYear(seasonEndYear);
				list.add(match);
			}
		}
		return list;
	}

	@Override
	public List<PlayerMatchStatistics> getPlayerMatchStatistics(
			List<Integer> matchIdList) {
		// TODO Auto-generated method stub
		List<PlayerMatchStatistics> list = new ArrayList<PlayerMatchStatistics>();
		for (Integer matchId : matchIdList) {
			try {
				String urlStr = DataSourceUrl.getMatchStatisticsURL(matchId);
				StringBuffer webPageBuffer = WebPageReader.readWebPage(urlStr);
				pattern = Pattern
						.compile("(<a href=\"team.php.id=)(.*?)(\" target)(.*?)(class=tlogo)");
				matcher = pattern.matcher(webPageBuffer);
				int visitingTeamId = -1;
				int homeTeamId = -1;
				if (matcher.find()) {// 获取客队id
					visitingTeamId = Integer.parseInt(matcher.group(2));
				}
				if (matcher.find()) {// 获取主队id
					homeTeamId = Integer.parseInt(matcher.group(2));
				}
				pattern = Pattern
						.compile("(<table width=\"702\" border=\"0\" align=\"center\" cellpadding=\"0\")(.*?)(</table>)");
				matcher = pattern.matcher(webPageBuffer);
				if (matcher.find()) {// 客队球员技术统计
					List<PlayerMatchStatistics> list1 = grabPlayerStatistics(matcher
							.group(2));
					for (PlayerMatchStatistics statistics : list1) {
						statistics.setTeamId(visitingTeamId);// 设置球队id
						statistics.setMatchId(matchId);//设置比赛id
						list.add(statistics);
					}
				}
				if (matcher.find()) {// 主队球员技术统计
					List<PlayerMatchStatistics> list1 = grabPlayerStatistics(matcher
							.group(2));
					for (PlayerMatchStatistics statistics : list1) {
						statistics.setTeamId(homeTeamId);// 设置球队id
						statistics.setMatchId(matchId);//设置比赛id
						list.add(statistics);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				MyLog.e("Grab PlayerMatchStatistics Data Error:", "matchId:"+matchId);
			}
		}
		return list;
	}
	
	@Override
	public List<TeamMatchStatistics> getTeamMatchStatistics(
			List<Integer> matchIdList) {
		// TODO Auto-generated method stub
		List<TeamMatchStatistics> list=new ArrayList<TeamMatchStatistics>();
		for (Integer matchId : matchIdList) {
			try {
				String urlStr = DataSourceUrl.getMatchStatisticsURL(matchId);
				StringBuffer webPageBuffer = WebPageReader.readWebPage(urlStr);
				pattern = Pattern
						.compile("(<a href=\"team.php.id=)(.*?)(\" target)(.*?)(class=tlogo)");
				matcher = pattern.matcher(webPageBuffer);
				int visitingTeamId = -1;
				int homeTeamId = -2;
				if (matcher.find()) {// 获取客队id
					visitingTeamId = Integer.parseInt(matcher.group(2));
				}
				if (matcher.find()) {// 获取主队id
					homeTeamId = Integer.parseInt(matcher.group(2));
				}
				pattern = Pattern
						.compile("(<table width=\"702\" border=\"0\" align=\"center\" cellpadding=\"0\")(.*?)(</table>)");
				matcher = pattern.matcher(webPageBuffer);
				if (matcher.find()) {// 客队数据统计
					TeamMatchStatistics statistics=grabTeamMatchStatistics(matcher.group(2));
					statistics.setMatchId(matchId);
					statistics.setTeamId(visitingTeamId);
					statistics.setIfHome(0);
					list.add(statistics);
				}
				if (matcher.find()) {// 主队数据统计
					TeamMatchStatistics statistics=grabTeamMatchStatistics(matcher.group(2));
					statistics.setMatchId(matchId);
					statistics.setTeamId(homeTeamId);
					statistics.setIfHome(1);
					list.add(statistics);
				}
			} catch (Exception e) {
				// TODO: handle exception
				MyLog.e("Grab TeamMatchStatistics Data Error!","matchId:"+matchId);
			}
			
		}
		return list;
	}


	/**
	 * @param year
	 * @param month
	 * @return 获取某年某月已完结比赛列表
	 */
	private List<Match> getMonthMatchList(int year, int month) {
		List<Match> list = new ArrayList<Match>();
		String urlStr = DataSourceUrl.getMatchListURL(year, month);
		StringBuffer webPageBuffer = WebPageReader.readWebPage(urlStr);
		pattern = Pattern
				.compile("(.*)(<!-- 赛程内容开始 -->)(.*?)(<!-- 赛程内容end-->)(.*)");
		matcher = pattern.matcher(webPageBuffer);
		if (matcher.matches()) {
			String str1 = matcher.group(3);
			pattern = Pattern.compile("(<tr)(.*?)(</tr>)");
			matcher = pattern.matcher(str1);
			String date = "";
			while (matcher.find()) {
				String str2 = matcher.group(2);
				Pattern pattern1 = Pattern
						.compile("([0-9]{2})(月)([0-9]{2})(日)");
				Matcher matcher1 = pattern1.matcher(str2);
				if (matcher1.find()) {// 日期栏目
					date = matcher1.group(0);
				} else {// 比赛栏目
					pattern1 = Pattern
							.compile("(.*)(<td height=\"25\">)(.*?)(</td>)(.*)");
					matcher1 = pattern1.matcher(str2);
					if (matcher1.matches()) {
						String str3 = matcher1.group(3);
						String[] temp = str3.split("\\s+");
						if (temp[1].equals("未赛")||temp[1].equals("延期")) {// 跳过尚未开始的比赛
							continue;
						}
						Match match = new Match();
						match.setDate(date);// 设置比赛日期
						match.setTime(temp[0]);// 设置比赛时间
						String str4 = matcher1.group(5);
						pattern1 = Pattern.compile("(<td>)(.*?)(</td>)");
						matcher1 = pattern1.matcher(str4);
						if (matcher1.find()) {// 设置比赛类型
							if (matcher1.group(2).equals("常规赛")) {
								match.setType(0);
							} else if (matcher1.group(2).equals("季后赛")) {
								match.setType(1);

							} else if (matcher1.group(2).equals("季前赛")) {
								/*
								 * 由于季前赛信息较为混乱，所以不计入比赛数据
								 */
								continue;
							}
						}
						pattern1 = Pattern
								.compile("(<a href=\"team.php.id=)(.*?)(\" target=\"_blank\">)(.*?)(</a>)");
						matcher1 = pattern1.matcher(str4);
						if (matcher1.find()) {// 设置客队id和客队名
							match.setvId(Integer.parseInt(matcher1.group(2)));
							match.setVisitingTeam(matcher1.group(4));
						}
						if (matcher1.find()) {// 设置主队id和主队名
							match.sethId(Integer.parseInt(matcher1.group(2)));
							match.setHomeTeam(matcher1.group(4));
						}
						pattern1 = Pattern
								.compile("(<a href=\"http://sports.sina.com.cn/nba/live)(.*?)(>)(.*?)(</a>)");
						matcher1 = pattern1.matcher(str4);
						if (matcher1.find()) {// 设置主队客队比分
							String[] temp1 = matcher1.group(4).split("-");
							match.setVisitingScore(Integer.parseInt(temp1[0]));
							match.setHomeScore(Integer.parseInt(temp1[1]));
						}
						pattern1 = Pattern
								.compile("(look_scores.php.id=)(.*?)(\")");
						matcher1 = pattern1.matcher(str4);
						if (matcher1.find()) {// 设置比赛id
							match.setId(Integer.parseInt(matcher1.group(2)));
						}
						list.add(match);
					}
				}
			}
		}
		return list;
	}

	/**
	 * @param group
	 * @return 从网页源码片段中获取球员比赛统计信息
	 */
	private List<PlayerMatchStatistics> grabPlayerStatistics(String str) {
		// TODO Auto-generated method stub
		List<PlayerMatchStatistics> list = new ArrayList<PlayerMatchStatistics>();
		Pattern pattern = Pattern.compile("(.*)(首发球员)(.*?)(替补球员)(.*)");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			String str1 = matcher.group(3);// 首发球员统计信息
			String str2 = matcher.group(5);// 替补球员统计信息
			pattern = Pattern
					.compile("(<tr bgcolor=\"#FFEFB6\" onMouseOut)(.*?)(</tr>)");
			matcher = pattern.matcher(str1);
			while (matcher.find()) {
				String str3 = matcher.group(2);
				PlayerMatchStatistics statistics = generatePlayerMatchStatistics(str3);
				statistics.setIsFirst(0);// 首发球员
				list.add(statistics);
			}
			matcher = pattern.matcher(str2);
			while (matcher.find()) {
				String str3 = matcher.group(2);
				if (str3.contains("没有上场") || str3.contains("未被激活")) {
					continue;
				}
				PlayerMatchStatistics statistics = generatePlayerMatchStatistics(str3);
				statistics.setIsFirst(1);// 替补球员
				list.add(statistics);
			}

		}
		return list;
	}

	/**
	 * @param str3
	 * @return 根据网页源码片段生成一条球员的比赛统计记录
	 */
	private PlayerMatchStatistics generatePlayerMatchStatistics(String str) {
		// TODO Auto-generated method stub
		PlayerMatchStatistics statistics = new PlayerMatchStatistics();
		Pattern pattern = Pattern
				.compile("(.*)(<a href=\"player_one.php.id=)(.*?)(\" targe)(.*?)(>)(.*?)(</a>)(.*)");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {// 设置球员id和姓名
			statistics.setPlayerId(Integer.parseInt(matcher.group(3)));
			statistics.setPlayerName(replaceEscapedCharacter(matcher.group(7)));
		}
		pattern = Pattern.compile("(<td>)(.*?)(</td>)");
		matcher = pattern.matcher(str);
		if (matcher.find()) {// 设置球员上场时间
			statistics.setTime(Integer.parseInt(matcher.group(2)));
		}
		if (matcher.find()) {// 设置球员投篮数和命中数
			String[] temp=matcher.group(2).split("-");
			statistics.setTwoHit(Integer.parseInt(temp[0]));;
			statistics.setTwoShot(Integer.parseInt(temp[1]));
		}
		if (matcher.find()) {// 设置球员三分出手数和命中数
			String[] temp=matcher.group(2).split("-");
			statistics.setThreeHit(Integer.parseInt(temp[0]));;
			statistics.setThreeShot(Integer.parseInt(temp[1]));
		}
		if (matcher.find()) {// 设置球员罚球出手数和命中数
			String[] temp=matcher.group(2).split("-");
			statistics.setFreeThrowHit(Integer.parseInt(temp[0]));;
			statistics.setFreeThrowShot(Integer.parseInt(temp[1]));
		}
		if (matcher.find()) {// 设置球员前场篮板
			statistics.setOffReb(Integer.parseInt(matcher.group(2)));
		}
		if (matcher.find()) {// 设置球员后场篮板
			statistics.setDefReb(Integer.parseInt(matcher.group(2)));
		}
		if (matcher.find()) {// 设置球员总篮板
			statistics.setTotReb(Integer.parseInt(matcher.group(2)));
		}
		if (matcher.find()) {// 设置球员助攻数
			statistics.setAss(Integer.parseInt(matcher.group(2)));
		}
		if (matcher.find()) {// 设置球员抢断数
			statistics.setSteal(Integer.parseInt(matcher.group(2)));
		}
		if (matcher.find()) {// 设置球员盖帽数
			statistics.setBlockShot(Integer.parseInt(matcher.group(2)));
		}
		if (matcher.find()) {// 设置球员失误数
			statistics.setTurnOver(Integer.parseInt(matcher.group(2)));
		}
		if (matcher.find()) {// 设置球员犯规数
			statistics.setFoul(Integer.parseInt(matcher.group(2)));
		}
		if (matcher.find()) {// 设置球员得分
			statistics.setScore(Integer.parseInt(matcher.group(2)));
		}
		return statistics;
	}
	
	/**
	 * @param str
	 * @return	从网页源码片段中获取球队比赛数据统计信息
	 */
	private TeamMatchStatistics grabTeamMatchStatistics(String str) {
		// TODO Auto-generated method stub
		TeamMatchStatistics statistics=new TeamMatchStatistics();
		Pattern pattern = Pattern
				.compile("(总计)(.*?)(</tr>)");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			String str1=matcher.group(2);
			pattern=Pattern.compile("(<td>)(.*?)(</td>)");
			matcher=pattern.matcher(str1);
			if (matcher.find()) {// 设置球队总上场时间
				statistics.setTime(Integer.parseInt(matcher.group(2)));
			}
			if (matcher.find()) {// 设置球队投篮数和命中数
				String[] temp=matcher.group(2).split("-");
				statistics.setTwoHit(Integer.parseInt(temp[0]));;
				statistics.setTwoShot(Integer.parseInt(temp[1]));
			}
			if (matcher.find()) {// 设置球队三分出手数和命中数
				String[] temp=matcher.group(2).split("-");
				statistics.setThreeHit(Integer.parseInt(temp[0]));;
				statistics.setThreeShot(Integer.parseInt(temp[1]));
			}
			if (matcher.find()) {// 设置球队罚球出手数和命中数
				String[] temp=matcher.group(2).split("-");
				statistics.setFreeThrowHit(Integer.parseInt(temp[0]));;
				statistics.setFreeThrowShot(Integer.parseInt(temp[1]));
			}
			if (matcher.find()) {// 设置球队前场篮板
				statistics.setOffReb(Integer.parseInt(matcher.group(2)));
			}
			if (matcher.find()) {// 设置球队后场篮板
				statistics.setDefReb(Integer.parseInt(matcher.group(2)));
			}
			if (matcher.find()) {// 设置球队总篮板
				statistics.setTotReb(Integer.parseInt(matcher.group(2)));
			}
			if (matcher.find()) {// 设置球队助攻数
				statistics.setAss(Integer.parseInt(matcher.group(2)));
			}
			if (matcher.find()) {// 设置球队抢断数
				statistics.setSteal(Integer.parseInt(matcher.group(2)));
			}
			if (matcher.find()) {// 设置球队盖帽数
				statistics.setBlockShot(Integer.parseInt(matcher.group(2)));
			}
			if (matcher.find()) {// 设置球队失误数
				statistics.setTurnOver(Integer.parseInt(matcher.group(2)));
			}
			if (matcher.find()) {// 设置球队犯规数
				statistics.setFoul(Integer.parseInt(matcher.group(2)));
			}
			if (matcher.find()) {// 设置球队得分
				statistics.setScore(Integer.parseInt(matcher.group(2)));
			}
			
		}
		return statistics;
	}
	
	/**
	 * @param str
	 * @return	
	 * 去除String中的转义字符\r \n \t
	 */
	private String replaceEscapedCharacter(String str) {
		String str1 = str.replaceAll("\r", "");
		String str2 = str1.replaceAll("\n", "");
		String str3 = str2.replaceAll("\t", "");
		return str3;
	}


}
