package com.station.taxi.gui;

import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.JPanel;

import com.station.taxi.Cab;
/**
 * Panel contain cabs in waiting state
 * @author alex
 *
 */
public class CabsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<Integer, CabView> mCabViews = new HashMap<Integer, CabView>();

	public CabsPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
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
