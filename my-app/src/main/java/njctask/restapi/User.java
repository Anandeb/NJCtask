package njctask.restapi;
 
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import spark.Route;
 
@DatabaseTable(tableName = "users")
public class User {
 
       @DatabaseField(generatedId = true)
    private int id;
 
       @DatabaseField
    private String username;
 
       @DatabaseField
    private String email;

	private Object connectionSource;
 
       public User() {
        // ORMLite needs a no-arg constructor
     }
 
       public int getId() {
        return this.id;
    }
 
       public String getUsername() {
        return this.username;
    }
 
       public void setUsername(String username) {
        this.username = username;
    }
 
       public String getEmail() {
        return email;
    }
 
       public void setEmail(String email) {
        this.email = email;
    }
      
       Dao<User,String> userDao = DaoManager.createDao(connectionSource, User.class);
       
       TableUtils.createTableIfNotExists(connectionSource, User.class);
       
       post(new Route("/users") {
    	    @Override
    	    public Object handle(Request request, Response response) {
    	        String username = request.queryParams("username");
    	        String email = request.queryParams("email");
    	 
    	                       User user = new User();
    	        user.setUsername(username);
    	        user.setEmail(email);
    	 
    	                       userDao.create(user);
    	 
    	                                  response.status(201); // 201 Created
    	     }
    	});
       
       get(new Route("/users/:id") {
    	   @Override
    	   public Object handle(Request request, Response response) {
    	       User user = userDao.queryForId(request.params(":id"));
    	       if (user != null) {
    	           return "Username: " + user.getUsername(); // or JSON? :-)
    	       } else {
    	           response.status(404); // 404 Not found
    	           return "User not found";
    	       }
    	   }
    	});
}