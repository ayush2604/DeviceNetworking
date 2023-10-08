import src.Cloud;
import src.Configuration;
import src.Login;
import src.Gateway;
import src.Device;
import java.util.*;
import java.util.Map.Entry; 

public class App {

    private String username, password;
    Cloud cloud;
    Map<Integer,Gateway> gateways;
    Configuration configuration;
    
    App(){
        loginProcess();
        initialiseGateways();
        initialiseDevices();
        initialiseCloud();
    }

    private void loginProcess(){
        Login login = new Login();
        try {
            username = login.getUsername();
            password = login.getPassword();
        } catch (InterruptedException interruptedException) {
            System.out.println("Error: " + interruptedException);
        }
        configuration = new Configuration(username, password);
    }

    private void initialiseGateways(){
        gateways = new HashMap<>();
        for(int i = 0; i < configuration.getNumberOfGateways(); i++) {
            Gateway gateway = new Gateway(configuration);
            gateway.setGatewayID(i);
            gateways.put(gateway.getGatewayID(), gateway);
        }
    }

    private void initialiseDevices(){
        int deviceID = 0;
        for(Entry<Integer,Gateway> entry: gateways.entrySet()) {
            for (int i = 0; i < configuration.getNumberOfDevicesInGateway(); i++) {
                Gateway gateway = entry.getValue();
                Device device = new Device(configuration);
                device.setDeviceID(deviceID++);
                device.setGatewayID(gateway.getGatewayID());
                device.getGateways(gateways);
                gateway.connectDevice(device);
            }
        }
    }

    private void initialiseCloud(){
        cloud = new Cloud(configuration, gateways);
    }

    private void printStatus() {
        System.out.println("App started.");
    }

    public static void main(String args[]){
        App app = new App();
        app.printStatus();
    }
}
