package doctor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EntryField extends JPanel {
	/**
	 * Default serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The String to be used on the label
	 */
	private String labelText;
	
	/**
	 * Number of blank cells to the right of the text field
	 */
	private int blanks;
	
	/**
	 * Label portion of the entry field
	 */
	private JLabel label;
	
	/**
	 * Text field portion of the entry field
	 */
	private JTextField field;
	
	public EntryField(String name){
		super(new GridBagLayout());
		labelText = name;
		blanks = 1;
		initialize();
	}
	
	public EntryField(String name, int blanks){
		super(new GridBagLayout());
		labelText = name;
		this.blanks = blanks;
		initialize();
	}
	
	private void initialize(){
		label = new JLabel(labelText);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.insets = new Insets(0, 0, 0, 3);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(label, gbc);
		
		field = new JTextField();
		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 3, 0, 3);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(field, gbc);
		
		JPanel space = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = blanks;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(space, gbc);
	}
	
	/**
	 * Gets the text that is currently in the JTextField.
	 * @return The text currently typed into the EntryField.
	 */
	public String getText(){
		return field.getText();
	}
}
