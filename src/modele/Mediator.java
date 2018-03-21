package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Mediator
{
	private String viewName;
	
	public Mediator(String viewName)
	{
		this.viewName = viewName;
	}
	
	public void Vue1(){
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE VIEW "+ viewName + " AS ");
		sb.append("SELECT wrap1.city, wrap3.city, 2014_murders, wrap3.2015_murders, wrap3.2016_murders "
				+ "FROM wrap1, wrap3 WHERE wrap1.city = wrap3.city");
		sendQuery(sb.toString());
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
