package net.nba.dataSpider.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.stereotype.Service;

import net.nba.dataSpider.PlayerInfoSpider;
import net.nba.model.Player;
import net.nba.util.DataSourceUrl;
import net.nba.util.ImageDownloader;
import net.nba.util.WebPageReader;

@Service("playerInfoSpider")
public class PlayerInfoSpiderImpl implements PlayerInfoSpider {
	private Pattern pattern;
	private Matcher matcher;
	private static String fileFolderPath = "/Users/sunfan314/Documents/workspace/NBADataSystem/WebContent/resources/playerImgs";// 图片存储路径

	@Override
	public List<Player> getTeamPlayerList() {
		// TODO Auto-generated method stub
		List<Player> list = new ArrayList<Player>();
		int count = 1;
		for (int i = 1; i < 31; i++) {// 从各只球队网页阵容信息中依次获取球队球员信息
			String urlStr = DataSourceUrl.getTeamInfoURL(i);
			StringBuffer webPageBuffer = WebPageReader.readWebPage(urlStr);
			pattern = Pattern
					.compile("(.*)(<!-- 阵容 begin -->)(.*)(<!-- 阵容 end -->)(.*)");
			matcher = pattern.matcher(webPageBuffer);
			if (matcher.matches()) {
				String str1 = matcher.group(3);
				/*
				 * 正则表达式 前后不可以加(.*) 否则无法匹配到所有满足条件的子序列
				 */
				pattern = Pattern
						.compile("(<tr bgcolor=\"#.{6}\" onMouseOut)(.*?)(</tr>)");
				matcher = pattern.matcher(str1);
				while (matcher.find()) {
					Player player = new Player();
					player.setId(count);
					count++;
					player.setTeamId(i);
					String str2 = matcher.group(2);
					Pattern pattern1 = Pattern.compile("(<td>)(.*?)(</td>)");
					Matcher matcher1 = pattern1.matcher(str2);
					if (matcher1.find()) {// 获取球员号码
						player.setNum(Integer.parseInt(matcher1.group(2)));
					}
					if (matcher1.find()) {// 获取球员位置
						player.setPos(matcher1.group(2));
					}
					if (matcher1.find()) {// 获取球员身高
						String str3 = matcher1.group(2);
						Pattern pattern2 = Pattern.compile("(.*)(米)");
						Matcher matcher2 = pattern2.matcher(str3);
						if (matcher2.matches()) {
							player.setHeight(Double.parseDouble(matcher2
									.group(1)));
						}
					}
					if (matcher1.find()) {// 获取球员体重
						String str3 = matcher1.group(2);
						Pattern pattern2 = Pattern.compile("(.*)(公斤)");
						Matcher matcher2 = pattern2.matcher(str3);
						if (matcher2.matches()) {
							player.setWeight(Double.parseDouble(matcher2
									.group(1)));
						}
					}
					if (matcher1.find()) {// 获取球员年龄
						String str3 = matcher1.group(2);
						Pattern pattern2 = Pattern.compile("(.*)(岁)");
						Matcher matcher2 = pattern2.matcher(str3);
						if (matcher2.matches()) {
							player.setAge(Integer.parseInt(matcher2.group(1)));
						}
					}
					if (matcher1.find()) {// 获取球员生日
						player.setBirthday(matcher1.group(2));
					}
					if (matcher1.find()) {// 获取球员球龄
						String str3 = matcher1.group(2);
						Pattern pattern2 = Pattern.compile("(.*)(年)");
						Matcher matcher2 = pattern2.matcher(str3);
						if (matcher2.matches()) {
							player.setYearInNBA(Integer.parseInt(matcher2
									.group(1)));
						}
					}
					pattern1 = Pattern
							.compile("(<a href=\"http://nba.sports.sina.com.cn/star/)(.*?)(.shtml)");
					matcher1 = pattern1.matcher(str2);
					if (matcher1.find()) {// 获取球员英文名
						player.setNameInEn(matcher1.group(2));
					}
					pattern1 = Pattern
							.compile("(<a href)(.*?)(>)(.*?)(</a></td>)");
					matcher1 = pattern1.matcher(str2);
					if (matcher1.find()) {// 获取球员名
						player.setName(matcher1.group(4));
					}
					list.add(player);
				}
			}
		}
		return list;
	}

	@Override
	public void downloadPlayerPic(List<String> nameInEnList) {
		// TODO Auto-generated method stub
		// 根据球员英文名访问球员信息页面下载球员图片
		for (String name : nameInEnList) {
			String urlStr = DataSourceUrl.getPlayerDetailInfoURL(name);
			StringBuffer webPageBuffer = WebPageReader.readWebPage(urlStr);
			pattern = Pattern
					.compile("(.*)(<!-- 个人信息 begin -->)(.*?)(<!-- 个人信息 end -->)(.*)");
			matcher = pattern.matcher(webPageBuffer);
			if (matcher.matches()) {
				String str1 = matcher.group(3);
				pattern = Pattern.compile("(.*)(<img src=\")(.*?)(\")(.*)");
				matcher = pattern.matcher(str1);
				if (matcher.matches()) {
					ImageDownloader.downloadAndSaveImage(matcher.group(3),fileFolderPath, name);
				}
			}
		}
		
		

	}

}
