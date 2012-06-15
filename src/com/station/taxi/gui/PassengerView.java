package com.station.taxi.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.station.taxi.Passenger;
import javax.swing.JLabel;

public class PassengerView extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private Passenger mPassenger;
	public PassengerView(Passenger p) {
		setBorder(new TitledBorder(null, p.getPassangerName(), TitledBorder.CENTER, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		
		
		
		mPassenger = p;
		setupViews();
		
	}
	private void setupViews() {
		
		JLabel lblDestination = new JLabel(mPassenger.getDestination());
		add(lblDestination, BorderLayout.CENTER);
		JLabel lblTimeLeft = new JLabel(mPassenger.getTimeLeft()+"");
		add(lblTimeLeft, BorderLayout.CENTER);
		
	}

}
