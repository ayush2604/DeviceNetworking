package src;

import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {
    JButton b1;  
    JPanel newPanel;  
    JLabel userLabel, passLabel;  
    final JTextField  textField1, textField2;  
    String username, password;
    boolean actionPerformed;

    public Login(){
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
        setSize(400,150); 
        setVisible(true); 
        setLocation(600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        username = "";
        password = "";
        actionPerformed = false;
    }

    public void printStatus(){
        System.out.println("Login process initiated.");
    }

    public synchronized void actionPerformed(ActionEvent ae) {  
        String userValue = textField1.getText();        
        String passValue = textField2.getText();  
        username = userValue;
        password = passValue;
        actionPerformed = true;
        notifyAll();
        setVisible(false);
        dispose();
    }
    
    public synchronized String getUsername () throws InterruptedException{
        while ( !actionPerformed ) wait();
        return username;
    }

    public synchronized String getPassword () throws InterruptedException{
        while ( !actionPerformed ) wait();
        return password;
    }
}
