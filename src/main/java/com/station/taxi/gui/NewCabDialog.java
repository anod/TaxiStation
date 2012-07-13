package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.station.taxi.Cab;
import com.station.taxi.Station;
/**
 * Add new cab dialog
 * @author alex
 * @version 0.2
 */
public class NewCabDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Station mStation;
	private JTextField mNumber;
	private JComboBox<String> mWhileWaiting;
	private String[] mWhileWaitingValues;

	public NewCabDialog(StationFrame stationFrame) {
		setTitle(TextsBundle.getString("dialog_addcab_title")); //$NON-NLS-1$
		mStation = stationFrame.getStation();
		new JDialog(stationFrame, TextsBundle.getString("dialog_title_addcab"));
		setupViews();
		pack();
		setLocationRelativeTo(null);		
	}

	/**
	 * Initialize inner components
	 */
	private void setupViews() {
		JPanel btnPanel = new JPanel();
		JButton okBtn = new JButton(TextsBundle.getString("dialog_add"));
		JButton noBtn = new JButton(TextsBundle.getString("dialog_cancel"));

		btnPanel.add(okBtn);
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent ae) {
				addButton();
			}
		});
		noBtn.addActionListener(new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent ae) {
				cancelButton();
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
		mNumber.setInputVerifier(new CabNumberInputVerifier(this, mNumber));
		panel.add(mNumber);
		
		JLabel lblNewLabel = new JLabel(TextsBundle.getString("dialog_addcab_waiting")); //$NON-NLS-1$
		panel.add(lblNewLabel);

		String[] waitingStrings = initWaitingStrings();
		mWhileWaiting = new JComboBox<String>(waitingStrings);
		panel.add(mWhileWaiting);
		getContentPane().add(btnPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Init values for waiting combobox
	 * @return
	 */
	private String[] initWaitingStrings() {
		
		String[] waitingStrings = { 
			TextsBundle.getString("cab_waiting_eat"),
			TextsBundle.getString("cab_waiting_drink"),
			TextsBundle.getString("cab_waiting_read_news_papper")
		};
		
		mWhileWaitingValues = new String[3];
		mWhileWaitingValues[0]=Cab.WAIT_EAT;
		mWhileWaitingValues[1]=Cab.WAIT_DRINK;
		mWhileWaitingValues[2]=Cab.WAIT_NEWSPAPPER;
		
		return waitingStrings;
	}
	
	/**
	 * Click on add button
	 */
	private void addButton() {
		if (!mNumber.getInputVerifier().verify(mNumber)){
			return;
		}

		String whileWaiting = mWhileWaitingValues[mWhileWaiting.getSelectedIndex()];
		int num = Integer.valueOf(mNumber.getText());
		Cab cab = new Cab(num, whileWaiting);
		mStation.addCab(cab);
		setVisible(false);  
	}

	/**
	 * Click on cancel button
	 */
	private void cancelButton() {  
		setVisible(false);  
	}

	/**
	 * Input verifier for Cab number
	 * On errro highlight red and show popup message 
	 */
	private class CabNumberInputVerifier extends InputVerifier implements KeyListener {
		private static final int NUM_MIN_LEN = 3;
		private static final int NUM_MAX_LEN = 5;
		private JDialog mPopup;
		private Color mColor;
		private JLabel mImage;
		private JLabel mMessageLabel;
		
		public CabNumberInputVerifier(JDialog parent, JComponent component) {
			mPopup = new JDialog(parent);
			mColor = new Color(243, 255, 159);
			mImage = new JLabel(ImageUtils.createImageIcon("error"));
			mMessageLabel = new JLabel("Error msessage");
			component.addKeyListener(this);
	        initComponents();
		}

		private void initComponents() {
			mPopup.getContentPane().setLayout(new FlowLayout());
			mPopup.setUndecorated(true);
			mPopup.getContentPane().setBackground(mColor);
			mPopup.add(mImage);
			mPopup.add(mMessageLabel);
			mPopup.setFocusableWindowState(false);
		}
 
		private boolean validateCabNumber(JTextField input) {
			String numberStr = input.getText();
			
			if (numberStr.length() < NUM_MIN_LEN || numberStr.length() > NUM_MAX_LEN) {
				String errText = String.format(TextsBundle.getString("dialog_addcab_num_err_length"), NUM_MIN_LEN, NUM_MAX_LEN);
				mMessageLabel.setText(errText);
				return false;
			}
			
			for (char c: numberStr.toCharArray()){
				if(!Character.isDigit(c)){
					mMessageLabel.setText(TextsBundle.getString("dialog_addcab_num_err_chars"));
					return false;
				}
			}
			
			return true;
		}
		
		@Override
		public boolean verify(JComponent component) {
			if (!validateCabNumber((JTextField)component)) {
				component.setBackground(Color.PINK);
				mPopup.setSize(0, 0);
				mPopup.setLocationRelativeTo(component);
				Point point = mPopup.getLocation();
				Dimension cDim = component.getSize();
				mPopup.setLocation(point.x-(int)cDim.getWidth()/2, point.y+(int)cDim.getHeight()/2);
				mPopup.pack();
				mPopup.setVisible(true);
				return false;
			}
		
			component.setBackground(Color.WHITE);
			return true;
		}

		@Override
		public void keyTyped(KeyEvent e) { }

		@Override
		public void keyPressed(KeyEvent e) {
			mPopup.setVisible(false);
		}

		@Override
		public void keyReleased(KeyEvent e) {}
		
	}
}
