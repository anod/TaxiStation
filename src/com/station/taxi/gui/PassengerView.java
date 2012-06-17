package com.station.taxi.gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import com.station.taxi.Passenger;
/**
 * The graphical represantation for a single 
 * passenger
 * @author Eran Zimbler
 * @version 0.2
 */
public class PassengerView extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private Passenger mPassenger;
	private int mTimeLeft=0;
	private JLabel mIcon = new JLabel("");
	private JLabel lblTimeLeft = new JLabel("");
	public PassengerView(Passenger p) {
		setBorder(new TitledBorder(null, p.getPassangerName(), TitledBorder.CENTER, TitledBorder.TOP, null, null));
		setLayout(new GridLayout(0,2,2,1));
		mPassenger = p;
		
		setupViews();
		updateSelf();
	}
	public void updateSelf()	{
		mTimeLeft= mPassenger.getTimeLeft();
		lblTimeLeft.setText("Time left:" + mTimeLeft +"");
		if(mTimeLeft > 5)
			mIcon.setIcon(ImageUtils.createImageIcon("passenger"));
		else
			mIcon.setIcon(ImageUtils.createImageIcon("angry"));
		repaint();
	}
	private void setupViews() {
		
		JLabel lblDestination = new JLabel(mPassenger.getDestination());
		add(new JLabel("Destination:"));
		add(lblDestination);
		mTimeLeft = mPassenger.getTimeLeft();
		add(lblTimeLeft);
		JLayeredPane mIconLayerdPane = new JLayeredPane();
		mIconLayerdPane.setPreferredSize(new Dimension(72, 72));
		add(mIconLayerdPane);
		

		mIcon.setSize(new Dimension(72, 72));
		mIcon.setBounds(0, 0, 72, 76);
		mIconLayerdPane.add(mIcon);
		mIcon.setVerticalAlignment(SwingConstants.TOP);

		
	}

}
