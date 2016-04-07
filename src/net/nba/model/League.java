package net.nba.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



/*
 * 赛区信息：
 * id		赛区
 * name		赛区名
 */
@Entity
@Table(name="league")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class League {
	@Id
	private int id;
	private String name;
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
	

}
