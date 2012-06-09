package com.station.taxi.gui;

import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.JPanel;

import com.station.taxi.Cab;
import com.station.taxi.gui.CabView.AnimationCallback;
import java.awt.GridLayout;
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
			final CabView view = mCabViews.get(number);
			remove(view);
			return true;
		}
		return false;
	}
	
}
