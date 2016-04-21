package net.nba.util;

/**
 * @author sunfan314 
 * 项目基本数据的配置
 */
public class CommonDataManager {

	public static int SEASONSTARTYEAR = 2015;

	public static int SEASONENDYEAR = 2016;

	public static String SEASON = "2015-2016";
	
	/**
	 * 一个赛季从10月开始进入季前赛，4月进入季后赛，6月决出总冠军
	 */
	public static int SEASONSTARTMONTH = 10;
	
	public static int SEASONENDMONTH = 6;
	
	/**
	 * @param year
	 * @param month
	 * @return	查看某月是否在当前赛季中
	 */
	public static boolean monthInSeason(int year,int month){
		if(year==SEASONSTARTYEAR){
			if(month>9&&month<13){
				return true;
			}
			else{
				return false;
			}
		}else if(year==SEASONENDYEAR){
			if(month>0&&month<7){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
	}

}
