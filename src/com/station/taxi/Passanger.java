package com.station.taxi;

public class Passanger {
	private static final int STATE_WAITING = 0;
	private static final int STATE_DRIVING = 1; //TODO: better name
	private static final int STATE_EXIT = 2;

	private int mExitTime = 0;
	private String mName;
	private String mDestination;

	public Passanger(String name, String destination) {
		mName = name;
		mDestination = destination;
	}
	
	
	private void onWaitingState() {
		// TODO assign mExitTime
	}
}
