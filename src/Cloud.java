package src;

import java.util.Map;

public class Cloud {
    
    Gateway gateways[];
    Database centralDatabase;
    DatabaseConnection databaseConnection;
    Configuration configuration;

    public Cloud(Configuration _configuration){
        configuration = _configuration;
        databaseConnection = new DatabaseConnection(configuration);
        centralDatabase = new Database (configuration.getCentralDatabaseName(), databaseConnection);
        centralDatabase.createDatabase();
        centralDatabase.useDatabase();
        createCentralDataLedger();
    }

    private void createCentralDataLedger(){
        Map<String,String> schema = configuration.getCentralDatabaseSchema();
        centralDatabase.createTable(configuration.getCentralDatabaseTableName(), schema);
    }   
}