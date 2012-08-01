package com.station.taxi.configuration.jaxb;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author alex
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="system")
public class Config {

	@XmlAttribute(name="startPrice")
	private double startPrice;
	@XmlAttribute(name="pricePerSecond")
	private double oneSecPrice;

	@XmlElement(name="station")
	private ConfigStation stationConfig;
	
	@XmlElementWrapper(name="taxis")
	@XmlElement(name="taxi")
	private List<ConfigTaxi> taxis;

	@XmlElementWrapper(name="passengers")
	@XmlElement(name="passenger")
	private List<ConfigPassenger> passengers;

	public Config() { }
	/**
	 * @return the startPrice
	 */
	public double getStartPrice() {
		return startPrice;
	}

	/**
	 * @param startPrice the startPrice to set
	 */
	public void setStartPrice(double startPrice) {
		this.startPrice = startPrice;
	}

	/**
	 * @return the oneSecPrice
	 */
	public double getOneSecPrice() {
		return oneSecPrice;
	}

	/**
	 * @param oneSecPrice the oneSecPrice to set
	 */
	public void setOneSecPrice(double oneSecPrice) {
		this.oneSecPrice = oneSecPrice;
	}

	/**
	 * @return the taxis
	 */
	public List<ConfigTaxi> getTaxis() {
		return taxis;
	}

	/**
	 * @param taxis the taxis to set
	 */
	public void setTaxis(List<ConfigTaxi> taxis) {
		this.taxis = taxis;
	}

	/**
	 * @return the passengers
	 */
	public List<ConfigPassenger> getPassengers() {
		return passengers;
	}

	/**
	 * @param passengers the passengers to set
	 */
	public void setPassengers(List<ConfigPassenger> passengers) {
		this.passengers = passengers;
	}

	/**
	 * @return the stationConfig
	 */
	public ConfigStation getStationConfig() {
		return stationConfig;
	}

	/**
	 * @param stationConfig the stationConfig to set
	 */
	public void setStationConfig(ConfigStation stationConfig) {
		this.stationConfig = stationConfig;
	}
	

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name="station")
	public static class ConfigStation {
		@XmlAttribute(name="name")
		private String name;

		@XmlAttribute(name="maxWaitingTaxis")
		private int maxWaitingTaxis;

		public ConfigStation() { }
		
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the maxWaitingTaxis
		 */
		public int getMaxWaitingTaxis() {
			return maxWaitingTaxis;
		}

		/**
		 * @param maxWaitingTaxis the maxWaitingTaxis to set
		 */
		public void setMaxWaitingTaxis(int maxWaitingTaxis) {
			this.maxWaitingTaxis = maxWaitingTaxis;
		}

		@Override
		public String toString() {
			return "ConfigStation [" + "maxWaitingTaxis: " + maxWaitingTaxis + ", " + "name: " + name + "]";
		}
		
		
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name="taxi")
	public static class ConfigTaxi {
		@XmlAttribute(name="number")
		private int number;
		@XmlAttribute(name="whileWaiting")
		private String whileWaiting;

		public ConfigTaxi() { }
		/**
		 * @return the number
		 */
		public int getNumber() {
			return number;
		}

		/**
		 * @param number the number to set
		 */
		public void setNumber(int number) {
			this.number = number;
		}

		/**
		 * @return the whileWaiting
		 */
		public String getWhileWaiting() {
			return whileWaiting;
		}

		/**
		 * @param whileWaiting the whileWaiting to set
		 */
		public void setWhileWaiting(String whileWaiting) {
			this.whileWaiting = whileWaiting;
		}

		@Override
		public String toString() {
			return "ConfigTaxi [" + "number: " + number + ", " + "whileWaiting: " + whileWaiting + "]";
		}
		
		
	}
	
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name="passenger")
	public static class ConfigPassenger {
		@XmlAttribute(name="name")
		private String name;
		@XmlAttribute(name="destination")
		private String destination;

		public ConfigPassenger() { }
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the destination
		 */
		public String getDestination() {
			return destination;
		}

		/**
		 * @param destination the destination to set
		 */
		public void setDestination(String destination) {
			this.destination = destination;
		}

		@Override
		public String toString() {
			return "ConfigPassenger [" + "destination: " + destination + ", " + "name: " + name + "]";
		}
		
		
	}

	@Override
	public String toString() {
		return "Config [\n" 
			+ "  oneSecPrice: " + oneSecPrice + ", \n"
			+ "  startPrice: " + startPrice + ", \n"
			+ "  " + stationConfig + " \n"
			+ "  taxis: " + taxis + " \n"
			+ "  passengers: " + passengers + " \n"
		+ "]";
	}
	
	
}
