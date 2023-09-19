package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    Connection connection;
    Statement statement;
    Configuration configuration;

    DatabaseConnection(Configuration _configuration){
        configuration = _configuration;
        connectToDatabase();
    }

    private void connectToDatabase(){
        String username = configuration.getUsername();
        String password = configuration.getPassword();
        long portNumber = configuration.getPortNumber();
        String url = "jdbc:mysql://localhost:" + Long.toString(portNumber);
        try { 
            connection = DriverManager.getConnection(url, username, password); 
            statement = connection.createStatement();
        }
        catch(SQLException sqlexception){
            connection = null;
            statement = null;
            System.out.println("Error while connection to the database. \nError: " + sqlexception);
        }
    }

}
