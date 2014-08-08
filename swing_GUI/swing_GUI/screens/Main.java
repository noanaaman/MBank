package screens;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

import actions_classes.AdminAction;
import bank_exceptions.MBankException;
import mank_main.MBank;
 
public class Main {
    public static void main(String[] args) {
        final JFrame frame = new JFrame("MBank");
        final JButton btnLogin = new JButton("Click to login");
 
        btnLogin.addActionListener(
                new ActionListener(){
                    public void actionPerformed(ActionEvent e) {
                        LoginDialog loginDlg = new LoginDialog(frame);
                        loginDlg.setVisible(true);
                        // if logon successfully
                        if(loginDlg.isSucceeded()){
                            try {
								AdminAction adminAction = MBank.getInstance().adminLogin();
								new MainScreen(adminAction);
								frame.dispose();
							} catch (SQLException | MBankException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
                        }
                    }
                });
 
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 100);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().add(btnLogin);
        frame.setVisible(true);
    }
}
