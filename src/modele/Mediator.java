package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Mediator
{	
	WrapperFromCSV wrapperTerrorism;
	WrapperFromCSV wrapperCrimes;

	public Mediator(WrapperFromCSV wrapperTerrorism, WrapperFromCSV wrapperCrimes)
	{
		this.wrapperCrimes = wrapperCrimes;
		this.wrapperTerrorism = wrapperTerrorism;
	}
	
	
	public void createViews()
	{
		App.connectDatabase(App.mysqlDriver, App.mysqlConnectInfo);
		// if les vues n'existent pas, les creer
		
	}
	
	public void vue1Builder()
	{
		StringBuilder sb = new StringBuilder();
		String wrapperName = wrapperCrimes.getTableName();
		sendQuery("DROP VIEW IF EXISTS RatioCrimes; ");// Attention ça envoi un message d'erreur si la vue n'existe pas mais ce n'est pas bloquant
		sb.append("CREATE VIEW RatioCrimes ");
		sb.append("(city, year, nbCrimes, percapitaRatio) AS ");
		sb.append("SELECT ");
		sb.append("DISTINCT " + wrapperName + "_report_year AS city, ");
		sb.append(wrapperName + "_agency_jurisdiction AS year, ");
		sb.append(wrapperName + "_violent_crimes, ");
		sb.append("");
		sb.append(wrapperName + "_crimes_percapita ");
		sb.append("FROM " + wrapperName);
		System.out.println(sb.toString());	// Copié le resultat et verifié la syntaxe dans MYSQL avec l'onglet query
		
		sendQuery(sb.toString());
	}
	
	public String vue2Builder(String ville)
	{
		String city;
		if(ville==null)
			city="";
		else
			city=ville;
		StringBuilder sb = new StringBuilder();
		String wrapperName = wrapperCrimes.getTableName();
		sendQuery("DROP VIEW IF EXISTS HomicidesParVille; ");
		sb.append("CREATE VIEW HomicidesParVille ");
		sb.append("(year,ville, nbHomicides) AS ");
		sb.append("SELECT DISTINCT " + wrapperName + "_report_year," + wrapperName + "_agency_jurisdiction, "+ "SUM(" + wrapperName + "_violent_crimes) ");
		sb.append("FROM " + wrapperName);
		sb.append(" Where " + wrapperName + "_agency_jurisdiction like \""+  ville+"%\" " );
		sb.append(" GROUP BY " + wrapperName + "_report_year, " + wrapperName + "_agency_jurisdiction");
		System.out.println(sb.toString());	// Copié le resultat et verifié la syntaxe dans MYSQL avec l'onglet query

		sendQuery(sb.toString());
		return sb.toString();
	}
	
	public String vue3Builder()//TODO
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
