package com.station.taxi.gui;

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
public class GuiMain {

	private static final String CONFIG_PATH = "GuiXMLConfig.xml";

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONFIG_PATH, GuiMain.class);
		final GuiStationContext context = new GuiStationContext(applicationContext);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				StationFrame window = new StationFrame(context);
				window.addWindowListener(new StationWindowAdapter(window));
			}
		});
	}
}
