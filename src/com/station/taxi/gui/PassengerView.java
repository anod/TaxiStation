package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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
		add(new JLabel("Destination:"),BorderLayout.CENTER);
		add(lblDestination, BorderLayout.CENTER);
		int timeLeft = mPassenger.getTimeLeft();
		
		JLabel lblTimeLeft = new JLabel(timeLeft+"");
		add(lblTimeLeft, BorderLayout.CENTER);
		JLayeredPane mIconLayerdPane = new JLayeredPane();
		mIconLayerdPane.setPreferredSize(new Dimension(72, 72));
		add(mIconLayerdPane, BorderLayout.WEST);
		
		JLabel mIcon = new JLabel("");
		mIcon.setSize(new Dimension(72, 72));
		mIcon.setBounds(0, 0, 72, 76);
		mIconLayerdPane.add(mIcon);
		mIcon.setVerticalAlignment(SwingConstants.TOP);
		if(timeLeft > 5)
			mIcon.setIcon(ImageUtils.createImageIcon("passenger"));
		else
			mIcon.setIcon(ImageUtils.createImageIcon("angry"));
		
	}

}
