package doctor;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LoginPanel extends JPanel {
	/**
	 *	Default Serialization 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Keeps track of which view is currently visible to the user.
	 */
	private int view;
	
	/**
	 * The parent of this object.
	 */
	private FrontendGUI parent;
	
	
	//static variables to define the two views
	private static int LOGIN = 0;
	private static int CREATE = 1;
	
	//gui elements
	private JPanel loginPanel;
	private JPanel createUserPanel;
	
	public LoginPanel(FrontendGUI parent){
		super(new BorderLayout());
		this.parent = parent;
		createUserPanelInit();
		loginPanelInit();
		add(loginPanel, BorderLayout.CENTER);
		view = LOGIN;
	}
	
	//gui elements
	private JLabel loginLabel;
	private Seperator loginSeperator;
	private EntryField loginUsernameField;
	private EntryField loginPasswordField;
	private JButton loginSubmitButton;
	private JButton loginNewUserButton;
	
	private void loginPanelInit(){
		loginPanel = new JPanel(new GridBagLayout());
		
		loginLabel = new JLabel("Smartpill Login");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(0, 3, 0, 0);
		gbc.fill = GridBagConstraints.NONE;
		loginPanel.add(loginLabel, gbc);
		
		loginSeperator = new Seperator(3);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 0, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		loginPanel.add(loginSeperator, gbc);
		
		loginUsernameField = new EntryField("Username");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		loginPanel.add(loginUsernameField, gbc);
		
		loginPasswordField = new EntryField("Password", true);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		loginPanel.add(loginPasswordField, gbc);
		
		loginSubmitButton = new JButton("Submit");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 3);
		gbc.fill = GridBagConstraints.NONE;
		loginPanel.add(loginSubmitButton, gbc);
		
		loginNewUserButton = new JButton("Create New User");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.NONE;
		loginPanel.add(loginNewUserButton, gbc);
		
		//spacer panel to push the buttons to the left side of the panel
		JPanel buttonSpacer = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		loginPanel.add(buttonSpacer, gbc);
		
		//spacer panel to push the content to the top of the panel
		JPanel spacer = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		loginPanel.add(spacer, gbc);
		
		loginSubmitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean validated = checkLoginInfo();
				if (!validated){
					JOptionPane.showMessageDialog(LoginPanel.this, "The password you have provided is not correct for the given username!");
				}
			}
		});
		
		loginNewUserButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanels();
			}
		});
	}
	
	//gui elements
	private JLabel createLabel;
	private Seperator createSeperator;
	private EntryField createUsernameField;
	private EntryField createPasswordField;
	private EntryField createEmailField;
	private EntryField createFirstNameField;
	private EntryField createLastNameField;
	private JButton createSubmitButton;
	private JButton createCancelButton;
	
	private void createUserPanelInit(){
		createUserPanel = new JPanel(new GridBagLayout());
		
		createLabel = new JLabel("Smartpill Create New User");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(0, 3, 0, 0);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		createUserPanel.add(createLabel, gbc);
		
		createSeperator = new Seperator(3);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 0, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		createUserPanel.add(createSeperator, gbc);
		
		createUsernameField = new EntryField("Username");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		createUserPanel.add(createUsernameField, gbc);
		
		createPasswordField = new EntryField("Password", true);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		createUserPanel.add(createPasswordField, gbc);
		
		createEmailField = new EntryField("Email");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		createUserPanel.add(createEmailField, gbc);
		
		createFirstNameField = new EntryField("First Name");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		createUserPanel.add(createFirstNameField, gbc);
		
		createLastNameField = new EntryField("Last Name");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.gridwidth = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		createUserPanel.add(createLastNameField, gbc);
		
		createSubmitButton = new JButton("Submit");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 3);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		createUserPanel.add(createSubmitButton, gbc);
		
		createCancelButton = new JButton("Cancel");
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		createUserPanel.add(createCancelButton, gbc);
		
		//spacer panel to push the buttons to the left side of the panel
		JPanel buttonSpacer = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 7;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		createUserPanel.add(buttonSpacer, gbc);
		
		//spacer panel to push the content to the top of the panel
		JPanel spacer = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		createUserPanel.add(spacer, gbc);
		
		createSubmitButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				createNewUser();
			}
		});
		
		createCancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanels();
			}
		});
	}
	
	/**
	 * Checks to see if the information provided is a valid username and password.
	 * @return true if the user has provided the correct password associated with chosen username, false if not.
	 */
	private boolean checkLoginInfo(){
		boolean verified = parent.verifyLogin(loginUsernameField.getText(), loginPasswordField.getText());
		//if verified then switch the view of the parent
		if (verified){
			parent.setState(FrontendGUI.HOME);
		}
		return verified;
	}
	
	/**
	 * Creates a new user with the provided information.
	 * @return true if the new user was successfully created. Action could fail if for instance chosen username is already taken.
	 */
	private boolean createNewUser(){
		//do input validation
		if (createUsernameField.getText() == null || "".equals(createUsernameField.getText())){
			JOptionPane.showMessageDialog(LoginPanel.this, "Please provide a valid username.");
			return false;
		}
		
		if (createPasswordField.getText() == null || createPasswordField.getText().length() < 6){
			JOptionPane.showMessageDialog(LoginPanel.this, "The password you have choosen is too short, it must be 6 characters or longer.");
			return false;
		}
		
		if (createEmailField.getText() == null || "".equals(createEmailField.getText())){
			JOptionPane.showMessageDialog(LoginPanel.this, "You must provide an email address to register.");
			return false;
		}
		//if no @ symbol is found it clearly isn't a valid email address, this is a bare minimum of input validation ideally a confirmation
		//email would be used to verify a email address, but for now this all we will do.
		else if (createEmailField.getText().split("@").length < 2){
			JOptionPane.showMessageDialog(LoginPanel.this, "The email address you have provided is not valid.");
			return false;
		}
		
		if (createFirstNameField.getText() == null || "".equals(createFirstNameField.getText())){
			JOptionPane.showMessageDialog(LoginPanel.this, "You must provide a first name to register.");
			return false;
		}
		
		if (createLastNameField.getText() == null || "".equals(createLastNameField.getText())){
			JOptionPane.showMessageDialog(LoginPanel.this, "You must provide a last name to register.");
			return false;
		}
		
		String username = createUsernameField.getText();
		String password = createPasswordField.getText();
		String email = createEmailField.getText();
		String firstName = createFirstNameField.getText();
		String lastName = createLastNameField.getText();
		boolean verified = parent.verifyNewUser(username, password, email, firstName, lastName);
		//switch view if the new account is given the okay
		if (verified){
			//first let user know that the account creation process was a success
			JOptionPane.showMessageDialog(LoginPanel.this, "Your account has been made successfully!");
			parent.setState(FrontendGUI.HOME);
		}
		return verified;
	}
	
	/**
	 * Switches between the login view and the create user view.
	 */
	private void switchPanels(){
		if (view == LOGIN){
			createUsernameField.setText("");
			createPasswordField.setText("");
			createEmailField.setText("");
			createFirstNameField.setText("");
			createLastNameField.setText("");
			removeAll();
			add(createUserPanel, BorderLayout.CENTER);
			view = CREATE;
		}
		else{
			loginUsernameField.setText("");
			loginPasswordField.setText("");
			removeAll();
			add(loginPanel, BorderLayout.CENTER);
			view = LOGIN;
		}
		revalidate();
		repaint();
	}
	
}
