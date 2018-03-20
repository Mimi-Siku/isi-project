package modele;


public class App
{
	public static void main(String[] args) throws Throwable
	{
		String data1 = "https://raw.githubusercontent.com/fivethirtyeight/data/master/murder_2016/murder_2016_prelim.csv";
		String data2 = "https://raw.githubusercontent.com/fivethirtyeight/data/master/police-deaths/clean_data.csv";
		String data3 = "https://raw.githubusercontent.com/fivethirtyeight/data/master/murder_2016/murder_2015_final.csv";
		
		Wrapper wrap1 = new Wrapper("wrap1", data1);
		Wrapper wrap2 = new Wrapper("wrap2", data2);
		Wrapper wrap3 = new Wrapper("wrap3", data3);
		
		Mediator media1 = new Mediator();
		
		wrap1.wrapSource();
		wrap2.wrapSource();
		wrap3.wrapSource();
		
		media1.Vue1();
		//wrap1.readFile();
		//wrap2.readFile();
		
		//wrap1.createTable(data1);
		//wrap2.createTable(data2);
		
		//wrap1.fillTable();
		//wrap2.fillTable();
		
		
			
	}
}