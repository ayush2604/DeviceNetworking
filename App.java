import src.Cloud;
import src.Configuration;
import src.Login;

public class App {

    private String username, password;
    Cloud cloud;
    Configuration configuration;
    
    App(){
        Login login = new Login();
        try {
            username = login.getUsername();
            password = login.getPassword();
        } catch (InterruptedException interruptedException) {
            System.out.println("Error: " + interruptedException);
        }
        configuration = new Configuration(username, password);
        cloud = new Cloud(configuration);
    }

    private void printStatus() {
        System.out.println("App started.");
    }

    public static void main(String args[]){
        App app = new App();
        app.printStatus();
    }
}
