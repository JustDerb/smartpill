package doctor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.BOTH;
		add(new SchedulerPanel(), gbc);
		
		JPanel spacer = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		add(spacer, gbc);
	}
	
	//gui elements
	private JLabel infoLabel;
	private Seperator infoSeperator;
	private EntryField chooserField;
	private EntryField urlField;
	private EntryField refillField;
	private EntryField dosageField;
	private EntryField messageField;
	
	private JPanel infoPanel(){
		JPanel ret = new JPanel(new GridBagLayout());
		
		infoLabel = new JLabel("Add Prescription");
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(3, 3, 0, 0);
		gbc.fill = GridBagConstraints.NONE;
		ret.add(infoLabel, gbc);
		
		infoSeperator = new Seperator(3);
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 0, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		ret.add(infoSeperator, gbc);
		
		chooserField = new EntryField("Prescription Name");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weightx = 1;
		gbc.weighty = 2;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		ret.add(chooserField, gbc);
		
		urlField = new EntryField("Link to Picture");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		ret.add(urlField, gbc);
		
		refillField = new EntryField("Refill Period (Days)");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		ret.add(refillField, gbc);
		
		dosageField = new EntryField("Dosage");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		ret.add(dosageField, gbc);
		
		messageField = new EntryField("Message");
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.insets = new Insets(3, 3, 3, 0);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		ret.add(messageField, gbc);
		
		return ret;
	}
	
	public class SchedulerPanel extends JPanel {
		
		/**
		 * Default serialization
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * Array used to populate the hour drop down menu.
		 */
		private Integer hoursArr[];
		
		/**
		 * Array used to populate the minute drop down menu.
		 */
		private Integer minutesArr[];
		
		//gui elements
		private JLabel scheduleLabel;
		private Seperator scheduleSeperator;
		private JLabel chooseTimeLabel;
		private List<JComboBox<Integer>> hourChoosers;
		private List<JComboBox<Integer>> minuteChoosers;
		private JButton addTimeButton;
		
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
			
			scheduleLabel = new JLabel("Daily Schedule");
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.NONE;
			add(scheduleLabel, gbc);
			
			scheduleSeperator = new Seperator(3);
			gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.weightx = 1;
			gbc.weighty = 0;
			gbc.insets = new Insets(3, 0, 3, 0);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			add(scheduleSeperator, gbc);
			
			chooseTimeLabel = new JLabel("Choose Time");
			gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.NONE;
			add(chooseTimeLabel, gbc);
			
			JComboBox<Integer> hourChooserOne = new JComboBox<Integer>(hoursArr);
			hourChoosers.add(hourChooserOne);
			gbc = new GridBagConstraints();
			gbc.gridx = 1;
			gbc.gridy = 2;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.NONE;
			add(hourChoosers.get(0), gbc);
			
			JComboBox<Integer> minuteChooserOne = new JComboBox<Integer>(minutesArr);
			minuteChoosers.add(minuteChooserOne);
			gbc = new GridBagConstraints();
			gbc.gridx = 2;
			gbc.gridy = 2;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.NONE;
			add(minuteChoosers.get(0), gbc);
			
			addTimeButton = new JButton("Add Another Time");
			gbc = new GridBagConstraints();
			gbc.gridx = 3;
			gbc.gridy = 2;
			gbc.weightx = 0;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.NONE;
			add(addTimeButton, gbc);
			
			JPanel space = new JPanel();
			gbc = new GridBagConstraints();
			gbc.gridx = 4;
			gbc.gridy = 2;
			gbc.weightx = 1;
			gbc.weighty = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			add(space, gbc);
			
			addTimeButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					JComboBox<Integer> hourBox = new JComboBox<Integer>(hoursArr);
					hourChoosers.add(hourBox);
					JComboBox<Integer> minuteBox = new JComboBox<Integer>(minutesArr);
					minuteChoosers.add(minuteBox);
					
					System.out.println("hour size = " + hourChoosers.size());
					System.out.println("minute size = " + minuteChoosers.size());
					
					GridBagConstraints gbc = new GridBagConstraints();
					gbc.gridx = 1;
					gbc.gridy = hourChoosers.size() - 1;
					gbc.weightx = 0;
					gbc.weighty = 0;
					gbc.fill = GridBagConstraints.NONE;
					add(hourChoosers.get(hourChoosers.size() - 1), gbc);
					
					gbc = new GridBagConstraints();
					gbc.gridx = 2;
					gbc.gridy = minuteChoosers.size() - 1;
					gbc.weightx = 0;
					gbc.weighty = 0;
					gbc.fill = GridBagConstraints.NONE;
					add(minuteChoosers.get(minuteChoosers.size() - 1), gbc);
					
					revalidate();
					repaint();
				}
			});
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