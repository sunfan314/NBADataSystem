package net.nba.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * id			球员id（可根据id访问球员信息界面获取球员信息）
 * num			球员号码
 * teamId 		球员所属球队id
 * name			球员名(可用于获取球员id)	
 * pos			球员位置
 * height		球员身高（单位：米）
 * weight		球员体重（单位：公斤）
 * age			球员年龄
 * birthday 	球员生日（例：1993-3-3）
 * yearInNBA	球员NBA球龄
 */

@Entity
@Table(name = "player")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class Player {
	@Id
	private int id;

	private int num;

	private int teamId;

	private String name;

	private String pos;

	private double height;

	private double weight;

	private int age;

	private String birthday;

	private int yearInNBA;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTeamId() {
		return teamId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public int getYearInNBA() {
		return yearInNBA;
	}

	public void setYearInNBA(int yearInNBA) {
		this.yearInNBA = yearInNBA;
	}

}
