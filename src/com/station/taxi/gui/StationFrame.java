package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.station.taxi.Cab;
import com.station.taxi.Passenger;
import com.station.taxi.Station;
import com.station.taxi.Station.IStateChangeListener;

public class StationFrame extends JFrame implements IStateChangeListener {

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
		// TODO: add MenuBar
		// TODO: listen to station events: change status of tax and passenger
		
		setLocationRelativeTo(null);
		setVisible(true);

	}
	
	private static Dimension getFrameDimension() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return new Dimension((int)(screenSize.width * 0.5), (int)(screenSize.height * 0.5));
	}

	@Override
	public void onStationStart(Station station) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCabStatusChange(Cab cab, int oldStatus, int newStatus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPassengerStatusChange(Passenger p, int oldStatus,
			int newStatus) {
		// TODO Auto-generated method stub
		
	}

}
