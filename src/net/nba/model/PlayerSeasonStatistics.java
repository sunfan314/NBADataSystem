package net.nba.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author sunfan314
 *playerId 			球员id
 *playerName 		球员姓名
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
