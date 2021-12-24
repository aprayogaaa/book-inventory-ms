import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.BookDAO;

public class Books extends JFrame implements ActionListener{
	
	JFrame frame;
	JPanel mainPanel, insertPanel, bottonPanel;
	JLabel bookIdLabel, nameLabel, genreLabel, authorLabel, numPageLabel, publishDateLabel, publisherLabel, pagesLabel;
	JTextField bookIdTextField, nameTextField, authorTextField, publishDateTextField, publisherTextField, pagesTextField;
	JButton insertButton, updateButton, deleteButton;	
	JComboBox<String> genreComboBox;
	JTable table;
	JScrollPane scrollPane;
	DefaultTableModel defaultTableModel;
	
	public Books() throws SQLException {
		initFrame();
	}
	
	public void initView() throws SQLException {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		
		Vector<Object> columns = new Vector<>();
		
		columns.add("Book Id");
		columns.add("Name");
		columns.add("Genre");
		columns.add("Author(s)");
		columns.add("Publish Date");
		columns.add("Publisher");
		columns.add("Number of Pages");
		
		BookDAO bookDAO = new BookDAO();
		
		defaultTableModel = new DefaultTableModel(bookDAO.getData(), columns);
		table = new JTable(defaultTableModel);
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(1000, 250));
		
		mainPanel.add(scrollPane);
		mainPanel.setBorder(new EmptyBorder(0, 0, 50, 0));
		
		add(mainPanel, BorderLayout.SOUTH);
	}
	
	public void initButton() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		
		bottonPanel = new JPanel(new GridLayout(1, 3, 50, 0));
		bottonPanel.setBackground(Color.WHITE);
		
		insertButton = new JButton("Insert");
		insertButton.setPreferredSize(new Dimension(120, 35));
		insertButton.setFocusable(false);
		insertButton.setBackground(Color.BLACK);
		insertButton.setForeground(Color.WHITE);
		updateButton = new JButton("Update");
		updateButton.setPreferredSize(new Dimension(120, 35));
		updateButton.setFocusable(false);
		updateButton.setBackground(Color.BLACK);
		updateButton.setForeground(Color.WHITE);
		deleteButton = new JButton("Delete");
		deleteButton.setPreferredSize(new Dimension(120, 35));
		deleteButton.setFocusable(false);
		deleteButton.setBackground(Color.BLACK);
		deleteButton.setForeground(Color.WHITE);
		
		bottonPanel.add(insertButton);
		bottonPanel.add(updateButton);
		bottonPanel.add(deleteButton);
		bottonPanel.setBorder(new EmptyBorder(25, 0, 0, 0));
		
		mainPanel.add(bottonPanel);
		
		insertButton.addActionListener(this);
		updateButton.addActionListener(this);
		deleteButton.addActionListener(this);
		
		add(mainPanel, BorderLayout.CENTER);
	}
	
	public void initInsert() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		
		insertPanel = new JPanel(new GridLayout(7, 2, 0, 10));
		insertPanel.setBackground(Color.WHITE);
		
		bookIdLabel = new JLabel("Book Id:");
		bookIdTextField = new JTextField();
		bookIdTextField.setPreferredSize(new Dimension(400, 40));
		
		nameLabel = new JLabel("Name:");
		nameTextField = new JTextField();
		nameTextField.setPreferredSize(new Dimension(400, 40));
		
		genreLabel = new JLabel("Genre:");
		String [] genreArr = {"Select a Genre", "Drama", "Fable", "Fairy Tale", "Fantasy", "Fiction", "Folklore", "Horror",
				"Humor", "Legend", "Mistery", "Mythology", "Petry", "Realistic Fiction", "Science", "Short Story", 
				"Tall Tale", "Biography/Autobiography", "Essay", "Narrative Nonfiction", "Nonfiction", "Novel"};
		genreComboBox = new JComboBox<String>(genreArr);
		
		authorLabel = new JLabel("Author(s):");
		authorTextField = new JTextField();
		authorTextField.setPreferredSize(new Dimension(400, 40));
		
		publishDateLabel = new JLabel("Publish Date:");
		publishDateTextField = new JTextField();
		publishDateTextField.setPreferredSize(new Dimension(400, 40));
		
		publisherLabel = new JLabel("Publisher:");
		publisherTextField = new JTextField();
		publisherTextField.setPreferredSize(new Dimension(400, 40));
		
		pagesLabel = new JLabel("Number of Pages:");
		pagesTextField = new JTextField();
		pagesTextField.setPreferredSize(new Dimension(400, 40));
		
		insertPanel.add(bookIdLabel);
		insertPanel.add(bookIdTextField);
		insertPanel.add(nameLabel);
		insertPanel.add(nameTextField);
		insertPanel.add(genreLabel);
		insertPanel.add(genreComboBox);
		insertPanel.add(authorLabel);
		insertPanel.add(authorTextField);
		insertPanel.add(publishDateLabel);
		insertPanel.add(publishDateTextField);
		insertPanel.add(publisherLabel);
		insertPanel.add(publisherTextField);
		insertPanel.add(pagesLabel);
		insertPanel.add(pagesTextField);
		
		mainPanel.add(insertPanel);
		mainPanel.setBorder(new EmptyBorder(20, 0, 0, 0));
		
		add(mainPanel, BorderLayout.NORTH);
	}
	
	public void initFrame() throws SQLException {
		initInsert();
		initButton();
		initView();
		setSize(1200, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Book's Inventory");
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == insertButton) {
			String bookName = nameTextField.getText();
			String bookGenre = (String) genreComboBox.getSelectedItem(); 
			String bookAuthor = authorTextField.getText(); 
			String publishDate = publishDateTextField.getText();
			String publisher = publisherTextField.getText(); 
			Integer numOfPage;
			try {
				numOfPage = Integer.parseInt(pagesTextField.getText());
			} catch (Exception e2) {
				numOfPage = -1;
			}
			
			if (isValidInput(bookName, bookGenre, bookAuthor, publishDate, publisher, numOfPage)) {
				try {
					BookDAO bookDAO = new BookDAO();
					bookDAO.insertBook(bookName, bookGenre, bookAuthor, publishDate, publisher, numOfPage);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
			
			try {
				new Books();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}else if (e.getSource() == updateButton) {
			String bookId = bookIdTextField.getText();
			String bookName = nameTextField.getText();
			String bookGenre = (String) genreComboBox.getSelectedItem(); 
			String bookAuthor = authorTextField.getText(); 
			String publishDate = publishDateTextField.getText();
			String publisher = publisherTextField.getText(); 
			Integer numOfPage;
			try {
				numOfPage = Integer.parseInt(pagesTextField.getText());
			} catch (Exception e2) {
				numOfPage = -1;
			}
			
			if (isValidUpdate(bookId, bookName, bookGenre, bookAuthor, publishDate, publisher, numOfPage)) {
				try {
					BookDAO bookDAO = new BookDAO();
					bookDAO.updateBook(bookId, bookName, bookGenre, bookAuthor, publishDate, publisher, numOfPage);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			try {
				new Books();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}else if (e.getSource() == deleteButton) {
			String bookId = bookIdTextField.getText();
			if (isValidDelete(bookId)) {
				try {
					BookDAO bookDAO = new BookDAO();
					bookDAO.deleteBook(bookId);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			try {
				new Books();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public boolean isValidInput(String bookName, String bookGenre, String bookAuthor, String publishDate, String publisher, Integer numOfPage) {
		if (!bookName.isEmpty()) {
			if (!bookGenre.isEmpty()) {
				if (!bookAuthor.isEmpty()) {
					if (!publishDate.isEmpty()) {
						if (!publisher.isEmpty()) {
							if (numOfPage > 0) {
								JOptionPane.showMessageDialog(this, "Book successfully inserted!", "Information", JOptionPane.INFORMATION_MESSAGE);
								return true;
							}else {
								JOptionPane.showMessageDialog(this, "Page(s) must be filled more than 0!", "Information", JOptionPane.INFORMATION_MESSAGE);
							}
						}else {
							JOptionPane.showMessageDialog(this, "Publisher must be filled!", "Information", JOptionPane.INFORMATION_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(this, "Publish date must be filled!", "Information", JOptionPane.INFORMATION_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(this, "Author(s) must be filled!", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(this, "Genre must be filled!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		}else {
			JOptionPane.showMessageDialog(this, "Book Name must be filled!", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		return false;
	}
	
	public boolean isValidUpdate(String bookId, String bookName, String bookGenre, String bookAuthor, String publishDate, String publisher, Integer numOfPage) {
		if (!bookId.isEmpty()) {
			if (!bookName.isEmpty()) {
				if (!bookGenre.isEmpty()) {
					if (!bookAuthor.isEmpty()) {
						if (!publishDate.isEmpty()) {
							if (!publisher.isEmpty()) {
								if (numOfPage > 0) {
									JOptionPane.showMessageDialog(this, "Book successfully updated!", "Information", JOptionPane.INFORMATION_MESSAGE);
									return true;
								}else {
									JOptionPane.showMessageDialog(this, "Page(s) must be filled more than 0!", "Information", JOptionPane.INFORMATION_MESSAGE);
								}
							}else {
								JOptionPane.showMessageDialog(this, "Publisher must be filled!", "Information", JOptionPane.INFORMATION_MESSAGE);
							}
						}else {
							JOptionPane.showMessageDialog(this, "Publish date must be filled!", "Information", JOptionPane.INFORMATION_MESSAGE);
						}
					}else {
						JOptionPane.showMessageDialog(this, "Author(s) must be filled!", "Information", JOptionPane.INFORMATION_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(this, "Genre must be filled!", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
			}else {
				JOptionPane.showMessageDialog(this, "Book Name must be filled!", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		}else {
			JOptionPane.showMessageDialog(this, "Book Id must be filled!", "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		return false;
	}
	
	public boolean isValidDelete(String bookId) {
		if (!bookId.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Book successfully deleted!", "Information", JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}
}
