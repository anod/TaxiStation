package com.station.taxi.gui;

import java.awt.FlowLayout;

import javax.swing.JPanel;
import java.awt.GridLayout;
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
