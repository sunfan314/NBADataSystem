package net.nba.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import net.nba.util.CommonDataManager;
import net.nba.util.DoubleFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author sunfan314
 *teamId 			球队id
 *teamName 			球队名
 *season 			赛季信息
 *totalMatches 		比赛场次
 *twoHit 			场均两份命中数
 *twoShot 			场均两份出手次数
 *threeHit 			场均三分命中数
 *threeShot 		场均三分出手次数
 *freeThrowHit 		场均罚球次数
 *freeThrowShot 	场均罚球命中率
 *offReb 			场均进攻篮板个数
 *defReb 			场均防守篮板个数
 *totReb 			场均篮板数
 *ass 				场均助攻数
 *steal 			场均抢断数
 *blockShot 		场均盖帽数
 *turnOver 			场均失误
 *foul 				场均犯规
 *score 			场均得分
 */

@Entity
@Table(name = "team_season_statistics")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class TeamSeasonStatistics {
	@Id
	private int teamId;
	
	private String teamName;
	
	private String season;
	
	private int totalMatches;
	
	private double twoHit;
	
	private double twoShot;
	
	private double threeHit;
	
	private double threeShot;
	
	private double freeThrowHit;
	
	private double freeThrowShot;
	
	private double offReb;
	
	private double defReb;
	
	private double totReb;
	
	private double ass;
	
	private double steal;
	
	private double blockShot;
	
	private double turnOver;
	
	private double foul;
	
	private double score;
	
	public TeamSeasonStatistics() {
		super();
	}
	
	/**
	 * @param matchStatistics
	 * 根据比赛列表计算场均数据
	 */
	public TeamSeasonStatistics(Team team,List<TeamMatchStatistics> dataList,String season){
		super();
		this.setTeamId(team.getId());
		this.setTeamName(team.getName());
		this.setSeason(season);
		int totMatches=dataList.size();
		this.setTotalMatches(totMatches);
		int twoHitTot=0,twoShotTot=0,threeHitTot=0,threeShotTot=0;
		int freeThrowHitTot=0,freeThrowShotTot=0,offRebTot=0,defRebTot=0,totRebTot=0;
		int assTot=0,stealTot=0,blockShotTot=0,turnOverTot=0,foulTot=0,scoreTot=0;
		for (TeamMatchStatistics s : dataList) {
			twoHitTot=twoHitTot+s.getTwoHit();
			twoShotTot=twoShotTot+s.getTwoShot();
			threeHitTot=threeHitTot+s.getThreeHit();
			threeShotTot=threeShotTot+s.getThreeShot();
			freeThrowHitTot=freeThrowHitTot+s.getFreeThrowHit();
			freeThrowShotTot=freeThrowShotTot+s.getFreeThrowShot();
			offRebTot=offRebTot+s.getOffReb();
			defRebTot=defRebTot+s.getDefReb();
			totRebTot=totRebTot+s.getTotReb();
			assTot=assTot+s.getAss();
			stealTot=stealTot+s.getSteal();
			blockShotTot=blockShotTot+s.getBlockShot();
			turnOverTot=turnOverTot+s.getTurnOver();
			foulTot=foulTot+s.getFoul();
			scoreTot=scoreTot+s.getScore();			
		}
		this.setTwoHit(DoubleFormat.transfer((double)twoHitTot/totMatches));
		this.setTwoShot(DoubleFormat.transfer((double)twoShotTot/totMatches));
		this.setThreeHit(DoubleFormat.transfer((double)threeHitTot/totMatches));
		this.setThreeShot(DoubleFormat.transfer((double)threeShotTot/totMatches));
		this.setFreeThrowHit(DoubleFormat.transfer((double)freeThrowHitTot/totMatches));
		this.setFreeThrowShot(DoubleFormat.transfer((double)freeThrowShotTot/totMatches));
		this.setOffReb(DoubleFormat.transfer((double)offRebTot/totMatches));
		this.setDefReb(DoubleFormat.transfer((double)defRebTot/totMatches));
		this.setTotReb(DoubleFormat.transfer((double)totRebTot/totMatches));
		this.setAss(DoubleFormat.transfer((double)assTot/totMatches));
		this.setSteal(DoubleFormat.transfer((double)stealTot/totMatches));
		this.setBlockShot(DoubleFormat.transfer((double)blockShotTot/totMatches));
		this.setTurnOver(DoubleFormat.transfer((double)turnOverTot/totMatches));
		this.setFoul(DoubleFormat.transfer((double)foulTot/totMatches));
		this.setScore(DoubleFormat.transfer((double)scoreTot/totMatches));
		
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public int getTotalMatches() {
		return totalMatches;
	}

	public void setTotalMatches(int totalMatches) {
		this.totalMatches = totalMatches;
	}

	public double getTwoHit() {
		return twoHit;
	}

	public void setTwoHit(double twoHit) {
		this.twoHit = twoHit;
	}

	public double getTwoShot() {
		return twoShot;
	}

	public void setTwoShot(double twoShot) {
		this.twoShot = twoShot;
	}

	public double getThreeHit() {
		return threeHit;
	}

	public void setThreeHit(double threeHit) {
		this.threeHit = threeHit;
	}

	public double getThreeShot() {
		return threeShot;
	}

	public void setThreeShot(double threeShot) {
		this.threeShot = threeShot;
	}

	public double getFreeThrowHit() {
		return freeThrowHit;
	}

	public void setFreeThrowHit(double freeThrowHit) {
		this.freeThrowHit = freeThrowHit;
	}

	public double getFreeThrowShot() {
		return freeThrowShot;
	}

	public void setFreeThrowShot(double freeThrowShot) {
		this.freeThrowShot = freeThrowShot;
	}

	public double getOffReb() {
		return offReb;
	}

	public void setOffReb(double offReb) {
		this.offReb = offReb;
	}

	public double getDefReb() {
		return defReb;
	}

	public void setDefReb(double defReb) {
		this.defReb = defReb;
	}

	public double getTotReb() {
		return totReb;
	}

	public void setTotReb(double totReb) {
		this.totReb = totReb;
	}

	public double getAss() {
		return ass;
	}

	public void setAss(double ass) {
		this.ass = ass;
	}

	public double getSteal() {
		return steal;
	}

	public void setSteal(double steal) {
		this.steal = steal;
	}

	public double getBlockShot() {
		return blockShot;
	}

	public void setBlockShot(double blockShot) {
		this.blockShot = blockShot;
	}

	public double getTurnOver() {
		return turnOver;
	}

	public void setTurnOver(double turnOver) {
		this.turnOver = turnOver;
	}

	public double getFoul() {
		return foul;
	}

	public void setFoul(double foul) {
		this.foul = foul;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	
}
