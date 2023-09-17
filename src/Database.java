package src;

import java.sql.Connection;

public class Database {
    Configuration configuration;
    Connection connection;
    public Database(){
        configuration = new Configuration();
        connection = configuration.getConnection();
        configuration.initialiseDatabase(connection);
    }
}
