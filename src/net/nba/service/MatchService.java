package net.nba.service;

import java.util.List;

import net.nba.model.MatchInfo;

public interface MatchService {

	List<MatchInfo> getLatestMatchInfos();

	List<MatchInfo> getTeamMatchInfos(int teamId);

	MatchInfo getMatchInfo(int matchId);

}
