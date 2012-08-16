package com.station.taxi.gui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Waiting cabs panel
 * @author alex
 */
public class WaitingPanel extends CabsPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WaitingPanel(final StationFrame stationFrame) {
		super();
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() == 2 ) {
					stationFrame.showAddCabDialog();
				}
			}			
		});
	}

}
