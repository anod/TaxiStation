package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.station.taxi.Cab;
import com.station.taxi.Passenger;
import com.station.taxi.Station;
import com.station.taxi.Station.IStateChangeListener;
/**
 * Main station frame
 * @author alex
 *
 */
public class StationFrame extends JFrame implements IStateChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CabsPanel mCabsPanel;
	private PassengersPanel mPassegerPanel;
	private DrivingPanel mDrivingPanel;
	private JMenuBar mMenuBar;
	private JMenuItem mItemStation;
	
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
		// TODO: add MenuBar
		// TODO: listen to station events: change status of tax and passenger

		setupViews();
		setLocationRelativeTo(null);
		
		mMenuBar = new JMenuBar();
		setJMenuBar(mMenuBar);
		
		mItemStation = new JMenuItem("Station");
		mMenuBar.add(mItemStation);
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
		mCabsPanel.setBorder(BorderFactory.createTitledBorder("Cabs Panel"));
		mainPanel.add(mCabsPanel);
		mPassegerPanel = new PassengersPanel();
		mPassegerPanel.setBorder(BorderFactory.createTitledBorder("Passenger Panel"));
		mainPanel.add(mPassegerPanel);
		mDrivingPanel = new DrivingPanel();
		mDrivingPanel.setBorder(BorderFactory.createTitledBorder("Driving Panel"));
		mainPanel.add(mDrivingPanel);
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
	public void onPassengerStatusChange(Passenger p, int oldStatus,	int newStatus) {
		// TODO Auto-generated method stub
		
	}

}
