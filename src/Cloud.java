package src;

import java.util.HashMap;
import java.util.Map;

public class Cloud {
    
    Map<Integer,Gateway> gateways;
    int numberOfConnectedGateways;
    Database centralDatabase;
    DatabaseConnection databaseConnection;
    Configuration configuration;

    public Cloud(Configuration _configuration, Map<Integer, Gateway> _gateways){
        configuration = _configuration;
        numberOfConnectedGateways = 0;
        gateways = _gateways;
        databaseConnection = new DatabaseConnection(configuration);
        centralDatabase = new Database (configuration.getCentralDatabaseName(), databaseConnection);
        centralDatabase.createDatabase();
        centralDatabase.useDatabase();
        createCentralDataLedger();
    }

    public void connectGateway(Gateway gateway){
        if (configuration.getNumberOfGateways() > numberOfConnectedGateways){
            gateway.initialiseGatewayDatabase();
            gateways.put(gateway.getGatewayID(), gateway);
            numberOfConnectedGateways++;
        }
    }

    public void removeGateway(Gateway gateway){
        if (numberOfConnectedGateways > 0){
            if (gateways.containsKey(gateway.getGatewayID())) {
                gateways.remove(gateway.getGatewayID());
                gateway.removeGatewayDatabase();
            }
        }
    }

    private void createCentralDataLedger(){
        Map<String,String> schema = configuration.getCentralDatabaseSchema();
        centralDatabase.createTable(configuration.getCentralDatabaseTableName(), schema);
    }   
}