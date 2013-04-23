package doctor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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
	private JPanel headerPanel;
	private JLabel headerNameLabel;
	private JLabel headerEmailLabel;
	private PrescriptionPanel presPanel;
	
	public PatientPanel(FrontendGUI parent){
		super(new GridBagLayout());
		headerPanelInit();
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(headerPanel, gbc);
		
		tabPane = new JTabbedPane();
		presPanel = new PrescriptionPanel();
		tabPane.addTab("PrescriptionPanel", presPanel);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(tabPane, gbc);
	}
	
	private void headerPanelInit(){
		headerPanel = new JPanel(new GridBagLayout());
		
		headerNameLabel = new JLabel("Name here");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		headerPanel.add(headerNameLabel, gbc);
		
		headerEmailLabel = new JLabel("Email here");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.NONE;
		headerPanel.add(headerEmailLabel, gbc);
	}
}
