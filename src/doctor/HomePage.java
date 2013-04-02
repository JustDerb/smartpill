package doctor;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePage extends JPanel {

	/**
	 * Default serialization
	 */
	private static final long serialVersionUID = 1L;
	
	//different sections of the home page
	private JPanel existingPatientSection;
	private JPanel newPatientSection;
	private JPanel alertsSection;
	//Objects used for constructing the GUI
	private JLabel existingPatientLabel;
	private Seperator existingSeperator;
	private EntryField searchField;
	private JButton searchButton;
	private JLabel newPatientLabel;
	private Seperator newPatientSeperator;
	private EntryField firstNameField;
	private EntryField lastNameField;
	private EntryField emailField;
	private JButton addButton;
	private JLabel alertsLabel;
	private Seperator alertsSeperator;

	public HomePage(){
		super(new GridBagLayout());
		existingPatientSectionInit();
		newPatientSectionInit();
		alertsSectionInit();
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(existingPatientSection, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(newPatientSection, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(alertsSection, gbc);
	}
	
	public class Seperator extends JComponent {

		/**
		 * Default serialization
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Thickness of the seperator in pixels
		 */
		private int thickness;
		
		public Seperator(int thickness){
			this.thickness = thickness;
			setSize(getWidth(), thickness);
		}
		
		public void paint(Graphics g){
			int width = getWidth();
			setSize(width, thickness);
			g.fillRect(0, 0, width, thickness);
		}
	}
	
	private void existingPatientSectionInit(){
		existingPatientSection = new JPanel(new GridBagLayout());
		
		existingPatientLabel = new JLabel("Existing Patient");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(3, 3, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		existingPatientSection.add(existingPatientLabel, gbc);
		
		existingSeperator = new Seperator(3);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 0, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		existingPatientSection.add(existingSeperator, gbc);
		
		searchField = new EntryField("Search:");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		existingPatientSection.add(searchField, gbc);
		
		searchButton = new JButton("Search");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.NONE;
		existingPatientSection.add(searchButton, gbc);
		
		searchButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("looking for " + searchField.getText());
			}
		});
	}
	
	private void newPatientSectionInit(){
		newPatientSection = new JPanel(new GridBagLayout());
		
		newPatientLabel = new JLabel("Add New Patient");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(20, 3, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		newPatientSection.add(newPatientLabel, gbc);
		
		newPatientSeperator = new Seperator(3);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 0, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		newPatientSection.add(newPatientSeperator, gbc);
		
		firstNameField = new EntryField("First Name:");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		newPatientSection.add(firstNameField, gbc);
		
		lastNameField = new EntryField("Last Name:");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		newPatientSection.add(lastNameField, gbc);
		
		emailField = new EntryField("Email:");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		newPatientSection.add(emailField, gbc);
		
		addButton = new JButton("Add");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.NONE;
		newPatientSection.add(addButton, gbc);
		
		addButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TODO create database entry for the newly added patient
				System.out.println("adding " + firstNameField.getText() + " " + lastNameField.getText() + " " + emailField.getText());
			}
		});
	}
	
	private void alertsSectionInit(){
		alertsSection = new JPanel(new GridBagLayout());
		
		alertsLabel = new JLabel("Alerts");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(20, 3, 0, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		alertsSection.add(alertsLabel, gbc);
		
		alertsSeperator = new Seperator(3);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 0, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		alertsSection.add(alertsSeperator, gbc);
		
		//TODO get rid of spacer and replace with alerts panel
		JPanel spacer = new JPanel();
//		spacer.setBorder(BorderFactory.createLineBorder(Color.GREEN));
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		alertsSection.add(spacer, gbc);
	}
	
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new HomePage(), BorderLayout.CENTER);
		frame.add(panel);
		frame.setSize(400, 400);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
