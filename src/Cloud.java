package src;

import java.util.Map;

public class Cloud {
    
    Gateway gateways[];
    Database centralDatabase;
    DatabaseConnection databaseConnection;
    Configuration configuration;

    public Cloud(Configuration _configuration, Gateway _gateways[]){
        configuration = _configuration;
        gateways = _gateways;
        databaseConnection = new DatabaseConnection(configuration);
        centralDatabase = new Database (configuration.getCentralDatabaseName(), databaseConnection);
        centralDatabase.createDatabase();
        centralDatabase.useDatabase();
        createCentralDataLedger();
        assignIDsToGateways();
    }

    private void assignIDsToGateways(){
        int counter = 0;
        for (Gateway g : gateways){
            g.setGatewayID(counter++);
            g.initialiseGatewayDatabase();
        }
    }

    private void createCentralDataLedger(){
        Map<String,String> schema = configuration.getCentralDatabaseSchema();
        centralDatabase.createTable(configuration.getCentralDatabaseTableName(), schema);
    }   
}