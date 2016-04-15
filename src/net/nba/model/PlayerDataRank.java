package net.nba.model;

/**
 * @author sunfan314
 *球员赛季数据/每日数据排行信息
 *id				球员id
 *name				球员姓名
 *teamName			所在球队
 *data				某日数据		
 *seasonData		赛季数据
 *type				数据类型			
 */
public class PlayerDataRank {
	
	private int id;
	
	private String name;
	
	private String teamName;
	
	private int data;
	
	private double seasonData;
	
	/**
	 * 0:得分	1：篮板	2：助攻	3：抢断
	 */
	private int type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public double getSeasonData() {
		return seasonData;
	}

	public void setSeasonData(double seasonData) {
		this.seasonData = seasonData;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	

}
