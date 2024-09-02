package com.utopiaxc.zombiegame;
import com.utopiaxc.zombiegame.game.ZombieGameWithUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ApplicationTemp {
    private JFrame mFrame;
    public static void main(String[] args) {
        new ApplicationTemp();
    }

    public ApplicationTemp(){
        mFrame = new JFrame("Login Example");
        mFrame.setSize(350, 200);
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        mFrame.add(panel);
        placeComponents(panel);
        mFrame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);
        JLabel userLabel = new JLabel("User:");
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);
        JTextField userText = new JTextField(20);
        userText.setBounds(100,20,165,25);
        panel.add(userText);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);
        JButton loginButton = new JButton("Start Game");
        loginButton.setBounds(10, 80, 80, 25);
        panel.add(loginButton);

        loginButton.addActionListener(_ ->{

            ZombieGameWithUI.getInstance().init();
            mFrame.setVisible(false);
        });
    }
}
