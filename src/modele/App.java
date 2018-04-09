package modele;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

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

		String data1 = "https://github.com/fivethirtyeight/data/blob/master/murder_2016/murder_2015_final.csv";
		String data2 = "https://raw.githubusercontent.com/fivethirtyeight/data/master/police-killings/police_killings.csv";
		String data3 = "https://raw.githubusercontent.com/fivethirtyeight/data/master/hate-crimes/hate_crimes.csv";
		String dataTerrorism = "plots.csv";
		String dataCrimes = "report.csv";

		String wrapName1 = "wrapAttentatsUS";
		String wrapName2 = "wrapCrimes";
		String wrapName3 = "wrapPoliceKillings";
		String wrapName4 = "wrapHateCrime";
		String wrapName5 = "wrapHomicide2015";
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
		WrapperFromURL wrapHateCrime = new WrapperFromURL(wrapName4, data3);
		WrapperFromURL wrapHomicide2015 = new WrapperFromURL(wrapName5, data1);
		/********************Fin Zone *************************/


		Mediator media = new Mediator( wrapTerrorism,wrapCrimes, wrapPoliceKillings, wrapHateCrime, wrapHomicide2015);

		wrapHomicide2015.wrapSource();System.out.println("Wrapper "+wrapName1 + " créé");
		wrapTerrorism.wrapSource();System.out.println("Wrapper "+wrapName1 + " créé");
		wrapCrimes.wrapSource();System.out.println("Wrapper "+wrapName2 + " créé");
		wrapPoliceKillings.wrapSource();System.out.println("Wrapper "+wrapName3 + " créé");
		wrapHateCrime.wrapSource();System.out.println("Wrapper "+wrapName4 + " créé");

		chargerVues(media);
		
		Connection cnx;
		Statement st;
		ResultSet rst;
		String str = "";
		Scanner sc = new Scanner(System.in);
		while (str.compareTo("quitter")!=0) {
			System.out.println("Veuillez saisir une requete (sans saut de ligne):");
			System.out.println("Pour quitter taper \"quitter\"");
			str = sc.nextLine();
			if (str.compareTo("quitter")==0) {
				System.out.println("Programme terminé");
				break;
			}
			System.out.println("Vous avez saisi : " + str);
			try{
				 cnx=connecterDB();  
				st=cnx.createStatement();
				rst=st.executeQuery(str);
				String[] noms = getNomsColonnes(rst); 
				for(int i = 0; i < noms.length; i++){ 
				   System.out.println(noms[i]); 
				}
				while(rst.next()) {
				   for (String nom : noms) {
					   System.out.print(rst.getString(nom)+"\t");
				   }
				   System.out.println("\n");
				}
			}catch(Exception ex){
				ex.printStackTrace();
			} 
		}
		
		

	}
	public static String[] getNomsColonnes(ResultSet resultat) throws SQLException{ 
		   ResultSetMetaData metadata = resultat.getMetaData(); 
		   String[] noms = new String[metadata.getColumnCount()]; 
		   for(int i = 0; i < noms.length; i++){ 
		      String nomColonne = metadata.getColumnName(i+1); 
		      noms[i] = nomColonne; 
		   } 
		   return noms; 
		}
	public static void chargerVues(Mediator media) {
		media.vue1Builder();
		System.out.println(" Vue1 crée");
		media.vue2Builder("");
		System.out.println(" Vue2 crée");
		media.vue3Builder();
		System.out.println(" Vue3 crée");
		media.vue4Builder();
		System.out.println(" Vue4 crée");
		media.vue5Builder();
		System.out.println(" Vue5 crée");
		media.vue6Builder();
		System.out.println(" Vue6 crée");
		media.vue7Builder();
		System.out.println(" Vue7 crée");
		System.out.println(media.test());
	}
	// methode de connection Avec retour String des infos renvoyer par la requete sur la BDD
    public static Connection  connecterDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver oki");
            String url="jdbc:mysql://localhost:3306/sys";
            String user="root";
            String password="siku";
           Connection cnx=DriverManager.getConnection(url,user,password);
            System.out.println("Connexion bien établié");
            return cnx;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    // Methode de connection sans retour info
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
