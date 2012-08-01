/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.configuration.jaxb;

import com.station.taxi.configuration.jaxb.Config.ConfigPassenger;
import com.station.taxi.configuration.jaxb.Config.ConfigStation;
import com.station.taxi.configuration.jaxb.Config.ConfigTaxi;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 *
 * @author alex
 */
@XmlRegistry
public class ObjectFactory {
	private final static QName sQName = new QName("", "system");
	
	public Config createConfig() {
		return new Config();
	}
	
	public ConfigTaxi createConfigTaxi() {
		return new ConfigTaxi();
	}
	
	public ConfigPassenger createConfigPassenger() {
		return new ConfigPassenger();
	}
	
	public ConfigStation createConfigStation() {
		return new ConfigStation();
	}
	
	@XmlElementDecl(namespace = "", name = "system")
	public JAXBElement<Config> createConfig(Config value) {
		return new JAXBElement<>(sQName,Config.class,null,value);
	}
}
