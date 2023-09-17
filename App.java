import src.Database;

public class App {
    Database database;

    App(){
        database = new Database();
    }

    private void printStatus(){
        System.out.println("App is up and running.");
    }

    public static void main(String args[]){
        App app = new App();
        app.printStatus();
    }
}
