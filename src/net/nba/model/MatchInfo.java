package net.nba.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * id						比赛编号
 * homeTeam					主队
 * visitingTeam				客队
 * homeScore				主队得分
 * visitingScore   			客队得分
 * season					赛季信息
 * date						比赛日期
 * teamMatchInfos			俩队详细比赛信息
 * playerMatchInfos			球员比赛详细数据
 */
@Entity
@Table(name = "match_info")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class MatchInfo {
	@Id
	private int id;

	@Column(name = "home_team_id")
	private int homeTeamId;

	@Column(name = "home_score")
	private int homeScore;

	@Column(name = "visiting_team_id")
	private int visitingTeamId;

	@Column(name = "visiting_score")
	private int visitingScore;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "season_id", unique = true)
	private Season season;

	@Column(name = "match_date")
	private String date;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumns(value = { @JoinColumn(name = "match_id", referencedColumnName = "id") })
	private List<TeamMatchInfo> teamMatchInfos;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumns(value = { @JoinColumn(name = "match_id", referencedColumnName = "id") })
	private List<PlayerMatchInfo> playerMatchInfos;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHomeTeamId() {
		return homeTeamId;
	}

	public void setHomeTeamId(int homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public int getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}

	public int getVisitingTeamId() {
		return visitingTeamId;
	}

	public void setVisitingTeamId(int visitingTeamId) {
		this.visitingTeamId = visitingTeamId;
	}

	public int getVisitingScore() {
		return visitingScore;
	}

	public void setVisitingScore(int visitingScore) {
		this.visitingScore = visitingScore;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<TeamMatchInfo> getTeamMatchInfos() {
		return teamMatchInfos;
	}

	public void setTeamMatchInfos(List<TeamMatchInfo> teamMatchInfos) {
		this.teamMatchInfos = teamMatchInfos;
	}

	public List<PlayerMatchInfo> getPlayerMatchInfos() {
		return playerMatchInfos;
	}

	public void setPlayerMatchInfos(List<PlayerMatchInfo> playerMatchInfos) {
		this.playerMatchInfos = playerMatchInfos;
	}

}
