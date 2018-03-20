package modele;

public class Mediator {
	public Mediator() {
		// TODO Auto-generated constructor stub
	}
	public void Vue1(){
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE VIEW view1 AS ");
		sb.append("SELECT wrap1.city, wrap3.city, 2014_murders, wrap3.2015_murders, wrap3.2016_murders "
				+ "FROM wrap1, wrap3 WHERE wrap1.city = wrap3.city");
		Wrapper.sendRequest(sb.toString());
	}

}
