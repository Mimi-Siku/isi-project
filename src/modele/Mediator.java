package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Mediator
{	
	Wrapper wrapperTerrorism;
	Wrapper wrapperCrimes;

	public Mediator(Wrapper wrapperTerrorism, Wrapper wrapperCrimes)
	{
		this.wrapperCrimes = wrapperCrimes;
		this.wrapperTerrorism = wrapperTerrorism;
	}
	
	
	public void createViews()
	{
		App.connectDatabase(App.mysqlDriver, App.mysqlConnectInfo);
		// if les vues n'existent pas, les creer
		
	}
	
	public String vue1Builder()
	{
		StringBuilder sb = new StringBuilder();
		String wrapperName = wrapperCrimes.getTableName();
		
		sb.append("CREATE VIEW RatioCrimes ");
		sb.append("(city, year, nbCrimes, percapitaRatio) AS ");
		sb.append("SELECT ");
		sb.append("DISTINCT " + wrapperName + ".report_year AS city, ");
		sb.append("DISTINCT " + wrapperName + ".agency_jurisdiction AS year ");
		sb.append(wrapperName + ".violent_crimes");
		sb.append("");
		sb.append(wrapperName + ".crimes_percapita");
		sb.append("FROM " + wrapperName);
		
		return sb.toString();
	}
	
	public String vue2Builder()
	{
		StringBuilder sb = new StringBuilder();
		String wrapperName = wrapperCrimes.getTableName();
		
		sb.append("CREATE VIEW RatioCrimes ");
		sb.append("(year, nbCrimes) AS ");
		sb.append("SELECT " + wrapperName + ".report_year, SUM(" + wrapperName + ".violent_crimes)");
		sb.append("WHERE " + wrapperName + " ");
		sb.append("ORDER BY " + wrapperName + ".report_year");
		
		return sb.toString();
	}
	
	public void sendQuery(String query)
	{
		try
		{
			App.connectDatabase(App.mysqlDriver, App.mysqlConnectInfo);
			
			// Execute the queries
			Statement stmt = App.connection.createStatement();
			stmt.execute(query);
			
			App.disconnectDatabase();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
