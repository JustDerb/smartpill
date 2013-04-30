package doctor;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import shared.Alert;
import shared.Patient;
import shared.Prescription;

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
	private SchedulePanel schedulePanel;
	
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
		presPanel = new PrescriptionPanel(parent);
		schedulePanel = new SchedulePanel(parent);
		tabPane.addTab("PrescriptionPanel", presPanel);
		tabPane.addTab("SchedulePanel", schedulePanel);
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
		schedulePanel.setPatientInfo();
		revalidate();
		repaint();
	}
	
	public void updateSchedule(){
		schedulePanel.setPatientInfo();
	}
	
	public class SchedulePanel extends JPanel{

		/**
		 * Default Serialization
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * The parent of this object.
		 */
		private FrontendGUI parent;
		
		//gui parts
		private JScrollPane scheduleScrollPane;
		private JPanel listPanel;
		private JList<Prescription> scheduleList;
		private JButton removePrescription;
		
		public SchedulePanel(FrontendGUI parent){
			super(new BorderLayout());
			this.parent = parent;
			
			scheduleList = new JList<Prescription>();
			listPanel = new JPanel(new BorderLayout());
			listPanel.add(scheduleList, BorderLayout.CENTER);
			scheduleScrollPane = new JScrollPane(listPanel);
			add(scheduleScrollPane, BorderLayout.CENTER);
			
			removePrescription = new JButton("Remove Prescription");
			add(removePrescription, BorderLayout.SOUTH);
			
			removePrescription.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!scheduleList.isSelectionEmpty()){
						boolean marked = PatientPanel.this.parent.removePrescription(scheduleList.getSelectedValue());
						//remove it from the JList if it has been marked as read
						if (marked){
							int index = scheduleList.getSelectedIndex();
							DefaultListModel<Prescription> model = (DefaultListModel<Prescription>) scheduleList.getModel();
							model.removeElementAt(index);
						}
						setPatientInfo();
					}
				}
			});
		}
		
		public void setPatientInfo(){
			List<Prescription> list = parent.getPrescriptions();

			listPanel.removeAll();
			DefaultListModel<Prescription> model = new DefaultListModel<Prescription>();
			for (Prescription p: list){
				model.addElement(p);
			}
			scheduleList = new JList<Prescription>(model);
			
			listPanel.add(scheduleList, BorderLayout.CENTER);
			System.out.println("Called SchedulePanel.setPatientInfo " + list.size());
			revalidate();
			repaint();
		}
	}
}
