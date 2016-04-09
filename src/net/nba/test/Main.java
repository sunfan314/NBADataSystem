package net.nba.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.nba.dataSpider.PlayerInfoSpider;
import net.nba.dataSpider.impl.PlayerInfoSpiderImpl;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PlayerInfoSpiderImpl p=new PlayerInfoSpiderImpl();
		List<String> list=new ArrayList<String>();
		list.add("Jeff-Teague");
		p.downloadPlayerPic(list);
	}

}
