/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.sockets;

import java.net.Socket;
import java.util.Date;

/**
 *
 * @author alex
 */
public class ServerWorker implements Runnable {
	private final Socket mSocket;
	
	public ServerWorker(Socket socket) {
		mSocket = socket;
	}
	
	@Override
	public void run() {
		System.out.println(new Date() + " ---> Client connected: " + mSocket.getInetAddress() + ":" + mSocket.getPort());
//		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}
