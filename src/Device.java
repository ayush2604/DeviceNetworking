package src;
class Device{
    private int deviceID;
    private int gatewayID;
    private int sensorData;
    Device(){
        deviceID = 0;
        sensorData = 0;
    }
    public void setDeviceID(int deviceID){
        this.deviceID = deviceID;
    }
    public int getDeviceID(){
        return deviceID;
    }
    public void setGatewayID(int gatewayID){
        this.gatewayID = gatewayID;
    }
    public int getGatewayID(){
        return gatewayID;
    }
    public int sendSensorData(){
        return new Encryption().encrypt(sensorData);
    }
}