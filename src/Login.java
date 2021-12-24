import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.UserDAO;

public class Login extends JFrame implements ActionListener{
	
	JFrame frame;
	JPanel mainPanel, titlePanel, formLoginPanel, loginPanel, registerPanel, combinedPanel;
	JLabel titleLabel, usernameLabel, passwordLabel, registerLabel;
	JTextField usernameTextField;
	JPasswordField passwordField;
	JButton loginButton, registerButton;
	
	public Login() {
		initFrame();
	}
	
	public void initMenu() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		
		titlePanel = new JPanel();
		titleLabel = new JLabel("Welcome to Library Management Information");
		titleLabel.setFont(new Font("Open Sans", Font.BOLD, 20));
		titlePanel.setBackground(Color.WHITE);
		titlePanel.setBorder(new EmptyBorder(25, 0, 0, 0));
		
		formLoginPanel = new JPanel();
		formLoginPanel.setLayout(new GridLayout(2, 2, 20, 20));
		formLoginPanel.setBorder(new EmptyBorder(30, 0, 0, 0));
		formLoginPanel.setBackground(Color.WHITE);
		
		usernameLabel = new JLabel("Username:");
		usernameTextField = new JTextField();
		usernameTextField.setPreferredSize(new Dimension(200, 40));
		passwordLabel = new JLabel("Password:");
		passwordField = new JPasswordField();
		passwordField.setPreferredSize(new Dimension(200, 40));
		
		loginPanel = new JPanel();
		loginPanel.setBackground(Color.WHITE);
		
		loginButton = new JButton("Login");
		loginButton.setPreferredSize(new Dimension(150, 40));
		loginButton.setFocusable(false);
		loginButton.setBackground(Color.BLACK);
		loginButton.setForeground(Color.WHITE);
		
		registerPanel = new JPanel();
		registerPanel.setBackground(Color.WHITE);
		registerLabel = new JLabel("Don't have any account yet?");
		registerButton = new JButton("Register");
		registerButton.setBorder(null);
		registerButton.setContentAreaFilled(false);
		registerButton.setForeground(Color.BLUE);
		
		combinedPanel = new JPanel();
		combinedPanel.setBackground(Color.WHITE);
		combinedPanel.setLayout(new GridLayout(2, 1));
		combinedPanel.setBorder(new EmptyBorder(30, 0, 30, 0));
		formLoginPanel.setBackground(Color.WHITE);
		
		titlePanel.add(titleLabel);
		formLoginPanel.add(usernameLabel);
		formLoginPanel.add(usernameTextField);
		formLoginPanel.add(passwordLabel);
		formLoginPanel.add(passwordField);
		loginPanel.add(loginButton);
		registerPanel.add(registerLabel);
		registerPanel.add(registerButton);
		
		combinedPanel.add(loginPanel);
		combinedPanel.add(registerPanel);
		
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(formLoginPanel, BorderLayout.CENTER);
		mainPanel.add(combinedPanel);
		
		loginButton.addActionListener(this);
		registerButton.addActionListener(this);
		
		add(mainPanel);	
	}
	
	public void initFrame() {
		initMenu();
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Login");
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			String username = usernameTextField.getText();
			String password = String.valueOf(passwordField.getPassword());
			try {
				UserDAO userDAO = new UserDAO();
				if (userDAO.authenticationUser(username, password)) {
					this.dispose();
					new Books();
				}else {
					JOptionPane.showMessageDialog(this, "Username or Password is wrong!", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} else if (e.getSource() == registerButton) {
			new Registration();
			this.dispose();
		}	
	}
}
