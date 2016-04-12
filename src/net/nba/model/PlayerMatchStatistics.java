package net.nba.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author sunfan314
 *matchId			比赛id
 * playerId			球员id
 * playerName		球员名
 * teamId			球员所在球队id
 * isFirst			是否首发(0代表首发，1代表替补)
 * time 			上场时间
 * twoHit			两分球命中
 * twoShot			两分球出手次数
 * threeHit			三分球命中
 * threeShot		三分球出手次数
 * freeThrowHit		罚球命中
 * freeThrowShot	罚球次数
 * offReb			前场篮板
 * defReb			后场篮板
 * totReb			总篮板
 * ass				助攻
 * steal			抢断
 * blockShot		盖帽
 * turnOver			失误
 * foul				犯规
 * score			得分
 */
@Entity
@Table(name = "player_match_statistics")
@IdClass(net.nba.model.PlayerMatchStatisticsPK.class)
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class PlayerMatchStatistics {
	@Id
	private int matchId;
	@Id
	private int playerId;

	private String playerName;

	private int teamId;

	private int isFirst;

	private int time;

	private int twoHit;

	private int twoShot;

	private int threeHit;

	private int threeShot;

	private int freeThrowHit;

	private int freeThrowShot;

	private int offReb;

	private int defReb;

	private int totReb;

	private int ass;

	private int steal;

	private int blockShot;

	private int turnOver;

	private int foul;

	private int score;
	
	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
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

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getIsFirst() {
		return isFirst;
	}

	public void setIsFirst(int isFirst) {
		this.isFirst = isFirst;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getTwoHit() {
		return twoHit;
	}

	public void setTwoHit(int twoHit) {
		this.twoHit = twoHit;
	}

	public int getTwoShot() {
		return twoShot;
	}

	public void setTwoShot(int twoShot) {
		this.twoShot = twoShot;
	}

	public int getThreeHit() {
		return threeHit;
	}

	public void setThreeHit(int threeHit) {
		this.threeHit = threeHit;
	}

	public int getThreeShot() {
		return threeShot;
	}

	public void setThreeShot(int threeShot) {
		this.threeShot = threeShot;
	}

	public int getFreeThrowHit() {
		return freeThrowHit;
	}

	public void setFreeThrowHit(int freeThrowHit) {
		this.freeThrowHit = freeThrowHit;
	}

	
	public int getFreeThrowShot() {
		return freeThrowShot;
	}

	public void setFreeThrowShot(int freeThrowShot) {
		this.freeThrowShot = freeThrowShot;
	}

	public int getOffReb() {
		return offReb;
	}

	public void setOffReb(int offReb) {
		this.offReb = offReb;
	}

	public int getDefReb() {
		return defReb;
	}

	public void setDefReb(int defReb) {
		this.defReb = defReb;
	}

	public int getTotReb() {
		return totReb;
	}

	public void setTotReb(int totReb) {
		this.totReb = totReb;
	}

	public int getAss() {
		return ass;
	}

	public void setAss(int ass) {
		this.ass = ass;
	}

	public int getSteal() {
		return steal;
	}

	public void setSteal(int steal) {
		this.steal = steal;
	}

	public int getBlockShot() {
		return blockShot;
	}

	public void setBlockShot(int blockShot) {
		this.blockShot = blockShot;
	}

	public int getTurnOver() {
		return turnOver;
	}

	public void setTurnOver(int turnOver) {
		this.turnOver = turnOver;
	}

	public int getFoul() {
		return foul;
	}

	public void setFoul(int foul) {
		this.foul = foul;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
