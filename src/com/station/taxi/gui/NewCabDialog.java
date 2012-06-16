package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.border.EmptyBorder;

public class NewCabDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField mNumber;

	public NewCabDialog(Frame owner) {
		setTitle(TextsBundle.getString("dialog_addcab_title")); //$NON-NLS-1$
		new JDialog(owner, TextsBundle.getString("dialog_title_addcab"));
		setupViews();
		pack();
		setLocationRelativeTo(null);		
	}

	/**
	 * 
	 */
	private void setupViews() {
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
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel(TextsBundle.getString("dialog_addcab_num")); //$NON-NLS-1$
		panel.add(lblNewLabel_1);
		mNumber = new JTextField(32);
		panel.add(mNumber);
		
		JLabel lblNewLabel = new JLabel(TextsBundle.getString("dialog_addcab_waiting")); //$NON-NLS-1$
		panel.add(lblNewLabel);
		
		String[] waitingStrings = { 
			TextsBundle.getString("cab_waiting_eat"),
			TextsBundle.getString("cab_waiting_drink"),
			TextsBundle.getString("cab_waiting_read_news_papper")
		};

		JComboBox comboBox = new JComboBox(waitingStrings);
		panel.add(comboBox);
		getContentPane().add(btnPanel, BorderLayout.SOUTH);
	}
	
	private void okButton() {   
		setVisible(false);  
	}

	private void noButton() {  
		setVisible(false);  
	}

}
