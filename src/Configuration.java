package src;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Configuration {

    JSONParser jsonparser;
    FileReader file;
    JSONObject jsonobj;
    Connection connection;

    Configuration(){
        jsonparser = null;
        file = null;
        jsonobj = null;
        connection = null;
        init();
    }

    private void init(){
        jsonparser = new JSONParser();
        try { file = new FileReader("./config/config.json"); }
        catch(FileNotFoundException fileNotFoundException){
            System.out.println("Configuration file not found.");
        }
        try { jsonobj = (JSONObject)jsonparser.parse(file); }
        catch(Exception exception){
            jsonobj = null;
            System.out.println("Error while reading configurations. Error: " + exception);
        }
    }

    public JSONObject getConfiguration (){
        return this.jsonobj;
    }
}