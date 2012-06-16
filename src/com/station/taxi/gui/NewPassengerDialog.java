package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.station.taxi.Passenger;

public class NewPassengerDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextArea mNameField = new JTextArea(1, 30);
	private JTextArea mDestField = new JTextArea(1, 30);
	private StationFrame mOwner;
	public NewPassengerDialog(StationFrame owner) {
		new JDialog(owner, TextsBundle.getString("dialog_title_addpassenger"));
		JPanel content = new JPanel();
		mOwner = owner;
		JLabel lblName = new JLabel("Passenger Name:");
		JLabel lblDestination = new JLabel("Passenger Destination:");
		
		
		JPanel btnPanel = new JPanel();
		JButton okBtn = new JButton(TextsBundle.getString("dialog_add"));
		JButton noBtn = new JButton(TextsBundle.getString("dialog_cancel"));

		btnPanel.add(okBtn);
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent ae) {
				okButton();
			}

		});
		noBtn.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent ae) {
				noButton();
			}

		});
		btnPanel.add(noBtn);
		content.setLayout(new GridLayout(0, 2, 0, 0));
		
		content.add(lblName);
		content.add(mNameField);
		content.add(lblDestination);
		content.add(mDestField);
		getContentPane().add(content,BorderLayout.CENTER);
		getContentPane().add(btnPanel, BorderLayout.SOUTH);
		pack();
	}

	private void okButton() {
		setVisible(false);
		Passenger p = new Passenger(mNameField.getText(), mDestField.getText());
		mOwner.getStation().addPassenger(p);
	}

	private void noButton() {
		setVisible(false);
		
	}
}
