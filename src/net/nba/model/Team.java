package net.nba.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author sunfan314
 *id				球队id
 * name				球队名
 * city				球队所在城市
 * league			球队所在东西部信息
 * conference		球队分区信息
 * court			球队球馆
 * startYearInNBA	球队进入NBA时间
 * numOfChampions	球队总冠军数
 */
@Entity
@Table(name = "team")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Team {
	@Id
	private int id;

	private String name;

	private String city;

	private String league;

	private String conference;

	private String court;

	private int startYearInNBA;

	private int numOfChampions;

	public Team() {

	}

	public Team(int id) {
		this.id = id;
	}

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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLeague() {
		return league;
	}

	public void setLeague(String league) {
		this.league = league;
	}

	public String getConference() {
		return conference;
	}

	public void setConference(String conference) {
		this.conference = conference;
	}

	public String getCourt() {
		return court;
	}

	public void setCourt(String court) {
		this.court = court;
	}

	public int getStartYearInNBA() {
		return startYearInNBA;
	}

	public void setStartYearInNBA(int startYearInNBA) {
		this.startYearInNBA = startYearInNBA;
	}

	public int getNumOfChampions() {
		return numOfChampions;
	}

	public void setNumOfChampions(int numOfChampions) {
		this.numOfChampions = numOfChampions;
	}

}
