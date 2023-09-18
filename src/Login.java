package src;

import javax.swing.*;
import org.json.simple.JSONObject;
import java.awt.*;  
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;  

public class Login extends JFrame implements ActionListener{
    JButton b1;  
    JPanel newPanel;  
    JLabel userLabel, passLabel;  
    final JTextField  textField1, textField2;  
    String username, password;
    Connection connection;
    Configuration config;

    Login(){
        userLabel = new JLabel();  
        userLabel.setText("Username");
        textField1 = new JTextField(15); 
        passLabel = new JLabel();  
        passLabel.setText("Password");  
        textField2 = new JPasswordField(15); 
        b1 = new JButton("SUBMIT"); 
        newPanel = new JPanel(new GridLayout(3, 1));  
        newPanel.add(userLabel);    
        newPanel.add(textField1);    
        newPanel.add(passLabel);    
        newPanel.add(textField2);   
        newPanel.add(b1);           
        add(newPanel, BorderLayout.CENTER);     
        b1.addActionListener(this);   
        setTitle("ENTER DATABASE CREDENTIALS");   
        setSize(300,150); 
        setVisible(true); 
        setLocation(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        username = "";
        password = "";
        config = new Configuration();
    }

    public void printStatus(){
        System.out.println("Login process initiated.");
    }

    public void actionPerformed(ActionEvent ae) {  
        String userValue = textField1.getText();        
        String passValue = textField2.getText();  
        username = userValue;
        password = passValue;
        connectToDatabase();
        initialiseDatabase(connection);
        setVisible(false);
        dispose();
    }

    private void connectToDatabase(){
        long portNumber = (long)config.getConfiguration().get("portNumber");
        String url = "jdbc:mysql://localhost:" + Long.toString(portNumber);
        try { connection = DriverManager.getConnection(url, username, password); }
        catch(SQLException sqlexception){
            connection = null;
            System.out.println("Error while connection to the database. \nError: " + sqlexception);
        }
    }

    private void initialiseDatabase (Connection connection){
        Statement statement;
        try { statement = connection.createStatement();}
        catch( SQLException sqlexception1){
            statement = null;
            System.out.println("Error while creating database");
        }
        String databaseName = (String)config.getConfiguration().get("database");
        String query = "CREATE DATABASE IF NOT EXISTS " + databaseName + ";";
        int count = 0;
        try { 
            count = statement.executeUpdate(query); 
            System.out.println("Number of rows affected: " + count);
        }
        catch( SQLException sqlexception1){
            System.out.println("Error while creating database.");
        }
    }

    public Connection getConnection(){
        return this.connection;
    }

    public Configuration getConfiguration(){
        return this.config;
    }
}
