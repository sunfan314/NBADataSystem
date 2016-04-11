package net.nba.dataSpider.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import net.nba.dataSpider.MatchInfoSpider;
import net.nba.model.Match;
import net.nba.util.CommonDataManager;
import net.nba.util.DataSourceUrl;
import net.nba.util.WebPageReader;

@Service("matchInfoSpider")
/*
 * 从网页获取比赛相关数据
 */
public class MatchInfoSpiderImpl implements MatchInfoSpider{
	private Pattern pattern;
	private Matcher matcher;

	@Override
	public List<Match> getSeasonMatchList() {
		// TODO Auto-generated method stub
		List<Match> list=new ArrayList<Match>();
		int seasonStartYear=CommonDataManager.SEASONSTARTYEAR;
		int seasonEndYear=CommonDataManager.SEASONENDYEAR;
		/*
		 * 获取赛季比赛列表
		 * 赛季：一般赛季从10月开始进入季前赛，4月进入季后赛，6月决出总冠军
		 */
		for(int i=10;i<13;i++){
			for (Match match : getMonthMatchList(seasonStartYear, i)) {
				match.setSeason(seasonStartYear+"-"+seasonEndYear);
				match.setYear(seasonStartYear);
				list.add(match);
			}
		}
		for(int i=1;i<7;i++){
			for (Match match : getMonthMatchList(seasonEndYear, i)) {
				match.setSeason(seasonStartYear+"-"+seasonEndYear);
				match.setYear(seasonEndYear);
				list.add(match);
			}
		}
		return list;
	}
	
	private List<Match> getMonthMatchList(int year,int month){
		//获取某月比赛列表
		List<Match> list=new ArrayList<Match>();
		String urlStr=DataSourceUrl.getSeasonMatchListURL(year, month);
		StringBuffer webPageBuffer=WebPageReader.readWebPage(urlStr);
		pattern=Pattern.compile("(.*)(<!-- 赛程内容开始 -->)(.*?)(<!-- 赛程内容end-->)(.*)");
		matcher=pattern.matcher(webPageBuffer);
		if(matcher.matches()){
			String str1=matcher.group(3);
			pattern=Pattern.compile("(<tr)(.*?)(</tr>)");
			matcher=pattern.matcher(str1);
			String date="";
			while(matcher.find()){
				String str2=matcher.group(2);
				Pattern pattern1=Pattern.compile("([0-9]{2})(月)([0-9]{2})(日)");
				Matcher matcher1=pattern1.matcher(str2);
				if(matcher1.find()){//日期栏目
					date=matcher1.group(0);
				}else{//比赛栏目
					pattern1=Pattern.compile("(.*)(<td height=\"25\">)(.*?)(</td>)(.*)");
					matcher1=pattern1.matcher(str2);
					if(matcher1.matches()){
						String str3=matcher1.group(3);
						String[] temp=str3.split("\\s+");
						if(temp[1].equals("未赛")){//跳过尚未开始的比赛
							continue;
						}
						Match match=new Match();
						match.setDate(date);//设置比赛日期
						match.setTime(temp[0]);//设置比赛时间
						String str4=matcher1.group(5);
						pattern1=Pattern.compile("(<td>)(.*?)(</td>)");
						matcher1=pattern1.matcher(str4);
						if(matcher1.find()){//设置比赛类型
							if(matcher1.group(2).equals("常规赛")){
								match.setType(0);
							}else if(matcher1.group(2).equals("季后赛")){
								match.setType(1);
								
							}else if(matcher1.group(2).equals("季前赛")){
								match.setType(2);
							}
						}
						pattern1=Pattern.compile("(<a href=\"team.php.id=)(.*?)(\" target=\"_blank\">)(.*?)(</a>)");
						matcher1=pattern1.matcher(str4);
						if(matcher1.find()){//设置客队id和客队名
							match.setvId(Integer.parseInt(matcher1.group(2)));
							match.setVisitingTeam(matcher1.group(4));
						}
						if(matcher1.find()){//设置主队id和主队名
							match.sethId(Integer.parseInt(matcher1.group(2)));
							match.setHomeTeam(matcher1.group(4));
						}
						pattern1=Pattern.compile("(<a href=\"http://sports.sina.com.cn/nba/live)(.*?)(>)(.*?)(</a>)");
						matcher1=pattern1.matcher(str4);
						if(matcher1.find()){//设置主队客队比分
							String[] temp1=matcher1.group(4).split("-");
							match.setVisitingScore(Integer.parseInt(temp1[0]));
							match.setHomeScore(Integer.parseInt(temp1[1]));
						}
						pattern1=Pattern.compile("(look_scores.php.id=)(.*?)(\")");
						matcher1=pattern1.matcher(str4);
						if(matcher1.find()){//设置比赛id
							match.setId(Integer.parseInt(matcher1.group(2)));
						}
						list.add(match);
					}
				}
			}
		}
		return list;
	}

}
