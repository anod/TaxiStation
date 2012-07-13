package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.xml.soap.Text;

import com.station.taxi.Passenger;
import com.station.taxi.Station;
/**
 * The new Passenger dialog panel
 * @author Eran Zimbler
 * @version 0.2
 */
public class NewPassengerDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextArea mNameField = new JTextArea(1, 30);
	private JTextArea mDestField = new JTextArea(1, 30);
	private JPanel content = new JPanel();
	private JLabel warning = new JLabel();
	private StationFrame mOwner;
	public NewPassengerDialog(StationFrame owner) {
		new JDialog(owner, TextsBundle.getString("dialog_title_addpassenger"));
		
		mOwner = owner;
		setupViews();
		pack();
		setLocationRelativeTo(null);
	}

	private void okButton() {
		String name = mNameField.getText();
		String dest = mDestField.getText(); 
		if(name.isEmpty() || dest.isEmpty())
		{
			warning.setText(TextsBundle.getString("dialog_addPassenger_err_empty"));
			this.repaint();
			return;
		}
		if(mOwner.checkPassangerViewExists(name))
		{
			warning.setText(TextsBundle.getString("dialog_addPassenger_err_inuse"));
			this.repaint();
			return;
		}
		Passenger p = new Passenger(name,dest);
		mOwner.getStation().addPassenger(p);
		setVisible(false);

	}

	private void noButton() {
		setVisible(false);
		
	}
	private void setupViews()
	{
		
		content.setBorder(new EmptyBorder(5, 5, 5, 5));
		content.setLayout(new GridLayout(0, 2, 2, 5));
		JLabel lblName = new JLabel(TextsBundle.getString("dialog_addPassenger_name"));
		JLabel lblDestination = new JLabel(TextsBundle.getString("dialog_addPassenger_destination"));
		JPanel btnPanel = new JPanel();
		JButton okBtn = new JButton(TextsBundle.getString("dialog_add"));
		JButton noBtn = new JButton(TextsBundle.getString("dialog_cancel"));
		warning.setForeground(Color.RED);
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
		
		
		content.add(lblName);
		content.add(mNameField);
		content.add(lblDestination);
		content.add(mDestField);
		content.add(warning);
		getContentPane().add(content,BorderLayout.CENTER);
		getContentPane().add(btnPanel, BorderLayout.SOUTH);

	}
	
}
