package net.nba.dataSpider.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.stereotype.Service;

import sun.launcher.resources.launcher;
import net.nba.dataSpider.PlayerInfoSpider;
import net.nba.model.Player;
import net.nba.model.PlayerInfoDetail;
import net.nba.util.DataSourceUrl;
import net.nba.util.FilePathManager;
import net.nba.util.ImageDownloader;
import net.nba.util.WebPageReader;

@Service("playerInfoSpider")
/*
 * 使用正则表达式爬取球员相关信息
 */
public class PlayerInfoSpiderImpl implements PlayerInfoSpider {
	private Pattern pattern;
	private Matcher matcher;

	@Override
	public List<Player> getTeamPlayerList() {
		// TODO Auto-generated method stub
		List<Player> list = new ArrayList<Player>();
		String urlStr1=DataSourceUrl.PLAYERLISTURL;
		//包含球员名和id映射的网页缓存
		StringBuffer webPageBuffer1 = WebPageReader.readWebPage(urlStr1);
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
							.compile("(<a href)(.*?)(>)(.*?)(</a></td>)");
					matcher1 = pattern1.matcher(str2);
					if (matcher1.find()) {// 获取球员名
						player.setName(matcher1.group(4));
						/*
						 * 根据球员名进一步获取球员id
						 * 获取球员id网址URL:http://nba.sports.sina.com.cn/players.php?dpc=1
						 */
						player.setId(getPlayerId(matcher1.group(4),webPageBuffer1));
					}
					list.add(player);
				}
			}
		}
		return list;
	}

	@Override
	public void downloadPlayerPic(List<Integer> idList) {
		// TODO Auto-generated method stub
		// 根据球员英文名访问球员信息页面下载球员图片
		for (Integer id : idList) {
			String urlStr = DataSourceUrl.getPlayerDetailInfoURL(id);
			StringBuffer webPageBuffer = WebPageReader.readWebPage(urlStr);
			pattern = Pattern
					.compile("(.*)(<!-- 个人信息 begin -->)(.*?)(<!-- 个人信息 end -->)(.*)");
			matcher = pattern.matcher(webPageBuffer);
			if (matcher.matches()) {
				String str1 = matcher.group(3);
				pattern = Pattern.compile("(.*)(<img src=\")(.*?)(\")(.*)");
				matcher = pattern.matcher(str1);
				if (matcher.matches()) {
					ImageDownloader.downloadAndSaveImage(matcher.group(3),
							FilePathManager.PLAYERIMGPATH, String.valueOf(id));
				}
			}
		}

	}

	@Override
	public List<PlayerInfoDetail> getPlayerInfoDetail(List<Integer> idList) {
		// TODO Auto-generated method stub
		// 根据球员英文名从网页获取球员详细信息列表
		List<PlayerInfoDetail> list = new ArrayList<PlayerInfoDetail>();
		for (Integer id : idList) {
			PlayerInfoDetail player = new PlayerInfoDetail();
			player.setId(id);// 设置球员id
			String urlStr = DataSourceUrl.getPlayerDetailInfoURL(id);
			StringBuffer webPageBuffer = WebPageReader.readWebPage(urlStr);
			pattern = Pattern
					.compile("(.*)(<!-- 个人信息 begin -->)(.*?)(<!-- 个人信息 end -->)(.*)");
			matcher = pattern.matcher(webPageBuffer);
			if (matcher.matches()) {
				String str1 = matcher.group(3);
				pattern = Pattern.compile("(<strong)(.*?)(>)(.*?)(</strong>)");
				matcher = pattern.matcher(str1);
				if (matcher.find()) {// 设置球员名
					player.setName(replaceEscapedCharacter(matcher.group(4)));
				}
				pattern = Pattern
						.compile("(生　　日</td>	<td)(.*?)(>)(.*?)(</td>)");
				matcher = pattern.matcher(str1);
				if (matcher.find()) {// 设置球员生日
					player.setBirthday(replaceEscapedCharacter(matcher.group(4)));
				}
				pattern = Pattern
						.compile("(年　　龄</td>	<td)(.*?)(>)(.*?)(</td>)");
				matcher = pattern.matcher(str1);
				if (matcher.find()) {// 设置球员年龄
					player.setAge(replaceEscapedCharacter(matcher.group(4)));
				}
				pattern = Pattern.compile("(出 生 地</td>	<)(.*?)(>)(.*?)(</td>)");
				matcher = pattern.matcher(str1);
				if (matcher.find()) {// 设置球员出生地
					player.setBirthPlace(replaceEscapedCharacter(matcher
							.group(4)));
				}
				pattern = Pattern.compile("(毕业学校</td>	<)(.*?)(>)(.*?)(</td>)");
				matcher = pattern.matcher(str1);
				if (matcher.find()) {// 设置球员大学
					player.setCollege(replaceEscapedCharacter(matcher.group(4)));
				}
				pattern = Pattern.compile("(身　　高</td><)(.*?)(>)(.*?)(</td>)");
				matcher = pattern.matcher(str1);
				if (matcher.find()) {// 设置球员身高
					player.setHeight(replaceEscapedCharacter(matcher.group(4)));
				}
				pattern = Pattern.compile("(体　　重</td><)(.*?)(>)(.*?)(</td>)");
				matcher = pattern.matcher(str1);
				if (matcher.find()) {// 设置球员体重
					player.setWeight(replaceEscapedCharacter(matcher.group(4)));
				}
				pattern = Pattern
						.compile("(进入 NBA</td>	<)(.*?)(>)(.*?)(</td>)");
				matcher = pattern.matcher(str1);
				if (matcher.find()) {// 设置球员进入NBA年份
					player.setStartInNBA(replaceEscapedCharacter(matcher
							.group(4)));
				}
				pattern = Pattern.compile("(NBA 球龄</td><)(.*?)(>)(.*?)(</td>)");
				matcher = pattern.matcher(str1);
				if (matcher.find()) {// 设置球员在NBA球龄
					player.setYearInNBA(replaceEscapedCharacter(matcher
							.group(4)));
				}
				pattern = Pattern.compile("(选秀情况</td><)(.*?)(>)(.*?)(</td>)");
				matcher = pattern.matcher(str1);
				if (matcher.find()) {// 设置球员选秀情况
					player.setDraftStatus(replaceEscapedCharacter(matcher
							.group(4)));
				}
				pattern = Pattern
						.compile("(赛季最高分</td>      <)(.*?)(>)(.*?)(</td>)");
				matcher = pattern.matcher(str1);
				if (matcher.find()) {// 设置球员赛季最高分
					player.setsHS(replaceEscapedCharacter(matcher.group(4)));
				}
				pattern = Pattern
						.compile("(生涯最高分</td>      <)(.*?)(>)(.*?)(</td>)");
				matcher = pattern.matcher(str1);
				if (matcher.find()) {// 设置球员生涯最高分
					player.setcHS(replaceEscapedCharacter(matcher.group(4)));
				}
			}
			list.add(player);
		}
		return list;
	}
	
	/*
	 * 在网页：http://nba.sports.sina.com.cn/players.php?dpc=1中根据球员英文名获取球员id
	 */
	private int getPlayerId(String name,StringBuffer webPageBuffer) {
		// TODO Auto-generated method stub
		Pattern p=Pattern.compile("(.*)(<!-- 页面主要内容 begin -->)(.*?)(<!-- 页面主要内容 end -->)(.*)");
		Matcher m=p.matcher(webPageBuffer);
		if(m.matches()){
			String str=m.group(3);
			p=Pattern.compile("(<a href='player.php.id=)(.*?)('>)(.*?)(,)(.*?)(</a>)");
			m=p.matcher(str);
			while(m.find()){
				if(replaceEscapedCharacter(m.group(4)).equals(name)){
					//System.out.println(m.group(2));
					return Integer.parseInt(m.group(2));
				}
			}			
		}
		return 0;
	}

	private String replaceEscapedCharacter(String str) {
		// 去除String中的转义字符\r \n \t
		String str1 = str.replaceAll("\r", "");
		String str2 = str1.replaceAll("\n", "");
		String str3 = str2.replaceAll("\t", "");
		return str3;
	}
	

}
