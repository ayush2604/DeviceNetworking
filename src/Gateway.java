package src;

import java.util.HashMap;
import java.util.Map;

public class Gateway{

    int gatewayID;
    private int numberOfConnectedDevices;
    Map<Integer,Device> connectedDevices;
    Map<Integer,Gateway> gateways;
    Database gatewayDatabase;
    DatabaseConnection databaseConnection;
    Configuration configuration;

    public Gateway(Configuration _configuration){
        connectedDevices = new HashMap<>();
        configuration = _configuration;
        databaseConnection = new DatabaseConnection(configuration);
        numberOfConnectedDevices = 0;
    }

    public void setListOfGateways(Map<Integer,Gateway> _gateways){
        gateways = _gateways;
    }

    public int getNumberOfConnectedDevices(){
        return numberOfConnectedDevices;
    }

    public void connectDevice(Device device){
        if (!connectedDevices.containsKey(device.getDeviceID())) {
            connectedDevices.put(device.getDeviceID(), device);
            numberOfConnectedDevices++;
        }   
    }

    public void disconnectDevice(Device device){
        if (connectedDevices.containsKey(device.getDeviceID())){
            connectedDevices.remove(device.getDeviceID());
            numberOfConnectedDevices--;
        }
    }

    public void sendPacketToDevice (Packet packet){
        if (this.connectedDevices.containsKey(packet.getToDeviceID())) {
            this.connectedDevices.get(packet.getToDeviceID()).recieveDataPacket(packet);
            gatewayDatabase.updateGatewayDataLedger(packet);
        }
    }

    public void recievePacketFromDevice (Packet packet){
        sendPacketToGateway(packet);
        gatewayDatabase.updateGatewayDataLedger(packet);
    }

    public void sendPacketToGateway (Packet packet){
        gateways.get(packet.getToGatewayID()).recievePacketFromGateway(packet);
    }

    public void recievePacketFromGateway (Packet packet){
        this.sendPacketToDevice(packet);
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