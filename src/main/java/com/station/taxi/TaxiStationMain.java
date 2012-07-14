package com.station.taxi;

import com.station.taxi.gui.StationFrame;
import com.station.taxi.gui.StationWindowAdapter;
import com.station.taxi.spring.StationContext;
import javax.swing.SwingUtilities;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Main entry point
 *
 * @author alex
 * @author Eran Zimbler
 * @version 0.2
 */
public class TaxiStationMain {

	private static final String CONFIG_PATH = "spring/StationXMLConfig.xml";

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONFIG_PATH, TaxiStationMain.class);
		final StationContext context = new StationContext(applicationContext);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				StationFrame window = new StationFrame(context);
				window.addWindowListener(new StationWindowAdapter(window));
			}
		});
	}
}
