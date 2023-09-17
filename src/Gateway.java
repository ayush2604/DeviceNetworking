package src;
public class Gateway {
    int gatewayID;
    Device connectedDevices[];
    Gateway(){
        gatewayID = 0;
        connectedDevices = new Device[8];
    }
}