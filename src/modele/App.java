package modele;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import au.com.bytecode.opencsv.CSVReader;

public class App
{
	public static final String mysqlDriver = "com.mysql.jdbc.Driver";
	public static final String oracleDriver = "oracle.jdbc.driver.OracleDriver";
	public static final String[] mysqlConnectInfo = {"jdbc:mysql://localhost:3306/sys", "root", "siku"};
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
		String data2 = "https://raw.githubusercontent.com/fivethirtyeight/data/master/police-killings/police_killings.csv";
		//String data3 = "https://raw.githubusercontent.com/fivethirtyeight/data/master/murder_2016/murder_2015_final.csv";
		
		String dataTerrorism = "plots.csv";
		String dataCrimes = "report.csv";
		String wrapName1 = "wrapAttentatsUS";
		String wrapName2 = "wrapCrimes";
		String wrapName3 = "wrapPoliceKillings";
		/************* Zone de Test ****************************/
		/*HttpDownloadUtility dl= new HttpDownloadUtility();
		String urlTerrorism = "https://www.kaggle.com/marshallproject/crime-rates/downloads/report.csv/1";
		String dir=".";
		try {
		dl.downloadFile(urlTerrorism, ".");
		 } catch (IOException ex) {
	            ex.printStackTrace();}*/
		WrapperFromCSV wrapTerrorism = new WrapperFromCSV(wrapName1, dataTerrorism);
		WrapperFromCSV wrapCrimes = new WrapperFromCSV(wrapName2, dataCrimes); 
		WrapperFromURL wrapPoliceKillings = new WrapperFromURL(wrapName3, data2);
		/********************Fin Zone *************************/

		
		Mediator media = new Mediator(wrapCrimes, wrapCrimes);
		
		wrapTerrorism.wrapSource();System.out.println("Wrapper "+wrapName1 + " créé");
		wrapCrimes.wrapSource();System.out.println("Wrapper "+wrapName2 + " créé");
		wrapPoliceKillings.wrapSource();System.out.println("Wrapper "+wrapName3 + " créé");
		
		media.vue1Builder();
		System.out.println(" Vue RatioCrime1 crée");
		media.vue2Builder("");
		System.out.println(" Vue RatioCrime2 crée");
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
