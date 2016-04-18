package net.nba.util;

import java.text.DecimalFormat;

public class DoubleFormat {
	
	public static double transfer(Double data){//设置double精度为保留两位小数
		DecimalFormat df= new DecimalFormat("0.00");
		String temp=df.format(data);
		return Double.parseDouble(temp);
	}
		

}
