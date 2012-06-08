package com.station.taxi.gui;

import javax.swing.JPanel;

import com.station.taxi.Cab;
import java.awt.FlowLayout;
/**
 * Panel contain cabs in waiting state
 * @author alex
 *
 */
public class CabsPanel extends JPanel {
	public CabsPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		add(new CabLabel(new Cab(123, "test")));
		add(new CabLabel(new Cab(123, "test")));
		add(new CabLabel(new Cab(123, "test")));
		add(new CabLabel(new Cab(123, "test")));
		add(new CabLabel(new Cab(123, "test")));
		//removeAll();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Add cab to panel
	 * @param cab
	 */
	public synchronized void addCab(Cab cab) {
		add(new CabLabel(cab));
	}
	
}
