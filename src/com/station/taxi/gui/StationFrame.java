package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class StationFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StationFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setTitle("Taxi Station");
		
		setSize(getFrameDimension());
		getContentPane().setLayout(new BorderLayout());
		
		// TODO: add Taxi panel
		// TODO: add Passenger panel
		// TODO: add Driving Panel
		// TODO: listen to station events: change status of tax and passenger
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private static Dimension getFrameDimension() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return new Dimension((int)(screenSize.width * 0.5), (int)(screenSize.height * 0.5));
	}
}
