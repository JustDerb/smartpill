package doctor;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import shared.Patient;

public class PatientPanel extends JPanel{
	
	/**
	 * Default Serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The parent of this object.
	 */
	private FrontendGUI parent;

	//gui elements
	private JTabbedPane tabPane;
	private JScrollPane scrollPane;
	private JPanel headerPanel;
	private JLabel headerNameLabel;
	private JLabel headerEmailLabel;
	private PrescriptionPanel presPanel;
	
	public PatientPanel(FrontendGUI parent){
		super(new GridBagLayout());
		this.parent = parent;
		headerPanelInit();
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(headerPanel, gbc);
		
		tabPane = new JTabbedPane();
		presPanel = new PrescriptionPanel();
		tabPane.addTab("PrescriptionPanel", presPanel);
		scrollPane = new JScrollPane(tabPane);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(scrollPane, gbc);
	}
	
	private void headerPanelInit(){
		headerPanel = new JPanel(new BorderLayout());
		JPanel gridBagPanel = new JPanel(new GridBagLayout());
		headerPanel.add(gridBagPanel, BorderLayout.CENTER);
		
		headerNameLabel = new JLabel();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gridBagPanel.add(headerNameLabel, gbc);
		
		headerEmailLabel = new JLabel();
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.NONE;
		gridBagPanel.add(headerEmailLabel, gbc);
	}
	
	public void setPatientInfo(Patient patient){
		String name = "";
		String email = "";
		if (patient != null){
			name = patient.name;
			email = patient.email;
		}
		headerEmailLabel.setText("Email: " + email);
		headerNameLabel.setText("Name: " + name);
		revalidate();
		repaint();
	}
	
	public class SchedulePanel extends JPanel{

		/**
		 * Default Serialization
		 */
		private static final long serialVersionUID = 1L;
		
		//gui parts
		private JScrollPane scheduleScrollPane;
		private JList<String> scheduleList;
		private FrontendGUI parent;
		
		public SchedulePanel(FrontendGUI parent){
			this.parent = parent;
		}
		
		public void setPatientInfo(){
			
		}
	}
}
