package njctask.restapi;

import static spark.Spark.get;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import spark.Request;
import spark.Response;
import spark.Route;

public class Main {
	public static void main(String[] args) {
		
		get(new Route("/users/:id") {
			@Override
			public Object handle(Request request, Response response) {
				return "User: username=test, email=test@test.net";
			}
		});
		String databaseUrl = "jdbc:mysql://localhost/spark";

		ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl);
		((JdbcConnectionSource) connectionSource).setUsername("spark");
		((JdbcConnectionSource) connectionSource).setPassword("spark");
		
		
	}
}