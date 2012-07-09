package com.station.taxi;

import com.station.taxi.gui.StationFrame;
import com.station.taxi.gui.StationWindowAdapter;
import javax.swing.SwingUtilities;
/**
 * Main entry point
 * @author alex
 * @author Eran Zimbler
 * @version 0.2
 */
public class TaxiStationMain {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				StationFrame window = new StationFrame();
				window.addWindowListener(new StationWindowAdapter(window));
			}
		});

	}

}
