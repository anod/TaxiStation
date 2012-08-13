package com.station.taxi.configuration;

import com.station.taxi.configuration.jaxb.Config;
import com.station.taxi.configuration.jaxb.ConfigManager;

/**
 *
 * @author alex
 */
public class JAXBTestMain {
	private static final String SCHEMA_XSD = "src/main/resources/jaxb/config.xsd";
	private static final String CONFIG_XML = "configs/config1.xml";
	private static final String CONFIG2_XML = "configs/config2.xml";

	public static void main(String[] args) {
		
		
		ConfigManager manager = new ConfigManager();
		Config config = manager.load(CONFIG_XML,SCHEMA_XSD);
		System.out.println("Config is: "+config);
		
		manager.save(config, CONFIG2_XML);
	}
}
