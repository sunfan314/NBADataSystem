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
 *playerId 			球员id
 *playerName 		球员姓名
 *season 			赛季信息
 *teamId 			球队id
 *teamName 			球队名
 *isFirst 			首发次数
 *totalMatches 		出场次数
 *time 				场均上场时间
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
@Table(name = "player_season_statistics")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class PlayerSeasonStatistics {
	@Id
	private int playerId;
	
	private String playerName;
	
	private String season;
	
	private int teamId;
	
	private String teamName;
	
	private int isFirst;
	
	private int totalMatches;
	
	private double time;
	
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
	
	public PlayerSeasonStatistics(){
		super();
	}
	
	public PlayerSeasonStatistics(Player player,Team team,List<PlayerMatchStatistics> dataList,String season){
		super();
		this.season=season;
		this.playerId=player.getId();
		this.playerName=player.getName();
		this.teamId=team.getId();
		this.teamName=team.getName();
		int totMatches=dataList.size();
		this.totalMatches=totMatches;
		int isFirstTot = 0,timeTot=0,twoHitTot=0,twoShotTot=0,threeHitTot=0,threeShotTot=0;
		int freeThrowHitTot=0,freeThrowShotTot=0,offRebTot=0,defRebTot=0,totRebTot=0;
		int assTot=0,stealTot=0,blockShotTot=0,turnOverTot=0,foulTot=0,scoreTot=0;
		for (PlayerMatchStatistics s : dataList) {
			if(s.getIsFirst()==0){//首发次数
				isFirstTot++;
			}
			timeTot=timeTot+s.getTime();
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
		this.isFirst=isFirstTot;
		this.time=DoubleFormat.transfer((double)timeTot/totMatches);
		this.twoHit=DoubleFormat.transfer((double)twoHitTot/totMatches);
		this.twoShot=DoubleFormat.transfer((double)twoShotTot/totMatches);
		this.threeHit=DoubleFormat.transfer((double)threeHitTot/totMatches);
		this.threeShot=DoubleFormat.transfer((double)threeShotTot/totMatches);
		this.freeThrowHit=DoubleFormat.transfer((double)freeThrowHitTot/totMatches);
		this.freeThrowShot=DoubleFormat.transfer((double)freeThrowShotTot/totMatches);
		this.offReb=DoubleFormat.transfer((double)offRebTot/totMatches);
		this.defReb=DoubleFormat.transfer((double)defRebTot/totMatches);
		this.totReb=DoubleFormat.transfer((double)totRebTot/totMatches);
		this.ass=DoubleFormat.transfer((double)assTot/totMatches);
		this.steal=DoubleFormat.transfer((double)stealTot/totMatches);
		this.blockShot=DoubleFormat.transfer((double)blockShotTot/totMatches);
		this.turnOver=DoubleFormat.transfer((double)turnOverTot/totMatches);
		this.foul=DoubleFormat.transfer((double)foulTot/totMatches);
		this.score=DoubleFormat.transfer((double)scoreTot/totMatches);
		

	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
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

	public int getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(int isFirst) {
		this.isFirst = isFirst;
	}

	public int getTotalMatches() {
		return totalMatches;
	}

	public void setTotalMatches(int totalMatches) {
		this.totalMatches = totalMatches;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
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
