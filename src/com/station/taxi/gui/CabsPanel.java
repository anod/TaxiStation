package com.station.taxi.gui;

import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JPanel;

import com.station.taxi.Cab;
/**
 * Panel contain cabs in waiting state
 * @author alex
 *
 */
public abstract class CabsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<Integer, CabView> mCabViews = new HashMap<Integer, CabView>();
	
	public CabsPanel() {
		setLayout(new GridLayout(0, 2, 2, 2));
	}	

	public void addOrUpdateCab(Cab cab) {
		int num = cab.getNumber();
		if (!mCabViews.containsKey(num)) {
			CabView view = new CabView(cab);
			mCabViews.put(cab.getNumber(), view);
			add(view);
			validate();
		} else {
			mCabViews.get(num).refresh();
		}
	}
	
	
	public boolean removeCab(Cab cab) {
		int number = cab.getNumber();
		if (mCabViews.containsKey(number)) {
			remove(mCabViews.get(number));
			mCabViews.remove(number);
			return true;
		}
		return false;
	}
	
}
