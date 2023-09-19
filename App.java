import src.Cloud;
import src.Configuration;
import src.Login;
import src.Gateway;

public class App {

    private String username, password;
    Cloud cloud;
    Gateway gateways[];
    Configuration configuration;
    
    App(){
        loginProcess();
        initialiseGateways();
        cloud = new Cloud(configuration, gateways);
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
        gateways = new Gateway[configuration.getNumberOfGateways()];
        for(int i = 0; i < configuration.getNumberOfGateways(); i++) gateways[i] = new Gateway(configuration);
    }

    private void printStatus() {
        System.out.println("App started.");
    }

    public static void main(String args[]){
        App app = new App();
        app.printStatus();
    }
}
