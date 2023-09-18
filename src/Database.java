package src;

import java.sql.Connection;

public class Database {
    Configuration configuration;
    Connection connection;
    public Database(){
        Login login = new Login();
        connection = login.getConnection();
        configuration = login.getConfiguration();
    }
}
