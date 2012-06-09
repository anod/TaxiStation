package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

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
	private Station mStation;
	private ResourceBundle mResources;
	
	public StationFrame() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mResources = ResourceBundle.getBundle("com.station.taxi.gui.TextsBundle", Locale.getDefault());		
		
		setTitle(mResources.getString("window_title"));
		
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
		mCabsPanel.setBorder(BorderFactory.createTitledBorder(mResources.getString("cabs_panel_title")));
		mainPanel.add(mCabsPanel);
		mPassegerPanel = new PassengersPanel();
		mPassegerPanel.setBorder(BorderFactory.createTitledBorder(mResources.getString("passengers_panel_title")));
		mainPanel.add(mPassegerPanel);
		mDrivingPanel = new DrivingPanel();
		mDrivingPanel.setBorder(BorderFactory.createTitledBorder(mResources.getString("driving_panel_title")));
		mainPanel.add(mDrivingPanel);
		
		mMenuBar = new JMenuBar();
		setJMenuBar(mMenuBar);	
		mItemStation = new JMenuItem("Station");
		mMenuBar.add(mItemStation);
		
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
			addCabToPanel(cab);
		}
	}

	@Override
	public void onCabUpdate(Cab cab) {
		
		// TODO: remove only from one container
		mCabsPanel.removeCab(cab);
		mDrivingPanel.removeCab(cab);
		addCabToPanel(cab);
	}

	@Override
	public void onPassengerUpdate(Passenger p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCabAdd(Cab cab) {
		addCabToPanel(cab);
	}

	@Override
	public void onPassengerAdd(Passenger p) {
		// TODO Auto-generated method stub
		
	}

	private void addCabToPanel(Cab cab) {
		if (cab.isDriving()) {
			mDrivingPanel.addCab(cab);
		} else {
			mCabsPanel.addCab(cab);
		}
	}
}
