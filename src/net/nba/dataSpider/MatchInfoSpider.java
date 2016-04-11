package net.nba.dataSpider;

import java.util.List;

import net.nba.model.Match;

public interface MatchInfoSpider {
	
	public List<Match> getSeasonMatchList();
	

}
