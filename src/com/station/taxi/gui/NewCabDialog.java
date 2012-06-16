package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewCabDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField mNumber;

	public NewCabDialog(Frame owner) {
		new JDialog(owner, TextsBundle.getString("dialog_title_addcab"));

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
		mNumber = new JTextField(32);
		getContentPane().add(mNumber, BorderLayout.NORTH);
		getContentPane().add(btnPanel, BorderLayout.SOUTH);
		pack();
	}
	
	private void okButton() {   
	      setVisible(false);  
	   }  

	private void noButton() {  
	      setVisible(false);  
	}

}
