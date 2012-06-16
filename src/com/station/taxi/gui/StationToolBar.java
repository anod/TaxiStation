package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class StationToolBar extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String ADD_CAB = "add_cab";
	private static final String ADD_PASSENGER = "add_passenger";
	private static final String SAVE_EXIT = "save_exit";
	
	public StationToolBar() {
		super(new BorderLayout());
		
		JToolBar toolbar = new JToolBar("Main");
		addButtons(toolbar);
		toolbar.setFloatable(true);
		toolbar.setRollover(true);
		
        setPreferredSize(new Dimension(450, 25));
        add(toolbar, BorderLayout.PAGE_START);		
	}

	protected void addButtons(JToolBar toolbar) {
		JButton button = null;
		// first button
		button = makeNavigationButton(null, ADD_CAB,
				"Back to previous something-or-other", "Add cab");
		toolbar.add(button);

		// second button
		button = makeNavigationButton(null, ADD_PASSENGER,
				"Up to something-or-other", "Add passenger");

		toolbar.add(button);
		
		button = makeNavigationButton(null, SAVE_EXIT,
				"Up to something-or-other", "Save & Exit");
		
		toolbar.add(button);

	}

	protected JButton makeNavigationButton(String imageName,
			String actionCommand, String toolTipText, String altText) {

		// Create and initialize the button.
		JButton button = new JButton();
		button.setActionCommand(actionCommand);
		button.setToolTipText(toolTipText);
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
		// TODO Auto-generated method stub

	}

}
