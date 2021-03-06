package doctor;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import shared.Alert;
import shared.Patient;

public class HomePage extends JPanel {

	/**
	 * Default serialization
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The parent of this object.
	 */
	private FrontendGUI parent;
	
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
	private EntryField smsEmailField;
	private JButton addButton;
	private JLabel alertsLabel;
	private Seperator alertsSeperator;
	private JScrollPane alertsScroll;
	private JList<Alert> alertsList;
	private JButton alertsRemoveButton;

	public HomePage(FrontendGUI parent){
		super(new GridBagLayout());
		this.parent = parent;
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
				PatientSelectionDialog dialog = new PatientSelectionDialog(parent.getFrame(), true, parent.getPatients(searchField.getText()));
				dialog.setVisible(true);
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
		
		smsEmailField = new EntryField("SMS Email:");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		newPatientSection.add(smsEmailField, gbc);
		
		addButton = new JButton("Add");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.NONE;
		newPatientSection.add(addButton, gbc);
		
		addButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean added = parent.addPatient(firstNameField.getText(), lastNameField.getText(), emailField.getText(), smsEmailField.getText());
				if (!added){
					JOptionPane.showMessageDialog(HomePage.this, "Was unable to add new patient!");
				}
				else{
					JOptionPane.showMessageDialog(HomePage.this, "The patient was added successfully!");
					parent.setState(FrontendGUI.PATIENT);
				}
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
		
		List<Alert> listOfAlerts = parent.getAlerts();
		DefaultListModel<Alert> model = new DefaultListModel<Alert>();
		for (Alert a: listOfAlerts){
			model.addElement(a);
		}
		alertsList = new JList<Alert>(model);
		JPanel alertsPanel = new JPanel(new BorderLayout());
		alertsPanel.add(alertsList, BorderLayout.CENTER);
		alertsScroll = new JScrollPane(alertsPanel);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weighty = 1;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.BOTH;
		alertsSection.add(alertsScroll, gbc);
		
		alertsRemoveButton = new JButton("Remove");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weighty = 0;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		alertsSection.add(alertsRemoveButton, gbc);
		
		alertsRemoveButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if (!alertsList.isSelectionEmpty()){
					boolean marked = parent.markAsRead(alertsList.getSelectedValue());
					//remove it from the JList if it has been marked as read
					if (marked){
						int index = alertsList.getSelectedIndex();
						DefaultListModel<Alert> model = (DefaultListModel<Alert>) alertsList.getModel();
						model.removeElementAt(index);
					}
					revalidate();
					repaint();
				}
			}
		});
	}
	
	
	private class PatientSelectionDialog extends JDialog {
		
		/**
		 * Default Serialization
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * List of patients that will be displayed in the popup
		 */
		private List<Patient> listOfPatients;
		
		//gui elements
		private JList<String> patientsList;
		private JButton selectButton;
		private JButton cancelButton;
		
		private PatientSelectionDialog(JFrame frame, boolean modal, List<Patient> patients){
			super(frame, modal);
			listOfPatients = patients;
			
			JPanel outerPanel = new JPanel(new BorderLayout());
			JPanel innerPanel = new JPanel(new GridBagLayout());
			outerPanel.add(innerPanel, BorderLayout.CENTER);
			
			String names[] = new String[patients.size()];
			for (int i = 0; i < patients.size(); i++){
				names[i] = patients.get(i).name;
			}
			patientsList = new JList<String>(names);
			JScrollPane scroll = new JScrollPane(patientsList);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 2;
			gbc.weightx = 1;
			gbc.weighty = 1;
			gbc.fill = GridBagConstraints.BOTH;
			innerPanel.add(scroll, gbc);
			
			selectButton = new JButton("Select");
			gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.NONE;
			innerPanel.add(selectButton, gbc);
			
			cancelButton = new JButton("Cancel");
			gbc = new GridBagConstraints();
			gbc.gridx = 1;
			gbc.gridy = 1;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.NONE;
			innerPanel.add(cancelButton, gbc);
			
			selectButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (!patientsList.isSelectionEmpty()){
						int selected = patientsList.getSelectedIndex();
						//set the selected patient for the overall system
						parent.setPatient(listOfPatients.get(selected));
						parent.setState(FrontendGUI.PATIENT);
						dispose();
					}
				}
			});
			
			cancelButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			
			getContentPane().add(outerPanel);
			setSize(200, 400);
			setLocationRelativeTo(frame);
		}
	}
	
}
