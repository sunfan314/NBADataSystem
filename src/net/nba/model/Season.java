package net.nba.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * @author:Sunfan
 * id				赛季编号
 * season_des		赛季描述（例：2015~2016）	
 */
@Entity
@Table(name="season")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Season {
	@Id
	private int id;
	private String season_des;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSeason_des() {
		return season_des;
	}
	public void setSeason_des(String season_des) {
		this.season_des = season_des;
	}
	

}
