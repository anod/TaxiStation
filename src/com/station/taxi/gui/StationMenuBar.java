package com.station.taxi.gui;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
/**
 * Menu bar of the station frame
 * @author alex
 *
 */
public class StationMenuBar extends JMenuBar implements ActionListener {

	private static final String ACTION_EXIT = "act_exit";
	private static final String ACTION_ADD_CAB = "act_add_cab";
	private static final String ACTION_ADD_PASSENGER = "act_add_passenger";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StationFrame mStationFrame;

	public StationMenuBar(StationFrame stationFrame) {
		mStationFrame = stationFrame;

		JMenu menuStation = new JMenu(TextsBundle.getString("menu_station"));
		add(menuStation);

		JMenuItem miAddCab = new JMenuItem(
				TextsBundle.getString("menu_item_addcab"));
		miAddCab.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				InputEvent.ALT_MASK));
		miAddCab.addActionListener(this);
		miAddCab.setActionCommand(ACTION_ADD_CAB);
		menuStation.add(miAddCab);

		JMenuItem miAddPassenger = new JMenuItem(
				TextsBundle.getString("menu_item_addpassenger"));
		miAddPassenger.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				InputEvent.ALT_MASK));
		miAddPassenger.addActionListener(this);
		miAddPassenger.setActionCommand(ACTION_ADD_PASSENGER);
		menuStation.add(miAddPassenger);

		menuStation.addSeparator();

		JMenuItem miExit = new JMenuItem(TextsBundle.getString("exit"));
		miExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				InputEvent.ALT_MASK));
		miExit.addActionListener(this);
		miExit.setActionCommand(ACTION_EXIT);
		menuStation.add(miExit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_EXIT)) {
			WindowEvent wev = new WindowEvent(mStationFrame,
			WindowEvent.WINDOW_CLOSING);
			Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
		} else if (e.getActionCommand().endsWith(ACTION_ADD_CAB)) {
			NewCabDialog dialog = new NewCabDialog(mStationFrame);
			dialog.setVisible(true);
		} else if (e.getActionCommand().endsWith(ACTION_ADD_PASSENGER)) {
			NewPassengerDialog d= new NewPassengerDialog(mStationFrame);
			d.setVisible(true);
		}

	}

}
