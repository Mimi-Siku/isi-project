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
		String data1 = "https://raw.githubusercontent.com/fivethirtyeight/data/master/murder_2016/murder_2016_prelim.csv";
		String data2 = "https://raw.githubusercontent.com/fivethirtyeight/data/master/police-deaths/clean_data.csv";
		String data3 = "https://raw.githubusercontent.com/fivethirtyeight/data/master/murder_2016/murder_2015_final.csv";
		
		Wrapper wrap1 = new Wrapper("wrap1", data1);
		Wrapper wrap2 = new Wrapper("wrap2", data2);
		Wrapper wrap3 = new Wrapper("wrap3", data3);
		
		Mediator media1 = new Mediator("view1");
		
		wrap1.wrapSource();
		wrap2.wrapSource();
		wrap3.wrapSource();
		
		media1.Vue1();
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
