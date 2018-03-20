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
public class Wrapper
{
	private String tableName; // Name given to the table the wrapper generate
	private String webAdress; // Web adress of the CSV file (= wrapper source)
	private CSVReader reader; // Reader dedicated to CSV file (useful for some speciific traps)
	
	public Wrapper(String tableName, String webAdress)
	{
		this.tableName = tableName;
		this.webAdress = webAdress;
	}
	
	/*
	 * Read the CSV file and build the SQL requests creating and filling the table containing CSV data 
	 */
	public void wrapSource() throws IOException
	{
		// Connection to the online CSV file
		URL url = new URL(webAdress);
		HttpURLConnection httpCo = (HttpURLConnection) url.openConnection();
		Map<String, List<String>> crunchifyHeader = httpCo.getHeaderFields();

		// If URL is getting 301 and 302 redirection HTTP code then get new URL link.
		// This below for loop is totally optional if you are sure that your URL is not getting redirected to anywhere
		String webAdressAlt;
		for (String header : crunchifyHeader.get(null))
		{
			if (header.contains(" 302 ") || header.contains(" 301 "))
			{
				webAdressAlt = crunchifyHeader.get("Location").get(0);
				url = new URL(webAdressAlt);
				httpCo = (HttpURLConnection) url.openConnection();
				crunchifyHeader = httpCo.getHeaderFields();
			}
		}
		InputStream inputStream = httpCo.getInputStream();
		
		// Reading, building requests and execution
		if (inputStream != null)
		{
			try
			{
				this.reader = new CSVReader(new BufferedReader(new InputStreamReader(inputStream, "UTF-8")));
				String request = "DROP TABLE " + tableName;
				//String request = "DROP TABLE " + tableName + ";" ;//+ createTable() + ";" + fillTable();
				System.out.println(request);
				sendRequest(request);
				request = createTable() ;
				System.out.println(request);
				sendRequest(request);
				fillTable();
			}
			catch (IOException e)
			{
				System.out.println(e.getStackTrace());
			}
			finally
			{
				inputStream.close();
			}
		}
	}
	/*
	 * Build the request to create the table 
	 */
	private String createTable()
	{
		StringBuilder request = new StringBuilder(); 
		
		request.append("CREATE TABLE "+ tableName +"(");
		
		try
		{
			String[] firstLine = reader.readNext();
			
			int i = 0;
			while (i < firstLine.length - 1)
			{
				request.append(tableName + "_" + firstLine[i] + " TEXT, ");
				i++;
			}
			request.append(tableName + "_" + firstLine[i] + " TEXT)");
			
			//System.out.println(request);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return request.toString();
	}
	
	/*
	 * Build the request to fill the table 
	 */
	private String fillTable()
	{
		StringBuilder request = new StringBuilder("");
		String[] datas;
		
		try
		{
			int cpt = 0;
			while((datas = reader.readNext()) != null)
			{
				//System.out.println(cpt);
				//add2(datas);
				cpt++;
				
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return request.toString();
	}
	

	private void add2(String[] data)
	{
		StringBuilder sb = new StringBuilder ();
		String[] parts = data;
		
		try
		{
			Class.forName( "com.mysql.jdbc.Driver" );
			Connection con  = DriverManager.getConnection("jdbc:mysql://dbs-perso.luminy.univmed.fr:3306/b14017497", "b14017497", "LD.ZY4");

			sb.append("INSERT INTO "+ tableName +" ");
			sb.append("VALUES (");
			
			for(int i = 0; i < parts.length; i++)
			{
				sb.append("?");
				if(i != parts.length -1)	sb.append(",");
			}
			
			sb.append(")");
		
			PreparedStatement stmt = con.prepareStatement(sb.toString());
			
			for(int i = 0; i < parts.length; i++)
			{
				stmt.setString(i + 1, parts[i]);
			}
			
			stmt.execute();
			con.close();
			
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
//		sendRequest(sb.toString());
	}
	
	public void add(String data, String name)
	{
		StringBuilder sb = new StringBuilder ();
		String[] parts = data.split(",");
		
		sb.append("INSERT INTO "+ name +" ");
		sb.append("VALUES (");
		
		for(int i = 0; i < parts.length; i++)
		{
//			sb.append("?");
		
			sb.append(parts[i]);
			if(i != parts.length -1)	sb.append(",");
		}
		
		sb.append(")");
		System.out.println(sb.toString());
//		sendRequest(sb.toString());
	}
	
	/*
	 * Ex�cute la requ�te SQL sp�cifi�e apr�s s'�tre pr�alablement connect� � la base de donn�es
	 */
	public static void sendRequest(String request)
	{
		try
		{
			// ORACLE connection
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			//Connection con  = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "SYSTEM", "Sapristi2");

			// MYSQL connection
			Class.forName("com.mysql.jdbc.Driver");
			Connection con  = DriverManager.getConnection("jdbc:mysql://dbs-perso.luminy.univmed.fr:3306/b14017497", "b14017497", "LD.ZY4");

			Statement stmt = con.createStatement();

			stmt.execute(request);

			con.close();
		}
		catch(Exception e){System.out.println(e);}
	}
}
