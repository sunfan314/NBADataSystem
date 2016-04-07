package net.nba.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * 球队分区信息
 * id			分区id
 * name			分区名
 */
@Entity
@Table(name = "conference")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Conference {
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
