package net.nba.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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
 * 球队基本数据
 * id			球队id
 * name         球队名字
 * abbr         球队缩写
 * city         球队所在城市
 * league       球队所在赛区
 * conference   球队所在分区
 * sName        球队缩略名
 * founded      球队成立时间
 */
@Entity
@Table(name = "team")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Team {
	@Id
	private int id;

	private String name;

	private String abbr;

	private String city;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "leagueId", unique = true)
	private League league;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "conferenceId", unique = true)
	private Conference conference;

	private String sName;

	private int founded;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public Conference getConference() {
		return conference;
	}

	public void setConference(Conference conference) {
		this.conference = conference;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public int getFounded() {
		return founded;
	}

	public void setFounded(int founded) {
		this.founded = founded;
	}

}
