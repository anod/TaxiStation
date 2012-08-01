package com.station.taxi.configuration.jaxb;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author alex
 */
public class ConfigManager {
	
	public Config load(String fileName) {
		FileInputStream is = null;
		Config config = null;
        try {
			JAXBContext context = JAXBContext.newInstance(Config.class.getPackage().getName());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<Config> element = (JAXBElement<Config>) unmarshaller.unmarshal(new FileInputStream(fileName));
			config = element.getValue();
		} catch (JAXBException | IOException ex) {
			Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
            if (is != null) {
				try {
					is.close();
				} catch (IOException ex) {
					Logger.getLogger(ConfigManager.class.getName()).log(Level.SEVERE, null, ex);
				}
            }
        }
		return config;
	}
}
