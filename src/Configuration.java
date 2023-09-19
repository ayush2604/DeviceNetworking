package src;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Configuration {

    FileReader file;
    JSONObject jsonobj;
    private String username, password;

    public Configuration(String _username, String _password){
        username = _username;
        password = _password;
        init();
    }

    private void init(){
        JSONParser jsonparser = new JSONParser();
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

    private String dateToday(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyymmdd");
        return dateFormat.format(date);
    }

    public String getCentralDatabaseTableName(){
        return getCentralDatabaseName() + "_" + dateToday();
    }

    public Map<String,String> getCentralDatabaseSchema(String tableName){
        Map<String,String> schema = new HashMap<>();
        JSONArray columns = (JSONArray) jsonobj.get("columns");
        for (int j = 0; j < columns.size(); j++){
            try { 
                JSONObject columnName = (JSONObject) columns.get(j); 
                schema.put( (String)columnName.get("columnName"), (String)columnName.get("dataType"));
            }
            catch (JSONException jsonException){
                System.out.println("JSON Exception.\nError: " + jsonException);
            }
        }
        return schema;
    }

    public String getCentralDatabaseName(){
        return (String) jsonobj.get("database");
    }

    public String getUsername (){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public long getPortNumber(){
        return (long) jsonobj.get("portNumber");
    }
}