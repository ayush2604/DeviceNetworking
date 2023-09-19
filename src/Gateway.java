package src;

import java.util.Map;

public class Gateway {

    int gatewayID;
    Device connectedDevices[];
    Database gatewayDatabase;
    DatabaseConnection databaseConnection;
    Configuration configuration;

    public Gateway(Configuration _configuration, int _gatewayID){
        gatewayID = _gatewayID;
        configuration = _configuration;
        databaseConnection = new DatabaseConnection(configuration);
        gatewayDatabase = new Database (configuration.getGatewayDatabaseName(gatewayID), databaseConnection);
        gatewayDatabase.createDatabase();
        gatewayDatabase.useDatabase();
        createGatewayDataLedger();
    }

    private void createGatewayDataLedger(){
        Map<String,String> schema = configuration.getGatewayDatabaseSchema();
        gatewayDatabase.createTable(configuration.getGatewayDatabaseTableName(gatewayID), schema);
    }  
}