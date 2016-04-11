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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * id					比赛编号
 * vId					客队id
 * visitingTeam			客队名
 * hId					主队id
 * homeTeam				主队名
 * visitingScore   		客队得分
 * homeScore			主队得分
 * type					比赛类型（常规赛:0、季后赛:1、季前赛：2）
 * season				赛季信息（例：2015-2016，一般赛季从10月开始季前赛，4月进入季后赛，6月决出总冠军）
 * date					比赛日期
 * time 				比赛结束时间
 */
@Entity
@Table(name = "match_info")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Match {
	@Id
	private int id;

	private int vId;

	private String visitingTeam;

	private int hId;

	private String homeTeam;

	private int visitingScore;

	private int homeScore;

	private int type;

	@JsonIgnore
	private String season;

	private String date;

	private int year;

	private String time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getvId() {
		return vId;
	}

	public void setvId(int vId) {
		this.vId = vId;
	}

	public String getVisitingTeam() {
		return visitingTeam;
	}

	public void setVisitingTeam(String visitingTeam) {
		this.visitingTeam = visitingTeam;
	}

	public int gethId() {
		return hId;
	}

	public void sethId(int hId) {
		this.hId = hId;
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
