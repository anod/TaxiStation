package com.station.taxi.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.station.taxi.Cab;
import com.station.taxi.Passenger;
import com.station.taxi.events.CabEventListener;
import com.station.taxi.logger.LoggerWrapper;

public class CabView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Cab mCab;
	private JTable mPassangertable;
	private JLabel mStatusLabel;
	private JButton mBtnArrive;
	private JLabel mIcon;
	private Timer mAnimationTimer;

	public CabView(Cab cab) {
		setBorder(new TitledBorder(null, cab.getNumber()+"", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		
		mCab = cab;
		setupViews();
		
		setStatus(cab);
		setPassangers(cab);	
		setArriveBtn(cab);
		cab.addCabEventListener(new ViewCabEventListener());
		
	}

	interface AnimationCallback {
		void onFinish();
	}
	
	public void animate(final AnimationCallback callback) {
		if (mAnimationTimer!=null && mAnimationTimer.isRepeats()) {
			for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
			    System.out.println(ste + "\n");
			}
			return;
		}
		mAnimationTimer = new Timer(0, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int height = getHeight();
				if (height <= 0) {
					mAnimationTimer.stop();
					callback.onFinish();
					return;
				}
				height -= 1;
				setSize(getWidth(), height);
				repaint();
			}
		});
		
		mAnimationTimer.setDelay(1);
		mAnimationTimer.start();
	}
	
	
	/**
	 * 
	 */
	private void setupViews() {
		mPassangertable = new JTable();
		mPassangertable.setBackground(SystemColor.control);
		mPassangertable.setBorder(new EmptyBorder(0, 10, 0, 10));
		mPassangertable.setShowVerticalLines(false);
		mPassangertable.setShowHorizontalLines(false);
		mPassangertable.setShowGrid(false);
		mPassangertable.setRowSelectionAllowed(false);
		mPassangertable.setEnabled(false);
		add(mPassangertable, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		mBtnArrive = new JButton("Arrive");
		mBtnArrive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				animate(new AnimationCallback() {
					
					@Override
					public void onFinish() {
						mCab.arrive();
						
					}
				});
			}
		});
		panel.add(mBtnArrive);
		
		mStatusLabel = new JLabel("Waiting");
		mStatusLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
		mStatusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		mStatusLabel.setVerticalAlignment(SwingConstants.TOP);
		add(mStatusLabel, BorderLayout.NORTH);
		
		mIcon = new JLabel("");
		mIcon.setVerticalAlignment(SwingConstants.TOP);
		add(mIcon, BorderLayout.WEST);
		
		mIcon.setIcon(ImageUtils.createImageIcon("ic_cab"));
	}


	private void setArriveBtn(Cab cab) {
		if (cab.isDriving()) {
			//mBtnArrive.setVisible(true);
			mBtnArrive.setOpaque(true);
			mBtnArrive.setContentAreaFilled(true);
			mBtnArrive.setBorderPainted(true);
			mBtnArrive.setEnabled(true);			
		} else {
			//mBtnArrive.setVisible(false);
			mBtnArrive.setOpaque(false);
			mBtnArrive.setContentAreaFilled(false);
			mBtnArrive.setBorderPainted(false);
			mBtnArrive.setEnabled(false);
		}
	}


	/**
	 * 
	 */
	private void setPassangers(Cab cab) {
		List<Passenger> passengers = cab.getPassegners();
		int size = passengers.size();
		Object[][] data = new Object[2][2];
		data[0][0] = (size>0) ? passengers.get(0).getPassangerName() : "";
		data[0][1] = (size>1) ? passengers.get(1).getPassangerName() : "";
		data[1][0] = (size>2) ? passengers.get(2).getPassangerName() : "";
		data[1][1] = (size>3) ? passengers.get(3).getPassangerName() : "";		
		mPassangertable.setModel(new DefaultTableModel(
			data, new String[] {"", ""}
		));
		mPassangertable.validate();
	}

	/**
	 * @param cab
	 */
	private void setStatus(Cab cab) {
		
		if (cab.isDriving()) {
			mStatusLabel.setText("Driving [ " + (cab.getDrivingTime()/1000) + " seconds ]");
		} else if(cab.isOnBreak()) {
			int sec = (cab.getBreakTime()/1000);
			String secText = (sec == 1) ? "1 second" : sec + " seconds";
			mStatusLabel.setText("Break [ " + secText + " ]");
		} else if(cab.isWaiting()) {
			mStatusLabel.setText("Waiting, " + cab.getWhileWaiting());
		} else {
			mStatusLabel.setText("Init");
		}
		mStatusLabel.validate();
	}
	
	
	class ViewCabEventListener extends CabEventListener {

		@Override
		public void update(int type, Cab cab) {
			setStatus(cab);
			setPassangers(cab);
			setArriveBtn(cab);			
		}
		
	}
	
	
	
}
