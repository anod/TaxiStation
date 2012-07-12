package com.station.taxi;

import com.station.taxi.gui.StationFrame;
import com.station.taxi.gui.StationWindowAdapter;
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

	private static final String CONFIG_PATH = "com/station/taxi/spring/StationXMLConfig.xml";

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext(CONFIG_PATH);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				StationFrame window = new StationFrame();
				window.addWindowListener(new StationWindowAdapter(window));
			}
		});

		//clean the context
		((ClassPathXmlApplicationContext) context).close();
	}
}
