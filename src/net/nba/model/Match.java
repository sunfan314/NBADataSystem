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
 * id					比赛编号
 * visitingTeam			客队名
 * homeTeam				主队名
 * visitingScore   		客队得分
 * homeScore			主队得分
 * type					比赛类型（常规赛:0、季后赛:1）
 * season				赛季信息
 * date					比赛日期
 * time 				比赛结束时间
 */
@Entity
@Table(name = "match_info")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Match {
	@Id
	private int id;

	private String visitingTeam;

	private String homeTeam;

	private int visitingScore;

	private int homeScore;

	private int type;

	private String season;

	private String date;

	private String time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVisitingTeam() {
		return visitingTeam;
	}

	public void setVisitingTeam(String visitingTeam) {
		this.visitingTeam = visitingTeam;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public int getVisitingScore() {
		return visitingScore;
	}

	public void setVisitingScore(int visitingScore) {
		this.visitingScore = visitingScore;
	}

	public int getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(int homeScore) {
		this.homeScore = homeScore;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
