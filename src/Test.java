import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import net.nba.dataSpider.impl.MatchInfoSpiderImpl;
import net.nba.dataSpider.impl.PlayerInfoSpiderImpl;
import net.nba.service.impl.MatchServiceImpl;
import net.nba.util.MyFileWriter;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MatchInfoSpiderImpl m=new MatchInfoSpiderImpl();
		m.getSeasonMatchList();
		
	}

}
