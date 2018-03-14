package fr.master1isi;
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

import com.opencsv.CSVReader;

import java.sql.* ;  // for standard JDBC programs
import java.math.* ; // for BigDecimal and BigInteger support

public class Wrapper {
	private String name;
	
	private CSVReader reader;
	
	public Wrapper(String name) {
		this.name=name;
		// TODO Auto-generated constructor stub
	}
	@SuppressWarnings("deprecation")
	public void readFile( String path ) throws IOException {
		this.reader = new CSVReader(new FileReader(new File(path)), ',');
		
//		byte [] encoded = Files . readAllBytes(Paths. get(path));
//		return new String(encoded , StandardCharsets.UTF_8) ;
	}
	public void createTable(String data){
		int i;
		
		StringBuilder sb = new StringBuilder (); 
		
		sb.append("CREATE TABLE "+ name +"(");
		
		try {
			String[] premiereLigne = reader.readNext();
			
			
			for (i = 0; i<premiereLigne.length -1 ; i++)
				sb.append(name + "_" + premiereLigne[i] + " TEXT, ");
			
			sb.append(name + "_" + premiereLigne[i] +" TEXT )" );
			
			System.out.println(sb);
			sendRequest(sb.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		for( i =1; i<lignes.length;i++){
//			add(lignes[i], name);
//		}
		
	}
	
	public void fillTable(){
		String[] datas;
		
		try {
			int cpt = 0;
			while((datas = reader.readNext()) != null){
				System.out.println(cpt);
				add2(datas);
				cpt++;
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public void add2(String[] data){
		StringBuilder sb = new StringBuilder ();
		String[] parts = data;
		
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
			Connection con  = DriverManager.getConnection("jdbc:mysql://dbs-perso.luminy.univmed.fr:3306/b14017497", "b14017497", "LD.ZY4");

			sb.append("INSERT INTO "+ name +" ");
			sb.append("VALUES (");
			
			for(int i = 0; i < parts.length; i++){
				sb.append("?");
				if(i != parts.length -1)	sb.append(",");
			}
			
			sb.append(")");
		
			PreparedStatement stmt = con.prepareStatement(sb.toString());
			
			for(int i = 0; i < parts.length; i++){
				stmt.setString(i + 1, parts[i]);
			}
			
			stmt.execute();
			con.close();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
//		sendRequest(sb.toString());
	}
	
	public void add(String data, String name){
		StringBuilder sb = new StringBuilder ();
		String[] parts = data.split(",");
		
		sb.append("INSERT INTO "+ name +" ");
		sb.append("VALUES (");
		
		for(int i = 0; i < parts.length; i++){
//			sb.append("?");
		
			sb.append(parts[i]);
			if(i != parts.length -1)	sb.append(",");
		}
		
		sb.append(")");
		System.out.println(sb.toString());
//		sendRequest(sb.toString());
	}
	public void sendRequest(String request){


		try{
			Class.forName( "com.mysql.jdbc.Driver" );
			Connection con  = DriverManager.getConnection("jdbc:mysql://dbs-perso.luminy.univmed.fr:3306/b14017497", "b14017497", "LD.ZY4");

			Statement stmt = con.createStatement();

			stmt.execute(request);
			//step4�execute�query��
			//ResultSet rs = stmt.executeQuery(request);
//			while(rs.next())
//				System.out.println(rs.getInt(1)+"��"+rs.getString(2)+"��"+rs.getString(3));

			//step5�close�the�connection�object��
			con.close();
		}catch(Exception e){System.out.println(e);}
	}
}
