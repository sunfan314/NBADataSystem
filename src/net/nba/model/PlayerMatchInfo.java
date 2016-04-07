package net.nba.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "player_match_info")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class PlayerMatchInfo {
	@Id
	private int id;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "player_id", unique = true)
	private Player player;

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

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
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
