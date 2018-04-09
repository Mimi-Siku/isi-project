package modele;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import au.com.bytecode.opencsv.CSVReader;

import java.sql.* ;  // for standard JDBC programs
import java.util.List;
import java.util.Map;
import java.math.* ; // for BigDecimal and BigInteger support

/*
 * Wrapper class allows to create a table from a source, which is here a CSV file.
 */
public class WrapperTEST
{
	private String tableName; // Name given to the table the wrapper generate
	private String nomFichier; // Web adress of the CSV file (= wrapper source)
	private CSVReader reader; // Reader dedicated to CSV file (useful for some speciific traps)
	
	public String getTableName() {
		return tableName;
	}
	
	public WrapperTEST(String tableName, String nomFichier)
	{
		this.tableName = tableName;
		this.nomFichier = nomFichier;
	}
	
	/*
	 * Read the CSV file and build the SQL requests creating and filling the table containing CSV data 
	 */
	public void wrapSource() throws Exception{
		try
		{
			this.reader = new CSVReader(new FileReader(nomFichier));
			App.connectDatabase(App.mysqlDriver, App.mysqlConnectInfo);
			dropTable();
			createTable();
			fillTable();
			App.disconnectDatabase();
		}
		catch (Exception e) {e.printStackTrace();}		
	}
	
	/*
	 * Build and execute the query to drop the table
	 */
	private String dropTable()
	{
		String dropQuery = "DROP TABLE IF EXISTS " + tableName; // works with mysql only
		//String dropQuery = "DROP TABLE " + tableName;
		App.executeQuery(dropQuery);
		return dropQuery;
	}
	
	/*
	 * Build and execute the query to create the table 
	 */
	private String createTable()
	{
		StringBuilder createQuery = new StringBuilder("CREATE TABLE "+ tableName +"("); 
				
		try
		{
			String[] firstLine = reader.readNext();
			
			int i = 0;
			while (i < firstLine.length - 1)
			{
				//createQuery.append(tableName + "_" + firstLine[i] + " TEXT, ");
				createQuery.append(tableName + "_" + firstLine[i] + " VARCHAR(1000), ");
				i++;
			}
			//createQuery.append(tableName + "_" + firstLine[i] + " TEXT)");
			createQuery.append(tableName + "_" + firstLine[i] + " VARCHAR(1000))");
			App.executeQuery(createQuery.toString());
		}
		catch (IOException e) {e.printStackTrace();}

		return createQuery.toString();
	}
	
	/*
	 * Build and execute the queries to fill the table 
	 */
	private void fillTable()
	{
		String[] tuple;
		StringBuilder fillQuery;
		PreparedStatement stmt;
		
		try
		{
			while((tuple = reader.readNext()) != null)
			{
				// Preparation of the query with ? instead of the real values
				fillQuery = new StringBuilder("INSERT INTO " + tableName + " VALUES (");
				int i = 0;
				while (i < tuple.length - 1)
				{
					fillQuery.append("?, ");
					i++;
				}
				fillQuery.append("?)");
				
				// Replacement of the ? with the real values (this avoids problems with values containing ",")
				stmt = App.connection.prepareStatement(fillQuery.toString());
				for(i = 0; i < tuple.length; i++)
				{
					stmt.setString(i + 1, tuple[i]);
				}
				
				//System.out.println(fillQuery);
				stmt.execute();
			}
		}
		catch (SQLException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}
}
