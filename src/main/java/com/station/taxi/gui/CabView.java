package com.station.taxi.gui;

import com.station.taxi.ICab;
import com.station.taxi.IPassenger;
import com.station.taxi.events.CabEventListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
/**
 * Represents cab on the panel
 * @author alex
 * @version 0.2
 */
public class CabView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ICab mCab;
	private JTable mPassangertable;
	private JLabel mStatusLabel;
	private JButton mBtnArrive;
	private JLabel mIcon;
	private JLayeredPane mIconLayerdPane;
	private JLabel mWaitingIcon;
	private JLabel mTotalEarningsLabel;
	private MouseAdapter mClickAdapter;
	
	
	public CabView(ICab cab) {
		setBorder(new TitledBorder(null, cab.getNumber()+"", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		setLayout(new BorderLayout(0, 0));
		
		setPreferredSize(new Dimension(240, 140));
		mCab = cab;
		mClickAdapter = initClickAdpater();
		
		setupViews();
		
		setStatus(cab);
		setPassangers(cab);	
		setArriveBtn(cab);
		cab.addCabEventListener(new ViewCabEventListener());
		addMouseListener(mClickAdapter);
	}


	private MouseAdapter initClickAdpater() {
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() == 2 && mCab.isDriving()) {
					mCab.arrive();
				}
			}			
		};
	}
	

	/**
	 * Refresh cab ui
	 */
	public void refresh() {
		setStatus(mCab);
		setPassangers(mCab);
		setArriveBtn(mCab);	
	}
	
	/**
	 * Initialize inner components 
	 */
	private void setupViews() {
		
		JPanel mInfoPanel = new JPanel();
		FlowLayout fl_mInfoPanel = (FlowLayout) mInfoPanel.getLayout();
		fl_mInfoPanel.setHgap(0);
		mInfoPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		add(mInfoPanel, BorderLayout.CENTER);
		mPassangertable = new JTable();
		mInfoPanel.add(mPassangertable);
		mPassangertable.setBackground(SystemColor.control);
		mPassangertable.setBorder(new EmptyBorder(0,0,0,0));
		mPassangertable.setShowVerticalLines(false);
		mPassangertable.setShowHorizontalLines(false);
		mPassangertable.setShowGrid(false);
		mPassangertable.setRowSelectionAllowed(false);
		mPassangertable.setEnabled(false);
		mPassangertable.setPreferredSize(new Dimension(140, 40));
		mPassangertable.addMouseListener(mClickAdapter);
		mPassangertable.setModel(new DefaultTableModel(	new String[][] {
			{ "", "" }, { "", "" } }, new String[] { "", "" }
		));
		mBtnArrive = new JButton(TextsBundle.getString("btn_arrive"));
		mInfoPanel.add(mBtnArrive);
		mBtnArrive.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mCab.arrive();
			}
		});
		
		mStatusLabel = new JLabel("StatusLablel");
		mStatusLabel.setBorder(new EmptyBorder(0, 10, 0, 10));
		mStatusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		mStatusLabel.setVerticalAlignment(SwingConstants.TOP);
		mStatusLabel.addMouseListener(mClickAdapter);
		add(mStatusLabel, BorderLayout.NORTH);
		
		
		mIconLayerdPane = new JLayeredPane();
		mIconLayerdPane.setPreferredSize(new Dimension(72, 72));
		mIconLayerdPane.addMouseListener(mClickAdapter);
		add(mIconLayerdPane, BorderLayout.WEST);
		
		mIcon = new JLabel("");
		mIcon.setSize(new Dimension(72, 72));
		mIcon.setBounds(0, 0, 72, 76);
		mIcon.setVerticalAlignment(SwingConstants.TOP);
		mIcon.setIcon(ImageUtils.createImageIcon("ic_cab"));
		mIconLayerdPane.add(mIcon);
		mIconLayerdPane.setLayer(mIcon, 0);
		mWaitingIcon = new JLabel("");
		mWaitingIcon.setVisible(false);
		mWaitingIcon.setSize(new Dimension(76, 76));
		mWaitingIcon.setBounds(0, 0, 72, 76);
		mWaitingIcon.setVerticalAlignment(SwingConstants.BOTTOM);
		mWaitingIcon.setHorizontalAlignment(SwingConstants.RIGHT);
		mWaitingIcon.setIcon(ImageUtils.createImageIcon("ic_waiting_drink"));
		mIconLayerdPane.add(mWaitingIcon);
		mIconLayerdPane.setLayer(mWaitingIcon, 1);
		
		mTotalEarningsLabel = new JLabel(String.format(TextsBundle.getString("cab_total_earnings"), 0.0f)); //$NON-NLS-1$
		mTotalEarningsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(mTotalEarningsLabel, BorderLayout.SOUTH);
	}

	/**
	 * Change arrive button according to cab state
	 * @param cab
	 */
	private void setArriveBtn(ICab cab) {
		if (cab.isDriving()) {
			mBtnArrive.setVisible(true);
		} else {
			mBtnArrive.setVisible(false);
		}
	}


	/**
	 * Add passengers to the table component
	 */
	private void setPassangers(ICab cab) {
		List<IPassenger> passengers = cab.getPassegners();
		int size = passengers.size();
		String name = "";
		name = (size>0) ? passengers.get(0).getPassangerName() : "";
		mPassangertable.getModel().setValueAt(name, 0, 0);
		name = (size>1) ? passengers.get(1).getPassangerName() : "";
		mPassangertable.getModel().setValueAt(name, 0, 1);
		name = (size>2) ? passengers.get(2).getPassangerName() : "";
		mPassangertable.getModel().setValueAt(name, 1, 0);
		name = (size>3) ? passengers.get(3).getPassangerName() : "";
		mPassangertable.getModel().setValueAt(name, 1, 1);		
//		mPassangertable.validate();
	}

	/**
	 * Update status text and icon
	 * @param cab
	 */
	private void setStatus(ICab cab) {

		String text;
		if (cab.isDriving()) {
			text = String.format(TextsBundle.getString("cab_status_driving"), cab.getDestination(), (cab.getDrivingTime()/1000), cab.getMeter().getCurrentValue());
			mWaitingIcon.setVisible(false);
		} else if(cab.isOnBreak()) {
			text = String.format(TextsBundle.getString("cab_status_onbreak"), (cab.getBreakTime()/1000));
			mWaitingIcon.setVisible(false);
		} else if(cab.isWaiting()) {
			text = TextsBundle.getString("cab_status_waiting");
			mWaitingIcon.setIcon(ImageUtils.createImageIcon("ic_waiting_"+cab.getWhileWaiting()));
			mWaitingIcon.setVisible(true);
		} else {
			text = TextsBundle.getString("cab_status_init");
			mWaitingIcon.setVisible(false);
		}
		mTotalEarningsLabel.setText(String.format(TextsBundle.getString("cab_total_earnings"), cab.getTotalEarning()));
		mStatusLabel.setText(text);
		mStatusLabel.validate();
	}
	
	/**
	 * Listener of cab state changes and update the UI
	 */
	class ViewCabEventListener extends CabEventListener {
		@Override
		public void update(int type, ICab cab) {
			refresh();
		}
	}
	
	
}
