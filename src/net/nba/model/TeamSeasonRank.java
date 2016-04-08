package net.nba.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * teamId		球队id
 * rank			联盟排名
 * name 		球队名	
 * league 		球队所在联盟（0代表东部，1代表西部）
 * wins 		胜场
 * loses		负场
 * winRate		球队胜率
 * gamesbehind	胜场差
 * pspg			points scored per game 场均得分
 * papg			points allowed per game 场均失分
 */
@Entity
@Table(name = "team_ranks")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class TeamSeasonRank {
	@Id
	private int teamId;

	private int rank;

	private String name;

	private int league;

	private int wins;

	private int loses;

	private String winRate;

	private double gamesBehind;

	private double pspg;

	private double papg;

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLeague() {
		return league;
	}

	public void setLeague(int league) {
		this.league = league;
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public int getLoses() {
		return loses;
	}

	public void setLoses(int loses) {
		this.loses = loses;
	}

	public String getWinRate() {
		return winRate;
	}

	public void setWinRate(String winRate) {
		this.winRate = winRate;
	}

	public double getGamesBehind() {
		return gamesBehind;
	}

	public void setGamesBehind(double gamesBehind) {
		this.gamesBehind = gamesBehind;
	}

	public double getPspg() {
		return pspg;
	}

	public void setPspg(double pspg) {
		this.pspg = pspg;
	}

	public double getPapg() {
		return papg;
	}

	public void setPapg(double papg) {
		this.papg = papg;
	}

}
