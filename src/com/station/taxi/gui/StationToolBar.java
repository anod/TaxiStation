package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
/**
 * Represents toolbar of station
 * @author alex
 * @author Eran Zimbler
 * @version 0.2
 *
 */
public class StationToolBar extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ADD_CAB = "add_cab";
	private static final String ADD_PASSENGER = "add_passenger";
	private static final String SAVE_EXIT = "save_exit";
	
	private StationFrame mStationFrame;
	
	public StationToolBar(StationFrame stationFrame) {
		super(new BorderLayout());
		mStationFrame = stationFrame;
		
		JToolBar toolbar = new JToolBar("Main");
		addButtons(toolbar);
		toolbar.setFloatable(true);
		toolbar.setRollover(true);
		
        setPreferredSize(new Dimension(450, 30));
        add(toolbar, BorderLayout.PAGE_START);		
	}
	/**
	 * Add buttons to toolbar
	 * @param toolbar
	 */
	protected void addButtons(JToolBar toolbar) {
		JButton button = null;
		// first button
		button = makeNavigationButton(null, ADD_CAB, TextsBundle.getString("toolbar_add_cab"));
		toolbar.add(button);

		// second button
		button = makeNavigationButton(null, ADD_PASSENGER, TextsBundle.getString("toolbar_add_passenger"));

		toolbar.add(button);
		
		button = makeNavigationButton(null, SAVE_EXIT, TextsBundle.getString("toolbar_exit"));
		
		toolbar.add(button);

	}

	/**
	 * Create one button on the toolbar
	 * @param imageName
	 * @param actionCommand
	 * @param altText
	 * @return
	 */
	protected JButton makeNavigationButton(String imageName,
			String actionCommand, String altText) {

		// Create and initialize the button.
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(altText);
		button.addActionListener(this);

		if (imageName != null) { // image found
			button.setIcon(ImageUtils.createImageIcon(imageName));
		} else { // no image found
			button.setText(altText);
		}

		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ADD_CAB)) {
			mStationFrame.showAddCabDialog();
		} else if (e.getActionCommand().equals(ADD_PASSENGER)) {
			mStationFrame.showAddPassengerDialog();
		} else if (e.getActionCommand().equals(SAVE_EXIT)) {
			mStationFrame.closeWindow();
		}

	}
	
}
