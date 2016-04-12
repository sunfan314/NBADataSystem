import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import net.nba.dataSpider.impl.MatchInfoSpiderImpl;
import net.nba.dataSpider.impl.PlayerInfoSpiderImpl;
import net.nba.service.impl.MatchServiceImpl;
import net.nba.util.MyFileWriter;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MatchInfoSpiderImpl m=new MatchInfoSpiderImpl();
		List<Integer> list=new ArrayList<Integer>();
		m.getPlayerMatchStatistics(list);
		
	}

}
