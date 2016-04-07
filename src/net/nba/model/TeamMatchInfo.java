package net.nba.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * id			球队比赛信息记录编号
 * teamId		比赛球队编号
 * matchId		对应比赛编号
 * 比赛信息：score;firstScore;secondScore;thirdScore;forthScore
 */

@Entity
@Table(name = "team_match_info")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class TeamMatchInfo {
	@Id
	private int id;

	@Column(name = "team_id")
	private int teamId;

	@Column(name = "match_id")
	private int matchId;

	@Column(name = "score")
	private int score;

	@Column(name = "1st_score")
	private int firstScore;

	@Column(name = "2nd_score")
	private int secondScore;

	@Column(name = "3rd_score")
	private int thirdScore;

	@Column(name = "4th_score")
	private int forthScore;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public int getMatchId() {
		return matchId;
	}

	public void setMatchId(int matchId) {
		this.matchId = matchId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getFirstScore() {
		return firstScore;
	}

	public void setFirstScore(int firstScore) {
		this.firstScore = firstScore;
	}

	public int getSecondScore() {
		return secondScore;
	}

	public void setSecondScore(int secondScore) {
		this.secondScore = secondScore;
	}

	public int getThirdScore() {
		return thirdScore;
	}

	public void setThirdScore(int thirdScore) {
		this.thirdScore = thirdScore;
	}

	public int getForthScore() {
		return forthScore;
	}

	public void setForthScore(int forthScore) {
		this.forthScore = forthScore;
	}

}
