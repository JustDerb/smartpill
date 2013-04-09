package doctor;

import java.awt.Graphics;

import javax.swing.JComponent;

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