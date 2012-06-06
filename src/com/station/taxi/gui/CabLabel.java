package com.station.taxi.gui;

import javax.swing.JLabel;

import com.station.taxi.Cab;

public class CabLabel extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Cab mCab;

	public CabLabel(Cab cab) {
		mCab = cab;
		setText(String.valueOf(mCab.getNumber()));
	}


}
