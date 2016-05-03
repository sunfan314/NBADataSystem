package net.nba.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.metamodel.domain.Superclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.nba.util.DoubleFormat;

/**
 * @author sunfan314
 *playerId 			球员id
 *playerName 		球员姓名
 *season 			赛季信息
 *teamId 			球队id
 *teamName 			球队名
 *isFirst			首发次数
 *totalMatches 		出场次数
 *time 				场均上场时间
 * twoRate			两分命中率
 * threeRate		三分命中率
 * freeThrowRate	罚球命中率
 * trueRate			真实命中率
 * PER				效率值
 * doubleDouble		赛季两双次数
 * tripleDouble		赛季三双次数
 *
 */
@Entity
@Table(name = "player_advanced_statistics")
@JsonIgnoreProperties(value = { "handler", "hibernateLazyInitializer" })
public class PlayerAdvancedStatistics{
	@Id
	private int playerId;
	
	private String playerName;
	
	private String season;
	
	private int teamId;
	
	private String teamName;
	
	private int isFirst;
	
	private int totalMatches;
	
	private double time;
	
	private double twoRate;
	
	private double threeRate;
	
	private double freeThrowRate;
	
	private double trueRate;
	
	private double PER;
	
	private int doubleDouble;
	
	private int tripleDouble;
	
	
	public PlayerAdvancedStatistics(){
		super();
	}
	public PlayerAdvancedStatistics(Player player,Team team,List<PlayerMatchStatistics> dataList,String season){
		super();
		this.season=season;
		this.playerId=player.getId();
		this.playerName=player.getName();
		this.teamId=team.getId();
		this.teamName=team.getName();
		int totMatches=dataList.size();
		this.totalMatches=totMatches;
		double trueRateTot=0;
		int isFirstTot=0,timeTot=0,twoShotTot=0,twoHitTot=0,threeShotTot=0,threeHitTot=0,freeThrowShotTot=0,freeThrowHitTot=0;
		int perTot=0,doubleDoubleCount=0,tripleDoubleCount=0;
		for (PlayerMatchStatistics s : dataList) {
			int doubleCount=0;//统计上双项目数
			if(s.getScore()>9){
				doubleCount++;
			}
			if(s.getTotReb()>9){
				doubleCount++;
			}
			if(s.getAss()>9){
				doubleCount++;
			}
			if(s.getSteal()>9){
				doubleCount++;
			}
			if(s.getBlockShot()>9){
				doubleCount++;
			}
			if(doubleCount==2){
				doubleDoubleCount++;
			}else if(doubleCount==3){
				tripleDoubleCount++;
			}
			if(s.getIsFirst()==0){//首发次数
				isFirstTot++;
			}
			timeTot=timeTot+s.getTime();//出场时间
			twoHitTot=twoHitTot+s.getTwoHit();//两分球命中
			twoShotTot=twoShotTot+s.getTwoShot();//两分球出手次数
			threeHitTot=threeHitTot+s.getThreeHit();//三分球命中次数
			threeShotTot=threeShotTot+s.getThreeShot();//三分球出手次数
			freeThrowHitTot=freeThrowHitTot+s.getFreeThrowHit();//罚球命中次数
			freeThrowShotTot=freeThrowShotTot+s.getFreeThrowShot();//罚球出手次数
			/*
			 * 真实命中率计算：得分/[2*(出手次数+0.44罚球次数)]
			 */
			double matchTrueRate;
			if(s.getTwoShot()==0&&s.getThreeShot()==0&&s.getFreeThrowShot()==0){
				matchTrueRate=0;
			}else{
				matchTrueRate=(double)(s.getScore()/(2*(s.getTwoShot()+s.getThreeShot()+0.44*s.getFreeThrowShot())));
			}
			trueRateTot=trueRateTot+matchTrueRate;
			/*
			 * 效率值计算：【（得分+篮板+助攻+抢断+盖帽）-（出手次数-命中次数）-（罚球次数-罚球命中次数）-失误】/场次
			 */
			int matchPER=(s.getScore()+s.getTotReb()+s.getAss()+s.getSteal()+s.getBlockShot())-(s.getTwoShot()+s.getThreeShot()
					+s.getFreeThrowShot()-s.getTwoHit()-s.getThreeHit()-s.getFreeThrowHit())-s.getTurnOver();
			perTot=perTot+matchPER;
			
		}
		this.isFirst=isFirstTot;
		this.time=DoubleFormat.transfer((double)timeTot/totMatches);
		if(!(twoShotTot==0)){
			this.twoRate=DoubleFormat.transfer((double)(twoHitTot*100)/twoShotTot);
		}else{
			this.twoRate=0.00;
		}
		if(!(threeShotTot==0)){
			this.threeRate=DoubleFormat.transfer((double)(threeHitTot*100)/threeShotTot);
		}else{
			this.threeRate=0.00;
		}
		if(!(freeThrowShotTot==0)){
			this.freeThrowRate=DoubleFormat.transfer((double)(freeThrowHitTot*100)/freeThrowShotTot);
		}else{
			this.freeThrowRate=0.00;
		}
		this.trueRate=DoubleFormat.transfer((double)(trueRateTot*100)/totMatches);
		this.PER=DoubleFormat.transfer((double)perTot/totMatches);
		this.doubleDouble=doubleDoubleCount;
		this.tripleDouble=tripleDoubleCount;
	}
	
	
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	
	public int getIsFirst() {
		return isFirst;
	}
	public void setIsFirst(int isFirst) {
		this.isFirst = isFirst;
	}
	public int getTotalMatches() {
		return totalMatches;
	}
	public void setTotalMatches(int totalMatches) {
		this.totalMatches = totalMatches;
	}
	public double getTime() {
		return time;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public double getTwoRate() {
		return twoRate;
	}
	public void setTwoRate(double twoRate) {
		this.twoRate = twoRate;
	}
	public double getThreeRate() {
		return threeRate;
	}
	public void setThreeRate(double threeRate) {
		this.threeRate = threeRate;
	}
	public double getFreeThrowRate() {
		return freeThrowRate;
	}
	public void setFreeThrowRate(double freeThrowRate) {
		this.freeThrowRate = freeThrowRate;
	}
	public double getTrueRate() {
		return trueRate;
	}
	public void setTrueRate(double trueRate) {
		this.trueRate = trueRate;
	}
	public double getPER() {
		return PER;
	}
	public void setPER(double pER) {
		PER = pER;
	}
	public int getDoubleDouble() {
		return doubleDouble;
	}
	public void setDoubleDouble(int doubleDouble) {
		this.doubleDouble = doubleDouble;
	}
	public int getTripleDouble() {
		return tripleDouble;
	}
	public void setTripleDouble(int tripleDouble) {
		this.tripleDouble = tripleDouble;
	}
	
	

}
