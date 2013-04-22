package doctor;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrontendGUI {
	
	/**
	 * Indicates that the login panel should be displayed.
	 */
	public static int LOGIN = 0;
	/**
	 * Indicates that the home page panel should be displayed.
	 */
	public static int HOME = 1;
	
	/**
	 * The current panel being displayed
	 */
	private int state;
	
	//gui parts
	private JPanel panel;
	private LoginPanel loginPanel;
	private HomePage homePanel;
	
	public FrontendGUI(){
		loginPanel = new LoginPanel(this);
		homePanel = new HomePage();
		
		JFrame frame = new JFrame();
		panel = new JPanel(new BorderLayout());
		panel.add(loginPanel);
		state = LOGIN;
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setVisible(true);
		
	}
	
	/**
	 * Verify a username and password as being valid or not.
	 * @param username is the username that was chosen by the user.
	 * @param password is the password that the user associated with username.
	 * @return true if the username and password are valid false if not.
	 */
	public boolean verifyLogin(String username, String password){
		//TODO
		if ("username".equals(username) && "password".equals(password)){
			return true;
		}
		return false;
	}
	
	public boolean verifyNewUser(String username, String password, String email, String firstName, String lastName){
		//TODO
		return true;
	}
	
	/**
	 * Switches the panel that is currently in view for the user.
	 * @param view what view the gui should be showing to the user.
	 */
	public void setState(int view){
		panel.removeAll();
		if (view == LOGIN){
			panel.add(loginPanel, BorderLayout.CENTER);
			state = LOGIN;
		}
		else if (view == HOME){
			panel.add(homePanel, BorderLayout.CENTER);
			state = HOME;
		}
		panel.revalidate();
		panel.repaint();
	}
	
	/**
	 * Gets the current state that the gui is in.
	 * @return The static int the represents the view that is currently being displayed.
	 */
	public int getState(){
		return state;
	}
}
