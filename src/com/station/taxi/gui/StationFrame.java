package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.station.taxi.Cab;
import com.station.taxi.Passenger;
import com.station.taxi.Station.IStateChangeListener;

public class StationFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private FrameStationEventAdapter mStationEventListener = null;
	
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
		
		mStationEventListener = new FrameStationEventAdapter();
	}
	
	public IStateChangeListener getStationEventListener() {
		return mStationEventListener;
	}
	
	private static Dimension getFrameDimension() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return new Dimension((int)(screenSize.width * 0.5), (int)(screenSize.height * 0.5));
	}
	
	
	private class FrameStationEventAdapter implements IStateChangeListener {

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
	
}
