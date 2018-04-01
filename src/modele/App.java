package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class App
{
	public static final String mysqlDriver = "com.mysql.jdbc.Driver";
	public static final String oracleDriver = "oracle.jdbc.driver.OracleDriver";
	public static final String[] mysqlConnectInfo = {"jdbc:mysql://dbs-perso.luminy.univmed.fr:3306/b14017497", "b14017497", "LD.ZY4"};
	public static final String[] oracleConnectInfo = {"jdbc:oracle:thin:@localhost:1521:orcl", "siku", "siku"};
	public static Connection connection;
	
	public static void main(String[] args) throws Throwable
	{
		/* URL 
		 * https://www.kaggle.com/newamerica/terrorist-activity/data
		 * => useless
		 * https://www.kaggle.com/bmwhitehead2000/terrorism-in-the-united-states-year-by-year/data
		 * => select united states et entitymatching avec latitude longitude
		 * https://www.kaggle.com/marshallproject/crime-rates/data
		 * 
		 */
		
		//String data1 = "https://raw.githubusercontent.com/fivethirtyeight/data/master/murder_2016/murder_2016_prelim.csv";
		//String data2 = "https://raw.githubusercontent.com/fivethirtyeight/data/master/police-deaths/clean_data.csv";
		//String data3 = "https://raw.githubusercontent.com/fivethirtyeight/data/master/murder_2016/murder_2015_final.csv";
		
		String dataTerrorism = "https://www.kaggle.com/bmwhitehead2000/terrorism-in-the-united-states-year-by-year/data";
		String dataCrimes = "https://www.kaggle.com/marshallproject/crime-rates/data";
		String wrapName1 = "wrapTerrorism";
		String wrapName2 = "wrapCrimes";
		
		Wrapper wrapTerrorism = new Wrapper(wrapName1, dataTerrorism);
		Wrapper wrapCrimes = new Wrapper(wrapName2, dataCrimes);
		
		Mediator media = new Mediator(wrapTerrorism, wrapCrimes);
		
		wrapTerrorism.wrapSource();
		wrapCrimes.wrapSource();
		
		media.vue1Builder();
	}
	
	public static void connectDatabase(String driver, String[] connectInfo)
	{
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(connectInfo[0], connectInfo[1], connectInfo[2]);
		}
		catch(ClassNotFoundException e) {e.printStackTrace();}
		catch(SQLException e) {e.printStackTrace();}
	}
	
	public static void executeQuery(String query)
	{
		Statement stmt;
		try
		{
			stmt = connection.createStatement();
			stmt.execute(query);
		}
		catch(SQLException e) {e.printStackTrace();}
	}
	
	public static void disconnectDatabase()
	{
		try
		{
			connection.close();
		}
		catch (SQLException e) {e.printStackTrace();}
	}
}
