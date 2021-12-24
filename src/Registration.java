import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import dao.UserDAO;

public class Registration extends JFrame implements ActionListener{

	JFrame frame;
	JPanel titlePanel, mainPanel,formPanel, genderPanel, registerPanel, loginPanel, combinedPanel;
	JLabel titleLabel, nameLabel, addressLabel, genderLabel, ageLabel, passwordLabel, repasswordLabel, loginLabel;
	JTextField nameTextField, addressTextField;
	JRadioButton maleRadioButton, femaleRadioButton;
	ButtonGroup genderButtonGroup;
	JPasswordField passwordField, repasswordField;
	JComboBox<Integer> ageComboBox;
	JButton registerButton, loginButton;
	
	public Registration() {
		initFrame();
	}
	
	public void initRegistration() {
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
		mainPanel.setBackground(Color.WHITE);
		
		formPanel = new JPanel();
		formPanel.setLayout(new GridLayout(6, 2, 20, 20));
		formPanel.setBackground(Color.WHITE);
		
		titleLabel = new JLabel("Name:");
		nameTextField = new JTextField();
		nameTextField.setPreferredSize(new Dimension(200, 40));
		
		addressLabel = new JLabel("Address:");
		addressTextField = new JTextField();
		addressTextField.setPreferredSize(new Dimension(200, 40));
		
		genderPanel = new JPanel();
		genderPanel.setLayout(new GridLayout(1, 2));
		genderPanel.setBackground(Color.WHITE);
		
		genderLabel = new JLabel("Gender:");
		maleRadioButton = new JRadioButton("Male");
		maleRadioButton.setBackground(Color.WHITE);
		femaleRadioButton = new JRadioButton("Female");
		femaleRadioButton.setBackground(Color.WHITE);
		genderButtonGroup = new ButtonGroup();
		genderButtonGroup.add(maleRadioButton);
		genderButtonGroup.add(femaleRadioButton);
		
		genderPanel.add(maleRadioButton);
		genderPanel.add(femaleRadioButton);
		
		ageLabel = new JLabel("Age:");
		Integer [] ageArray = {17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35}; 
		ageComboBox = new JComboBox<>(ageArray);
		ageComboBox.setBackground(Color.WHITE);
		
		passwordLabel = new JLabel("Password:");
		passwordField = new JPasswordField();
		repasswordLabel = new JLabel("Re-password:");
		repasswordField = new JPasswordField();
		
		formPanel.add(titleLabel);
		formPanel.add(nameTextField);
		formPanel.add(addressLabel);
		formPanel.add(addressTextField);
		formPanel.add(genderLabel);
		formPanel.add(genderPanel);
		formPanel.add(ageLabel);
		formPanel.add(ageComboBox);
		formPanel.add(passwordLabel);
		formPanel.add(passwordField);
		formPanel.add(repasswordLabel);
		formPanel.add(repasswordField);
		
		combinedPanel = new JPanel();
		combinedPanel.setLayout(new GridLayout(2, 1));
		combinedPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
		combinedPanel.setBackground(Color.WHITE);
		
		registerPanel = new JPanel();
		registerPanel.setBackground(Color.WHITE);
		
		registerButton = new JButton("Register");
		registerButton.setPreferredSize(new Dimension(150, 40));
		registerButton.setFocusable(false);
		registerButton.setBackground(Color.BLACK);
		registerButton.setForeground(Color.WHITE);
		
		loginPanel = new JPanel();
		loginPanel.setBackground(Color.WHITE);
		
		loginLabel = new JLabel("Already have an account?");
		loginButton = new JButton("Login");
		loginButton.setBorder(null);
		loginButton.setContentAreaFilled(false);
		loginButton.setForeground(Color.BLUE);
		
		registerPanel.add(registerButton);
		loginPanel.add(loginLabel);
		loginPanel.add(loginButton);
		
		combinedPanel.add(registerPanel);
		combinedPanel.add(loginPanel);
		
		mainPanel.add(formPanel, BorderLayout.NORTH);
		mainPanel.add(combinedPanel);
		
		registerButton.addActionListener(this);
		loginButton.addActionListener(this);
		
		add(mainPanel);
	}
	
	public void initFrame() {
		initRegistration();
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Registration");
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == registerButton) {
			String name = nameTextField.getText();
			String address = addressTextField.getText();
			String gender = maleRadioButton.isSelected() ? "Male" : "Female";
			Integer age = (Integer)ageComboBox.getSelectedItem();
			String password = String.valueOf(passwordField.getPassword());
			String repassword = String.valueOf(repasswordField.getPassword());
			if (validInput(name, address, gender, age, password, repassword)) {
				try {
					UserDAO userDAO = new UserDAO();
					userDAO.insertNewUser(name, address, gender, age, password, repassword);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getSource() == loginButton) {
			new Login();
			this.dispose();
		}
	}
	
	public boolean validInput(String name, String address, String gender, Integer age, String password, String repassword) {
		if (!name.isEmpty()) {
			if (!address.isEmpty()) {
				if (maleRadioButton.isSelected() == true || femaleRadioButton.isSelected() == false) {
					if (ageComboBox.getSelectedItem() != null)  {
						if (isAlphaNumeric(password)) {
							if (repassword.equals(password)) {
								JOptionPane.showMessageDialog(this, "Successfully registered!", "Information", JOptionPane.INFORMATION_MESSAGE);
								return true;
							}else {
								JOptionPane.showMessageDialog(this, "Password does not match!", "Warning", JOptionPane.WARNING_MESSAGE);
							}
						}else {
							JOptionPane.showMessageDialog(this, "Password must be at least 8 characters and alphanumeric!", "Warning", JOptionPane.WARNING_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(this, "Age must be selected!", "Warning", JOptionPane.WARNING_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(this, "Gender must be selected!", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(this, "Address must be filled!", "Warning", JOptionPane.WARNING_MESSAGE);
			}
		}else {
			JOptionPane.showMessageDialog(this, "Name must be filled!", "Warning", JOptionPane.WARNING_MESSAGE);
		}
		return false;
	}
	
	public boolean isAlphaNumeric (String password) {
		boolean hasAlpha = false;
		boolean hasNumeric = false;
		
		if (password.length()>=8) {
			for (int i = 0; i < password.length(); i++) {
				if (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z') hasAlpha = true;
				else if (password.charAt(i) >= 'a' && password.charAt(i) <= 'z') hasAlpha = true;
				else if (password.charAt(i) >= '0' && password.charAt(i) <= '9') hasNumeric = true;
				else return false;
			}
		}
		return hasAlpha && hasNumeric;
	}
}
