package src;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class Database {
    
    private String databaseName;
    DatabaseConnection databaseConnection;

    public Database(String name, DatabaseConnection _databaseConnection){
        databaseName = name;
        databaseConnection = _databaseConnection;
    }

    public String getDatabaseName(){
        return databaseName;
    }

    public void useDatabase(){
        executeUpdateQuery("USE " + databaseName + ";");
    }

    public void executeUpdateQuery (String query){
        try {  databaseConnection.statement.executeUpdate(query); }
        catch( SQLException sqlexception){
            System.out.println("Error while running update query.\nQuery: " + query + "\nError: " + sqlexception);
        }
    }

    public ResultSet executeResultQuery (String query){
        ResultSet resultSet = null;
        try {  resultSet = databaseConnection.statement.executeQuery(query); }
        catch( SQLException sqlexception){
            System.out.println("Error while running result query.\nQuery: " + query + "\nError: " + sqlexception);
        }
        return resultSet;
    }

    public void createDatabase (){
        String query = "CREATE DATABASE IF NOT EXISTS " + databaseName + ";";
        executeUpdateQuery(query);
    }

    public void createTable(String tableName, Map<String,String> schema){
        String query = "CREATE TABLE IF NOT EXISTS " + tableName + " (";
        for (Map.Entry<String,String> entry : schema.entrySet()){
            query = query + " " + entry.getKey() + " " + entry.getValue() + ",";
        }
        query = query.substring(0, query.length() - 1) + " );";
        executeUpdateQuery(query);
    }
}
