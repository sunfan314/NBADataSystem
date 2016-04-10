package net.nba.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * id				球员id
 * name				球员名
 * birthday			生日
 * age				年龄
 * birthPlace		出生地
 * college			大学
 * height			球员身高
 * weight			体重
 * startInNBA		进入NBA年份
 * yearInNBA		NBA球龄
 * draftStatus		选秀情况
 * sHS				赛季最高分
 * cHS				生涯最高分
 */

@Entity
@Table(name = "player_info_detail")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class PlayerInfoDetail {
	@Id
	private int id;

	private String name;

	private String birthday;

	private String age;

	private String birthPlace;

	private String college;

	private String height;

	private String weight;

	private String startInNBA;

	private String yearInNBA;

	private String draftStatus;

	private String sHS;

	private String cHS;

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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getStartInNBA() {
		return startInNBA;
	}

	public void setStartInNBA(String startInNBA) {
		this.startInNBA = startInNBA;
	}

	public String getYearInNBA() {
		return yearInNBA;
	}

	public void setYearInNBA(String yearInNBA) {
		this.yearInNBA = yearInNBA;
	}

	public String getDraftStatus() {
		return draftStatus;
	}

	public void setDraftStatus(String draftStatus) {
		this.draftStatus = draftStatus;
	}

	public String getsHS() {
		return sHS;
	}

	public void setsHS(String sHS) {
		this.sHS = sHS;
	}

	public String getcHS() {
		return cHS;
	}

	public void setcHS(String cHS) {
		this.cHS = cHS;
	}

}
