package src;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Configuration {

    JSONParser jsonparser;
    FileReader file;
    JSONObject jsonobj;

    Configuration(){
        jsonparser = null;
        file = null;
        jsonobj = null;
        init();
    }

    private void init(){
        jsonparser = new JSONParser();
        try { file = new FileReader("\\D:\\Programs\\Java\\IoT\\DeviceNetworking\\config\\config.json"); }
        catch(FileNotFoundException fileNotFoundException){
            System.out.println("Configuration file not found.");
        }
        try { jsonobj = (JSONObject)jsonparser.parse(file); }
        catch(Exception exception){
            jsonobj = null;
            System.out.println("Error while reading configurations. Error: " + exception);
        }
    }

    public Connection getConnection(){
        String username = (String)jsonobj.get("username");
        String password = (String)jsonobj.get("password");
        long portNumber = (long)jsonobj.get("portNumber");
        Connection connection;
        String url = "jdbc:mysql://localhost:" + Long.toString(portNumber);
        try { connection = DriverManager.getConnection(url, username, password); }
        catch(SQLException sqlexception){
            connection = null;
            System.out.println("Error while connection to the database. \nError: " + sqlexception);
        }
        return connection;
    }

    public void initialiseDatabase (Connection connection){
        Statement statement;
        try { statement = connection.createStatement();}
        catch( SQLException sqlexception1){
            statement = null;
            System.out.println("Error while creating database");
        }
        String databaseName = (String)jsonobj.get("database");
        String query = "CREATE DATABASE IF NOT EXISTS " + databaseName + ";";
        int count = 0;
        try { 
            count = statement.executeUpdate(query); 
            System.out.println("Number of rows affected: " + count);
        }
        catch( SQLException sqlexception1){
            System.out.println("Error while creating database");
        }
    }
}