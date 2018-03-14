package fr.master1isi;


public class App {

	public static void main(String[] args) throws Throwable {
		CrunchifyLoadGithubContent crunchy = new CrunchifyLoadGithubContent();
		String data1 = crunchy.crunch("https://raw.githubusercontent.com/fivethirtyeight/data/master/murder_2016/murder_2016_prelim.csv");
		String data2 = crunchy.crunch("https://raw.githubusercontent.com/fivethirtyeight/data/master/police-deaths/clean_data.csv");
		//Wrapper wrap1 = new Wrapper("wrap1");
		Wrapper wrap2 = new Wrapper("wrap2");
		//wrap1.createTable(data1);
		wrap2.readFile("ressources/murder_2016_prelim.csv");
		wrap2.createTable(data2);
		wrap2.fillTable();
		Wrapper wrap1 = new Wrapper("wrap1");
		//wrap1.createTable(data1);
		wrap1.readFile("ressources/clean_data.csv");
		wrap1.createTable(data2);
		wrap1.fillTable();
			
	}


}
