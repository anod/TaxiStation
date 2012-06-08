package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import com.station.taxi.Cab;

public class CabLabel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Cab mCab;
	private JTable mPassangertable;

	public CabLabel(Cab cab) {
		setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setLayout(new BorderLayout(0, 0));
		
		JLabel lblTitle = new JLabel("Number: "+cab.getNumber());
		add(lblTitle, BorderLayout.NORTH);
		
		mPassangertable = new JTable();
		mPassangertable.setBackground(SystemColor.control);
		mPassangertable.setBorder(null);
		mPassangertable.setShowVerticalLines(false);
		mPassangertable.setShowHorizontalLines(false);
		mPassangertable.setShowGrid(false);
		mPassangertable.setRowSelectionAllowed(false);
		mPassangertable.setEnabled(false);
		mPassangertable.setModel(new DefaultTableModel(
			new Object[][] {
				{"Passanger 1", "Passanger 2"},
				{"Passanger 3", "Passanger 4"},
			},
			new String[] {
				"New column", "New column"
			}
		));
		add(mPassangertable, BorderLayout.WEST);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnArrived = new JButton("Arrived");
		panel.add(btnArrived);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(ImageUtils.createImageIcon("cab.png"));
		add(lblNewLabel, BorderLayout.CENTER);
		mCab = cab;
	}
}
