import java.util.List;

import javax.annotation.Resource;

import net.nba.dataSpider.TeamInfoSpider;
import net.nba.dataSpider.impl.TeamInfoSpiderImpl;
import net.nba.model.Team;
import net.nba.service.TeamService;
import net.nba.service.impl.TeamServiceImpl;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TeamInfoSpiderImpl t=new TeamInfoSpiderImpl();
		t.getTeamSeasonRanks();
	}

}
