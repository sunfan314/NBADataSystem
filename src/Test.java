import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			BufferedWriter writer = new BufferedWriter(
					new FileWriter(
							new File(
									"/Users/sunfan314/Documents/workspace/NBADataSystem/测试日志/TestLog")));
			writer.write("sunafnafda");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
