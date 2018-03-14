

public class Main {

	public static void main(String[] args) throws Throwable {
		CrunchifyLoadGithubContent crunchy = new CrunchifyLoadGithubContent();
		String data1 = crunchy.crunch("https://raw.githubusercontent.com/fivethirtyeight/data/master/murder_2016/murder_2016_prelim.csv");
		String data2 = crunchy.crunch("https://raw.githubusercontent.com/fivethirtyeight/data/master/police-deaths/clean_data.csv");
		//Wrapper wrap1 = new Wrapper("wrap1");
		Wrapper wrap2 = new Wrapper("wrap2");
		//wrap1.createTable1(data1);
		wrap2.createTable2(data2);
	}


}
