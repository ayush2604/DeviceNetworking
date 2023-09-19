package src;

import java.util.HashMap;
import java.util.Map;

public class Gateway {

    int gatewayID;
    Map<Integer,Device> connectedDevices;
    Database gatewayDatabase;
    DatabaseConnection databaseConnection;
    Configuration configuration;

    public Gateway(Configuration _configuration){
        connectedDevices = new HashMap<>();
        configuration = _configuration;
        databaseConnection = new DatabaseConnection(configuration);
    }

    private void createGatewayDataLedger(){
        Map<String,String> schema = configuration.getGatewayDatabaseSchema();
        gatewayDatabase.createTable(configuration.getGatewayDatabaseTableName(gatewayID), schema);
    }  

    public void setGatewayID(int id){
        this.gatewayID = id;
    }

    public void initialiseGatewayDatabase(){
        gatewayDatabase = new Database (configuration.getGatewayDatabaseName(gatewayID), databaseConnection);
        gatewayDatabase.createDatabase();
        gatewayDatabase.useDatabase();
        createGatewayDataLedger();
    }

    public void removeGatewayDatabase(){
        gatewayDatabase.removeDatabase();
    }

    public int getGatewayID(){
        return this.gatewayID;
    }
}