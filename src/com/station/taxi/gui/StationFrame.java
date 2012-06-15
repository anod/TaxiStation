package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
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
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WaitingPanel mWaitingPanel;
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
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		mWaitingPanel = new WaitingPanel();
		GridLayout gridLayout = (GridLayout) mWaitingPanel.getLayout();
		gridLayout.setColumns(5);
		mWaitingPanel.setBorder(BorderFactory.createTitledBorder(TextsBundle.getString("cabs_panel_title")));
		mainPanel.add(mWaitingPanel);
		mPassegerPanel = new PassengersPanel();
		GridLayout gridLayout_2 = (GridLayout) mPassegerPanel.getLayout();
		gridLayout_2.setColumns(5);
		mPassegerPanel.setBorder(BorderFactory.createTitledBorder(TextsBundle.getString("passengers_panel_title")));
		mainPanel.add(mPassegerPanel);
		mDrivingPanel = new DrivingPanel();
		GridLayout gridLayout_1 = (GridLayout) mDrivingPanel.getLayout();
		gridLayout_1.setColumns(5);
		mDrivingPanel.setBorder(BorderFactory.createTitledBorder(TextsBundle.getString("driving_panel_title")));
		mainPanel.add(mDrivingPanel);
		
		//setJMenuBar(new StationMenuBar(this));		
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
		placeCabInPanel(cab);
	}

	@Override
	public void onPassengerAdd(Passenger p) {
		addPassangerToLine(p);
	}
	

	private void placeCabInPanel(Cab cab) {
		mWaitingPanel.removeCab(cab);
		mDrivingPanel.removeCab(cab);
		if (cab.isDriving()) {
			mDrivingPanel.addCab(cab);
		} else {
			mWaitingPanel.addCab(cab);
		}
		sleep(300);
	}
	private void addPassangerToLine(Passenger p) {
		mPassegerPanel.addPassanger(p);
		
	}
	private void removePassangerFromLine(Passenger p) {
		mPassegerPanel.removePassanger(p);
		
	}
	
	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
