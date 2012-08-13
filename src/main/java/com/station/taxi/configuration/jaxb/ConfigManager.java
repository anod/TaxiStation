package com.station.taxi.configuration.jaxb;

import com.station.taxi.logger.LoggerWrapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author alex
 */
public class ConfigManager {
	
	public Config load(String fileName, String schemaFileName) {
		FileInputStream is = null;
		Config config = null;
        try {
			JAXBContext context = JAXBContext.newInstance(Config.class.getPackage().getName());
			Unmarshaller unmarshaller = context.createUnmarshaller();
			applySchema(unmarshaller, schemaFileName);
			JAXBElement<Config> element = (JAXBElement<Config>) unmarshaller.unmarshal(new FileInputStream(fileName));
			config = element.getValue();
		} catch (JAXBException | IOException ex) {
			LoggerWrapper.logException(ConfigManager.class.getName(), ex);
		} finally {
            if (is != null) {
				try {
					is.close();
				} catch (IOException ex) {
					LoggerWrapper.logException(ConfigManager.class.getName(), ex);
				}
            }
        }
		return config;
	}
	
	public void save(Config config, String fileName) {
		try {
			ObjectFactory facotry = new ObjectFactory();
			JAXBElement<Config> configElement = facotry.createConfig(config);
			JAXBContext context = JAXBContext.newInstance(Config.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(configElement, new FileOutputStream(fileName));
		} catch (FileNotFoundException | JAXBException ex) {
			LoggerWrapper.logException(ConfigManager.class.getName(), ex);
		}
	}
	
	private void applySchema(Unmarshaller unmarshaller, String schemaFileName) {
		if (schemaFileName == null) {
			return;
		}
		File schemaFile = new File(schemaFileName);
		if (!schemaFile.canRead()) {
			LoggerWrapper.logException(ConfigManager.class.getName(), new Exception("File cannot be read: "+schemaFileName));
			return;
		}
		
		Schema schema = getSchema(schemaFile);
		if (schema == null) {
			return;
		}
		
		unmarshaller.setSchema(schema);
	}
	
	private Schema getSchema(File schemaFile) {
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = null;
		try {
			schema = sf.newSchema(schemaFile);
		} catch (SAXException ex) {
			LoggerWrapper.logException(ConfigManager.class.getName(), ex);
		}
		return schema;
	} 
}
