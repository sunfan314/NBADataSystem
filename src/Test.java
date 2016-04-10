import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import net.nba.dataSpider.impl.PlayerInfoSpiderImpl;
import net.nba.util.MyFileWriter;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		PlayerInfoSpiderImpl p=new PlayerInfoSpiderImpl();
		p.getTeamPlayerList();
		
	}

}
