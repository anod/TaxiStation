package com.station.taxi.gui;

import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JPanel;

import com.station.taxi.Cab;

/**
 * Panel will contain cabs in driving state
 * @author alex
 *
 */
public class DrivingPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HashMap<Integer, CabView> mCabViews = new HashMap<Integer, CabView>();
	
	public DrivingPanel() {
		setLayout(new GridLayout(0, 2, 2, 2));
	}	

	public synchronized void addCab(Cab cab) {
		CabView view = new CabView(cab);
		mCabViews.put(cab.getNumber(), view);
		add(view);
		validate();
	}
	
	public synchronized boolean removeCab(Cab cab) {
		int number = cab.getNumber();
		if (mCabViews.containsKey(number)) {
			remove(mCabViews.get(number));
			return true;
		}
		return false;
	}

}
