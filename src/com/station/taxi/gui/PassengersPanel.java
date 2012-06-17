package com.station.taxi.gui;

import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JPanel;

import com.station.taxi.Passenger;
/**
 * Panel will contain passengers in waiting state
 * @author alex
 * @author Eran Zimbler
 * @version 0.2
 */
public class PassengersPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private HashMap<String, PassengerView> mPassViews = new HashMap<String, PassengerView>();
	public PassengersPanel() {
		setLayout(new GridLayout(0, 1, 2, 2));
	}
	public void addPassanger(Passenger p) {
		String passengerName = p.getPassangerName();
		if (!mPassViews.containsKey(passengerName))
		{
			PassengerView curr = new PassengerView(p);
			mPassViews.put(passengerName, curr);
			add(curr);
			//Sometimes view stays on the screen
			repaint();
		}
	}
	public boolean removePassanger(Passenger p) {
		if (mPassViews.containsKey(p.getPassangerName())) {
			final PassengerView view = mPassViews.get(p.getPassangerName());
			remove(view);
			mPassViews.remove(p.getPassangerName());
			repaint(); //Sometimes view stays on the screen

			return true;
		}
		return false;
		
	}
	public void updatePassenger(Passenger p)
	{
		String passengerName = p.getPassangerName();
		if(mPassViews.containsKey(passengerName))
		{
			PassengerView curr = mPassViews.get(passengerName);
			curr.updateSelf();
		}
	}
}
