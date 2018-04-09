package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Mediator
{	
	WrapperFromCSV wrapperTerrorism;
	WrapperFromCSV wrapperCrimes;
	WrapperFromURL wrapperPoliceKillings;
	WrapperFromURL wrapperHateCrime;
	WrapperFromURL wrapperHomicide2015 ;

	public Mediator(WrapperFromCSV wrapperTerrorism, WrapperFromCSV wrapperCrimes, WrapperFromURL wrapperPoliceKillings, WrapperFromURL wrapperHateCrime, WrapperFromURL wrapperHomicide2015)
	{
		this.wrapperCrimes = wrapperCrimes;
		this.wrapperTerrorism = wrapperTerrorism;
		this.wrapperHateCrime = wrapperHateCrime;
		this.wrapperPoliceKillings= wrapperPoliceKillings;
		this.wrapperHomicide2015 = wrapperHomicide2015;
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
	
	public String vue2Builder(String ville)	// Vue regroupant la somme de meurtre par année
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
		sb.append("(year, nbCrimes) AS ");
		sb.append("SELECT DISTINCT " + wrapperName + "_report_year," + "SUM(" + wrapperName + "_violent_crimes) ");
		sb.append("FROM " + wrapperName);
		sb.append(" Where " + wrapperName + "_agency_jurisdiction like \""+  ville+"%\" " );
		sb.append(" GROUP BY " + wrapperName + "_report_year " );
		System.out.println(sb.toString());	// Copié le resultat et verifié la syntaxe dans MYSQL avec l'onglet query

		sendQuery(sb.toString());
		return sb.toString();
	}
	
	public String vue3Builder()//EM pour faire sortir la ville du plot_name
	{
		StringBuilder sb = new StringBuilder();
		String wrapperName1 = wrapperHateCrime.getTableName();// Pour l'entity matching on utilise wrapCrimes car cette table possede les villes bien formés
		String wrapperName2 = wrapperTerrorism.getTableName();
		sendQuery("DROP VIEW IF EXISTS EMVille; ");
		sb.append("CREATE VIEW EMville  ");
		sb.append("(correspondanceVille) AS ");
		sb.append(" SELECT DISTINCT wrapAttentatsUS_plot_name " );
		sb.append(" FROM " + wrapperName1 + " ," + wrapperName2);
		sb.append(" WHERE `"+wrapperName1 +"_state` LIKE '%{$"+wrapperName2 +"_plot_name}%' " );
		System.out.println(sb.toString());	// Copié le resultat et verifié la syntaxe dans MYSQL avec l'onglet query

		sendQuery(sb.toString());
		return sb.toString();
	}
	
	public String vue4Builder()//EM pour faire sortir l'année du plot_name
	{
		StringBuilder sb = new StringBuilder();
		String wrapperName1 = wrapperCrimes.getTableName();	// Pour l'entity matching on utilise wrapCrimes car cette table possede les anéées bien formés
		String wrapperName2 = wrapperTerrorism.getTableName();
		sendQuery("DROP VIEW IF EXISTS EMAnnee; ");
		sb.append("CREATE VIEW EMAnnee  ");
		sb.append("(correspondanceAnnee) AS ");
		sb.append(" SELECT DISTINCT wrapAttentatsUS_plot_name " );
		sb.append(" FROM " + wrapperName1 + " ," + wrapperName2);
		sb.append(" WHERE `"+wrapperName1 +"_report_year` LIKE '%{$"+wrapperName2 +"_plot_name}%' " );
		System.out.println(sb.toString());	// Copié le resultat et verifié la syntaxe dans MYSQL avec l'onglet query

		sendQuery(sb.toString());
		return sb.toString();
	}
	
	public String vue5Builder()//Vu regroupe le nombre crimes violent et d'homicides dans tout le pays pour chaque année
	{
		StringBuilder sb = new StringBuilder();
		String wrapperName1 = wrapperCrimes.getTableName();
		sendQuery("DROP VIEW IF EXISTS HomicidePays; ");
		sb.append("CREATE VIEW HomicidePays  ");
		sb.append("(annee,ville,nbHomicide,nbCrimesViolents, population)  AS ");
		sb.append(" SELECT "+ wrapperName1 + "_report_year, " + wrapperName1 +"_agency_jurisdiction, " + wrapperName1 +"_homicides, "+ wrapperName1 +"_violent_crimes, "+ wrapperName1 +"_population " );
		sb.append(" FROM " + wrapperName1  );
		sb.append(" WHERE "+wrapperName1 +"_agency_jurisdiction=\"United States\" " );
		System.out.println(sb.toString());	// Copié le resultat et verifié la syntaxe dans MYSQL avec l'onglet query

		sendQuery(sb.toString());
		return sb.toString();
	}
	
	public String vue6Builder()//Cette vue a pour unique object de faciliter le calcul des ratio crime/habitant pour d'autres vues
	{
		StringBuilder sb = new StringBuilder();
		String wrapperName1 = wrapperCrimes.getTableName();
		sendQuery("DROP VIEW IF EXISTS sumCrimes; ");
		sb.append("CREATE VIEW sumCrimes  ");
		sb.append("(sumCrimes, ville" + 
				") AS ");
		sb.append(" SELECT "+ wrapperName1 + "_violent_crimes+ " + wrapperName1 +"_homicides+ " + wrapperName1 +"_rapes+ "+ wrapperName1 +"_assaults+ "+ wrapperName1 +"_robberies as sumCrimes, "+ wrapperName1 +"_agency_jurisdiction " );
		sb.append(" FROM " + wrapperName1  );
		sb.append(" WHERE "+wrapperName1 +"_report_year=\"2015\" " );
		sb.append(" order by "+wrapperName1 +"_report_year " );
		System.out.println(sb.toString());	// Copié le resultat et verifié la syntaxe dans MYSQL avec l'onglet query

		sendQuery(sb.toString());
		return sb.toString();
	}
	
	public String vue7Builder()//Vue sur les villes les plus securisé triés sur le ratio croissant de crime par habitant
	{
		StringBuilder sb = new StringBuilder();
		String wrapperName1 = wrapperCrimes.getTableName();
		sendQuery("DROP VIEW IF EXISTS villeSafe2015; ");
		sb.append("CREATE VIEW villeSafe2015  ");
		sb.append("(ville, ratio) AS ");
		sb.append(" SELECT distinct ville, sumcrimes/"+ wrapperName1+"_population as ratio " );
		sb.append(" FROM " + wrapperName1+", sumCrimes"  );
		sb.append(" WHERE "+ wrapperName1 +"_report_year=\"2015\" " );
		sb.append(" AND "+ wrapperName1 +"_agency_jurisdiction=ville " );
		sb.append(" order by ratio " );
		System.out.println(sb.toString());	// Copié le resultat et verifié la syntaxe dans MYSQL avec l'onglet query

		sendQuery(sb.toString());
		return sb.toString();
	}
	
	 public String test() {
		 StringBuilder sb = new StringBuilder();
		 sb.append("select * from HomicidesPArville");
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
