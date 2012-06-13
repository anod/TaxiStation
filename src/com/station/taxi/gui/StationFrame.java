package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import com.station.taxi.Cab;
import com.station.taxi.Passenger;
import com.station.taxi.Station;
import com.station.taxi.Station.IStateChangeListener;
/**
 * TaxiStationMain station frame
 * @author alex
 *
 */
public class StationFrame extends JFrame implements IStateChangeListener {
    /**
     * Lock used when maintaining queue of requested updates.
     */
	private static Object sLock = new Object();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CabsPanel mCabsPanel;
	private PassengersPanel mPassegerPanel;
	private DrivingPanel mDrivingPanel;
	private Station mStation;
	
	public StationFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setTitle(TextsBundle.getString("window_title"));
		
    	setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(getFrameDimension());
		getContentPane().setLayout(new BorderLayout());

		setupViews();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	/**
	 * 
	 */
	private void setupViews() {
		JPanel mainPanel = new JPanel();
		getContentPane().add(mainPanel);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		
		mCabsPanel = new CabsPanel();
		mCabsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		mCabsPanel.setBorder(BorderFactory.createTitledBorder(TextsBundle.getString("cabs_panel_title")));
		mainPanel.add(mCabsPanel);
		mPassegerPanel = new PassengersPanel();
		mPassegerPanel.setBorder(BorderFactory.createTitledBorder(TextsBundle.getString("passengers_panel_title")));
		mainPanel.add(mPassegerPanel);
		mDrivingPanel = new DrivingPanel();
		mDrivingPanel.setBorder(BorderFactory.createTitledBorder(TextsBundle.getString("driving_panel_title")));
		mainPanel.add(mDrivingPanel);
		
		setJMenuBar(new StationMenuBar(this));		
	}
	
	private static Dimension getFrameDimension() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return new Dimension((int)(screenSize.width * 0.5), (int)(screenSize.height * 0.5));
	}

	@Override
	public void onStationStart(Station station) {
		mStation = station;
			List<Cab> cabs = station.getCabs();
			for(Cab cab: cabs) {
				placeCabInPanel(cab);
			}
			List<Passenger> pmany = mStation.getPassengers();
			for (Passenger p: pmany) {
				addPassangerToLine(p);
			}
	}

	@Override
	public void onCabUpdate(Cab cab) {
		placeCabInPanel(cab);			
	}

	@Override
	public void onPassengerUpdate(Passenger p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCabAdd(Cab cab) {
		synchronized (sLock) {
			placeCabInPanel(cab);
		}		
	}

	@Override
	public void onPassengerAdd(Passenger p) {
		addPassangerToLine(p);
	}
	

	private void placeCabInPanel(Cab cab) {
		// TODO: remove only from one container
		mCabsPanel.removeCab(cab);
		mDrivingPanel.removeCab(cab);
		if (cab.isDriving()) {
			mDrivingPanel.addCab(cab);
		} else {
			mCabsPanel.addCab(cab);
		}
	}
	private void addPassangerToLine(Passenger p) {
		mPassegerPanel.addPassanger(p);
		
	}
	private void removePassangerFromLine(Passenger p) {
		mPassegerPanel.removePassanger(p);
		
	}
}
