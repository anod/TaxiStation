/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.sockets;

import com.station.taxi.model.Station;
import com.station.taxi.spring.StationContext;
import java.net.Socket;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author alex
 */
public class SocketStationContext extends StationContext {
	private static final String CONFIG_PATH = "SocketsXMLConfig.xml";
	
	/**
	 * Factory method to create instance SocketStationContext
	 * based on XML configuration
	 * @return 
	 */
	public static SocketStationContext readFromXml() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONFIG_PATH, StationServer.class);
		return new SocketStationContext(applicationContext);
	}
	
	public SocketStationContext(ApplicationContext applicationContext) {
		super(applicationContext);
	}
	
	public Server createServer() {
		Server server = (Server)getApplicationContext().getBean("server", this);
		return server;
	}

	public ServerWorker createWorker(Socket socket, Station station, SocketStationContext context) {
		ServerWorker worker = (ServerWorker)getApplicationContext().getBean("worker", socket, station, context);
		return worker;
	}
	
	public Client createClient() {
		Client client = (Client)getApplicationContext().getBean("client", this);
		return client;
	}
}
