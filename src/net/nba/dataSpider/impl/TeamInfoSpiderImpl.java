package net.nba.dataSpider.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import net.nba.dao.BaseDao;
import net.nba.dataSpider.TeamInfoSpider;
import net.nba.model.Team;
import net.nba.model.TeamSeasonRank;
import net.nba.util.DataSourceUrl;
import net.nba.util.WebPageReader;

/**
 * @author sunfan314
 *从网页获取球队信息的爬虫工具
 */
@Service("teamInfoSpider")
public class TeamInfoSpiderImpl implements TeamInfoSpider {
	private Pattern pattern;
	private Matcher matcher;

	@Override
	public List<Team> getTeamInfoList() {
		// TODO Auto-generated method stub
		// 从网页获取球队基本信息
		List<Team> teamList = new ArrayList<Team>();
		// conferences=conferenceDao.find("from Conference");
		for (int i = 1; i < 31; i++) {// 球队id为1至30
			Team team = new Team(i);
			String urlStr = DataSourceUrl.getTeamInfoURL(i);
			StringBuffer webPageBuffer = WebPageReader.readWebPage(urlStr);
			pattern = Pattern
					.compile("(.*)(<td width=80 height=\"21\">队　　名</td>	<td width=190 align=\"left\" style=\"padding-left:10px;\">)(.*?)(队 </td>)(.*)");
			matcher = pattern.matcher(webPageBuffer);
			if (matcher.matches()) {// 获取球队名
				team.setName(matcher.group(3));
			}
			pattern = Pattern
					.compile("(.*)(<td height=\"21\">城　　市</td>	<td align=\"left\" style=\"padding-left:10px;\">)(.*?)(</td>)(.*)");
			matcher = pattern.matcher(webPageBuffer);
			if (matcher.matches()) {// 获取城市名
				team.setCity(matcher.group(3));
			}
			pattern = Pattern
					.compile("(.*)(<td height=\"21\">分　　区</td>	<td align=\"left\" style=\"padding-left:10px;\">)(.*?)(</td>)(.*)");
			matcher = pattern.matcher(webPageBuffer);
			if (matcher.matches()) {// 获取分区信息和球队东西部信息
				String conferenceStr = matcher.group(3);
				String[] temp = conferenceStr.split("\\s+");
				String league = temp[0];
				String conference = temp[1];
				team.setLeague(league);
				team.setConference(conference);
			}
			pattern = Pattern
					.compile("(.*)(<td height=\"21\">球　　场</td>	<td align=\"left\" style=\"padding-left:10px;\">)(.*?)(</td>)(.*)");
			matcher = pattern.matcher(webPageBuffer);
			if (matcher.matches()) {// 获取球队球场
				team.setCourt(matcher.group(3));
			}
			pattern = Pattern
					.compile("(.*)(<td height=\"21\">进入 NBA</td><td align=\"left\" style=\"padding-left:10px;\">)(.*?)(年</td>)(.*)");
			matcher = pattern.matcher(webPageBuffer);
			if (matcher.matches()) {// 获取球队进入年份
				team.setStartYearInNBA(Integer.parseInt(matcher.group(3)));
			}
			pattern = Pattern
					.compile("(.*)(<td height=\"21\">总冠军数</td><td align=\"left\" style=\"padding-left:10px;\">)(.*?)(</td>)(.*)");
			matcher = pattern.matcher(webPageBuffer);
			if (matcher.matches()) {// 获取球队获取总冠军数
				team.setNumOfChampions(Integer.parseInt(matcher.group(3)));
			}

			teamList.add(team);
		}

		return teamList;
	}

	@Override
	public List<TeamSeasonRank> getTeamSeasonRanks() {
		// TODO Auto-generated method stub
		// 获取球队赛季联盟排行信息
		List<TeamSeasonRank> ranks = new ArrayList<TeamSeasonRank>();
		String urlStr = DataSourceUrl.TEAMSEASONRANKSURL;
		StringBuffer webPageBuffer = WebPageReader.readWebPage(urlStr);
		pattern = Pattern
				.compile("(.*)(<table width=\"950\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"1\" class=\"text\">)(.*?)(</table>)(.*)");
		matcher = pattern.matcher(webPageBuffer);
		if (matcher.matches()) {
			String str1 = matcher.group(3);
			/*
			 * 正则表达式 前后不可以加(.*) 否则无法匹配到所有满足条件的子序列
			 */
			pattern = Pattern
					.compile("(<tr bgcolor=)(.*?)(onMouseOut=)(.*?)(</tr>)");
			matcher = pattern.matcher(str1);
			int count = 1;// 记录计数
			while (matcher.find()) {
				String str2 = matcher.group(4);
				TeamSeasonRank rank = new TeamSeasonRank();
				Pattern pattern1 = Pattern
						.compile("(.*)(<a)(.*?)(id=)(.*?)(\")(.*)");
				Matcher matcher1 = pattern1.matcher(str2);
				if (matcher1.matches()) {// 获取球队id
					rank.setTeamId(Integer.parseInt(matcher1.group(5)));
				}
				pattern1 = Pattern
						.compile("(.*)(<td height=\"25\" align=center>)(.*?)(</td>)(.*)");
				matcher1 = pattern1.matcher(str2);
				if (matcher1.matches()) {// 获取球队排名
					rank.setRank(Integer.parseInt(matcher1.group(3)));
				}
				pattern1 = Pattern
						.compile("(.*)(<a href)(.*?)(>)(<font color=\"#ff0000\">)?(.*?)(</font>)?(</a>)(.*)");
				matcher1 = pattern1.matcher(str2);
				if (matcher1.matches()) {// 获取球队名
					rank.setName(matcher1.group(6).toString());
				}
				if (count < 16) {// 前15支球队为东部球队，league设置为0
					rank.setLeague(0);
				} else {// 后15支球队为西部球队，league设置为1
					rank.setLeague(1);
				}
				pattern1 = Pattern.compile("(.*)(<a href=)(.*?)(</td>)(.*)");
				matcher1 = pattern1.matcher(str2);
				if (matcher1.matches()) {// 获取球队胜场、负场、胜率、胜场差、场均得分、场均失分
					String str3 = matcher1.group(5);
					pattern1 = Pattern.compile("(<td>)(.*?)(</td>)");
					matcher1 = pattern1.matcher(str3);
					if (matcher1.find()) {
						rank.setWins(Integer.parseInt(matcher1.group(2)));
					}
					if (matcher1.find()) {
						rank.setLoses(Integer.parseInt(matcher1.group(2)));
					}
					if (matcher1.find()) {
						rank.setWinRate(matcher1.group(2));
					}
					if (matcher1.find()) {
						rank.setGamesBehind(Double.parseDouble(matcher1
								.group(2)));
					}
					if (matcher1.find()) {
						rank.setPspg(Double.parseDouble(matcher1.group(2)));
					}
					if (matcher1.find()) {
						rank.setPapg(Double.parseDouble(matcher1.group(2)));
					}
				}
				count++;
				ranks.add(rank);
			}
		}
		return ranks;
	}

}
