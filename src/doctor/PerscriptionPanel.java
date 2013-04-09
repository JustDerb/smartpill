package doctor;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PerscriptionPanel extends JPanel {
	
	public PerscriptionPanel(){
		super(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(infoPanel(), gbc);
		
		refillField = new EntryField("Refill Period (Days)");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(refillField, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(new SchedulerPanel(), gbc);
		
		JPanel spacer = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(spacer, gbc);
	}
	
	//gui elements
	private JButton chooserButton;
	private JTextField chooserField;
	private EntryField refillField;
	
	private JPanel infoPanel(){
		JPanel ret = new JPanel(new GridBagLayout());
		
		chooserButton = new JButton("Choose Perscription");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(0, 3, 0, 3);
		gbc.fill = GridBagConstraints.NONE;
		ret.add(chooserButton, gbc);
		
		chooserField = new JTextField();
		chooserField.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(0, 3, 0, 3);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		ret.add(chooserField, gbc);
		return ret;
	}
	
	public class SchedulerPanel extends JPanel {
		
		private Integer hoursArr[];
		private Integer minutesArr[];
		
		//gui elements
		private JLabel scheduleLabel;
		private List<JComboBox<Integer>> hourChoosers;
		private List<JComboBox<Integer>> minuteChoosers;
		
		public SchedulerPanel(){
			super(new GridBagLayout());
			hourChoosers = new ArrayList<JComboBox<Integer>>();
			minuteChoosers = new ArrayList<JComboBox<Integer>>();
			
			hoursArr = new Integer[24];
			for (int i = 0; i < 24; i++){
				hoursArr[i] = i;
			}
			
			minutesArr = new Integer[60];
			for (int i = 0; i < 60; i++){
				minutesArr[i] = i;
			}
			
			scheduleLabel = new JLabel("Schedule");
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.NONE;
			add(scheduleLabel, gbc);
			
			JComboBox<Integer> hourChooserOne = new JComboBox<Integer>(hoursArr);
			hourChoosers.add(hourChooserOne);
			gbc = new GridBagConstraints();
			gbc.gridx = 1;
			gbc.gridy = 0;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.NONE;
			add(hourChoosers.get(0), gbc);
			
			JComboBox<Integer> minuteChooserOne = new JComboBox<Integer>(minutesArr);
			minuteChoosers.add(minuteChooserOne);
			gbc.gridx = 2;
			gbc.gridy = 0;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.NONE;
			add(minuteChoosers.get(0), gbc);
		}
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new PerscriptionPanel(), BorderLayout.CENTER);
		frame.add(panel);
		frame.setVisible(true);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
