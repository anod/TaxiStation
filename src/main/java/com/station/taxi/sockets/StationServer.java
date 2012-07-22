/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.sockets;

import com.station.taxi.logger.LoggerWrapper;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



	
/**
 *
 * @author alex
 */
public class StationServer implements Server {
	public static final int PORT = 13000;
	
	private ServerSocket mServer;
	private boolean mAccepting = false;
	private final SocketStationContext mStationContext;

	private StationServer(SocketStationContext context) {
		mStationContext = context;
	}
	
	@Override
	public boolean start() {
		try {
			mServer = new ServerSocket(PORT);
		} catch (IOException ex) {
			LoggerWrapper.logException(StationServer.class.getName() , ex);
			return false;
		}
		return true;
	}

	@Override
	public void stop() {
		mAccepting = false;
		if (mServer == null) {
			throw new IllegalStateException("Server is not connected");
		}
		try {
			mServer.close();
		} catch (IOException ex) {
			LoggerWrapper.logException(StationServer.class.getName() , ex);
		}
	}

	@Override
	public void accept() {
		mAccepting = true;
		while(mAccepting) {
			try {
				final Socket socket = mServer.accept();
				ServerWorker w = mStationContext.createWorker(socket);
				new Thread(w).start();
			} catch (IOException ex) {
				LoggerWrapper.logException(StationServer.class.getName() , ex);
				stop();
				break;
			}
			
		}
	}
	
	
	private static final String CONFIG_PATH = "SocketsXMLConfig.xml";

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONFIG_PATH, StationServer.class);
		final SocketStationContext context = new SocketStationContext(applicationContext);
		final Server server = context.createServer();
		boolean isStarted = server.start();
		if (isStarted) {
			server.accept();
			server.stop();
		}
		
	}
}
