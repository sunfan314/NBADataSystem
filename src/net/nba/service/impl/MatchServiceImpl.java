package net.nba.service.impl;

import java.util.List;

import javax.annotation.Resource;

import net.nba.dao.BaseDao;
import net.nba.model.MatchInfo;
import net.nba.service.MatchService;

import org.springframework.stereotype.Service;

@Service("matchService")
public class MatchServiceImpl implements MatchService{
	
	@Resource
	private BaseDao<MatchInfo> matchDao;

	@Override
	public List<MatchInfo> getLatestMatchInfos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MatchInfo> getTeamMatchInfos(int teamId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MatchInfo getMatchInfo(int matchId) {
		// TODO Auto-generated method stub
		return null;
	}

}
