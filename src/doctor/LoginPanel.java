package doctor;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginPanel extends JPanel {
	//static variables to define the two views
	private static int LOGIN = 0;
	private static int CREATE = 1;
	
	private int view;
	
	//gui elements
	private JPanel loginPanel;
	private JPanel createUserPanel;
	
	public LoginPanel(){
		super(new BorderLayout());
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
		
		loginPasswordField = new EntryField("Password");
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
				checkLoginInfo();
			}
		});
		
		loginNewUserButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				switchPanels();
			}
		});
	}
	
	private boolean checkLoginInfo(){
		//TODO
		System.out.println("username = " + loginUsernameField.getText());
		System.out.println("password = " + loginPasswordField.getText());
		if (loginUsernameField.getText().equals("username") && loginPasswordField.getText().equals("password")){
			return true;
		}
		return false;
	}
	
	private void switchPanels(){
		
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new LoginPanel(), BorderLayout.CENTER);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(400, 400);
	}
	
}
