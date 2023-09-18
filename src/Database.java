package src;

import java.sql.Connection;
import java.sql.Statement;

public class Database {
    
    Configuration configuration;
    Connection connection;
    Statement statement;

    public Database(){
        Login login = new Login();
        connection = login.getConnection();
        configuration = login.getConfiguration();
        statement = login.getStatement();
    }
}
