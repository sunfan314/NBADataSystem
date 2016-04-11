package net.nba.service;

import java.util.List;

import net.nba.model.Match;

public interface MatchService {
	
	public void updateSeasonMatchList();//更新赛季比赛数据

	public List<Match> getLatestMatchs();//获取最近50场比赛的记录

	List<Match> getTeamMatchs(int teamId);//获取某支球队当前赛季比赛信息

//	MatchInfo getMatchInfo(int matchId);

}
