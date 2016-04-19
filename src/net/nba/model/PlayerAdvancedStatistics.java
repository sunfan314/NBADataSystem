package net.nba.model;

import java.util.List;

import javax.persistence.Entity;

import org.hibernate.metamodel.domain.Superclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import net.nba.util.DoubleFormat;

/**
 * @author sunfan314
 * twoRate			两分命中率
 * threeRate		三分命中率
 * freeThrowRate	罚球命中率
 * trueRate			真实命中率
 * PER				效率值
 * doubleDouble		赛季两双次数
 * tripleDouble		赛季三双次数
 *
 */
@JsonIgnoreProperties(value = { "twoHit","twoShot","threeHit","threeShot","freeThrowHit","freeThrowShot"
		,"offReb","defReb","totReb","ass","steal","blockShot","turnOver","foul","score"})
public class PlayerAdvancedStatistics extends PlayerSeasonStatistics{
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
		super(player,team,dataList,season);
		int totMatches=dataList.size();
		double trueRateTot=0;
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
		if(!(super.getTwoShot()==0)){
			this.twoRate=DoubleFormat.transfer((double)(super.getTwoHit()*100/super.getTwoShot()));
		}else{
			this.twoRate=0.00;
		}
		if(!(super.getThreeShot()==0)){
			this.threeRate=DoubleFormat.transfer((double)(super.getThreeHit()*100/super.getThreeShot()));
		}else{
			this.threeRate=0.00;
		}
		if(!(super.getFreeThrowShot()==0)){
			this.freeThrowRate=DoubleFormat.transfer((double)(super.getFreeThrowHit()*100/super.getFreeThrowShot()));
		}else{
			this.freeThrowRate=0.00;
		}
		this.trueRate=DoubleFormat.transfer((double)(trueRateTot*100/totMatches));
		this.PER=DoubleFormat.transfer((double)(perTot/totMatches));
		this.doubleDouble=doubleDoubleCount;
		this.tripleDouble=tripleDoubleCount;
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
