import java.io.BufferedReader;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Wrapper {
	private String name;
	public Wrapper(String name) {
		this.name=name;
		// TODO Auto-generated constructor stub
	}
	public static String readFile( String path ) throws IOException {
		byte [] encoded = Files . readAllBytes(Paths. get(path));
		return new String(encoded , StandardCharsets.UTF_8) ;
	}
	public void createTable1(String data){
		StringBuilder sb = new StringBuilder (); 
		sb.append("CREATE TABLE "+ name +"(");
		String[] lignes = data.split ("\n" );
		String[] premiereLigne = lignes[0].split(",");
		sb.append("char[25] " + premiereLigne[0] +",");
		sb.append("char[25] " + premiereLigne[1] +",");
		sb.append("integer " + premiereLigne[2] +",");
		sb.append("integer " + premiereLigne[3] +",");
		sb.append("integer " + premiereLigne[4] +",");
		sb.append("char[25] " + premiereLigne[5] + ",");
		sb.append("char[25] " + premiereLigne[6] +")");

		for(int i =1; i<lignes.length;i++){
			add(lignes[i], name);
		}
	}
	public void createTable2(String data){
		StringBuilder sb = new StringBuilder (); 
		sb.append("CREATE TABLE "+ name +"(");
		String[] lignes = data.split ("\n" );
		String[] premiereLigne = lignes[0].split(",");
		sb.append("char[25] " + premiereLigne[0] +",");
		sb.append("char[25] " + premiereLigne[1] +",");
		sb.append("char[25] " + premiereLigne[2] +",");
		sb.append("char[25] " + premiereLigne[3] +",");
		sb.append("char[25] " + premiereLigne[4] +",");
		sb.append("Date " + premiereLigne[5] + ",");
		sb.append("bool " + premiereLigne[6] +",");// verifier type
		sb.append("char[25] " + premiereLigne[7] +",");
		sb.append("char[25] " + premiereLigne[8] +",");
		sb.append("char[25] " + premiereLigne[9] +")");

		for(int i =1; i<lignes.length;i++){
			add(lignes[i], name);
		}
	}
	public void createTable3(String data){
		StringBuilder sb = new StringBuilder (); 
		sb.append("CREATE TABLE "+ name +"(");
		String[] lignes = data.split ("\n" );
		String[] premiereLigne = lignes[0].split(",");
		sb.append("char[25] " + premiereLigne[0] +",");
		sb.append("char[25] " + premiereLigne[1] +",");
		sb.append("char[25] " + premiereLigne[2] +",");
		sb.append("char[25] " + premiereLigne[3] +",");
		sb.append("char[25] " + premiereLigne[4] +",");
		sb.append("Date " + premiereLigne[5] + ",");
		sb.append("bool " + premiereLigne[6] +",");// verifier type
		sb.append("char[25] " + premiereLigne[7] +",");
		sb.append("char[25] " + premiereLigne[8] +",");
		sb.append("char[25] " + premiereLigne[9] +")");

		for(int i =1; i<lignes.length;i++){
			add(lignes[i], name);
		}
	}
	public void add(String data, String name){
		StringBuilder sb = new StringBuilder ();
		String[] parts=data.split(",");
		sb.append("INSERT INTO "+ name +" ");
		sb.append("VALUES (");
		for(int i = 0; i<parts.length; i++){
			sb.append(parts[i]);
			if(i!= parts.length -1)	sb.append(",");
		}
		sb.append(")");
		System.out.println(sb);
	}
	public void sendRequest(String request){
		try{
			//step1 load the driver class  
			Class.forName("oracle.jdbc.driver.OracleDriver");

			//step2 create  the connection object  
			Connection con  = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","oracle");

			Statement stmt = con.createStatement();

			//step4 execute query  
			ResultSet rs = stmt.executeQuery(request);
			while(rs.next())
				System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));

			//step5 close the connection object  
			con.close();
		}catch(Exception e){System.out.println(e);}
	}
}
