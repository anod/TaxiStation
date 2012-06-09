package com.station.taxi.gui;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import com.station.taxi.Cab;
/**
 * Panel contain cabs in waiting state
 * @author alex
 *
 */
public class CabsPanel extends JPanel {
	public CabsPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

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
		add(new CabView(cab));
		validate();
	}
	
}
