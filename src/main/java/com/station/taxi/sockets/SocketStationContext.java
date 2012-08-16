package com.station.taxi.sockets;

import com.station.taxi.model.Station;
import com.station.taxi.spring.StationContext;
import java.net.Socket;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Context of socket station
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
	
	public Server createServer(Station station) {
		Server server = (Server)getApplicationContext().getBean("server", this, station);
		return server;
	}

	public StationWorker createStationWorker(Socket socket, Station station, SocketStationContext context) {
		Worker jsonWorker = (Worker)getApplicationContext().getBean("jsonWorker", socket);
		StationWorker stationWorker = (StationWorker)getApplicationContext().getBean("stationWorker", jsonWorker, station, context);
		return stationWorker;
	}
	
	public Client createClient(String host, Integer port) {
		Client client = (Client)getApplicationContext().getBean("client", host, port);
		return (Client)client;
	}
}
