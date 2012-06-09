package com.station.taxi.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;
/**
 * Panel will contain passengers in waiting state
 * @author alex
 *
 */
public class PassengersPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PassengersPanel() {
		setLayout(new GridLayout(0, 4, 2, 2));
	}
}
