package net.nba.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.nba.dao.BaseDao;
import net.nba.model.MatchInfo;
import net.nba.model.Player;
import net.nba.model.Team;
import net.nba.model.TeamMatchInfo;
import net.nba.service.TestService;
@Service("testService")
public class TestServiceImpl implements TestService {
	@Resource
	private BaseDao<Object> baseDao;

	@Override
	public List<Object> getTestData() {
		List<Object> list=baseDao.find("from MatchInfo");
		
		return list;
	}

}
