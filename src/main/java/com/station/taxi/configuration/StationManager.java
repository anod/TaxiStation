package com.station.taxi.configuration;

import com.station.taxi.model.Station;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author alex
 */
public class StationManager {

	private Marshaller marshaller;
	private Unmarshaller unmarshaller;

	public void setMarshaller(Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public void setUnmarshaller(Unmarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}
	
	public Station load(String fileName) {
		FileInputStream is = null;
        try {
			is = new FileInputStream(fileName);
			Station station = (Station) this.unmarshaller.unmarshal(new StreamSource(is));
		} catch (JAXBException | FileNotFoundException ex) {
			Logger.getLogger(StationManager.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
            if (is != null) {
				try {
					is.close();
				} catch (IOException ex) {
					Logger.getLogger(StationManager.class.getName()).log(Level.SEVERE, null, ex);
				}
            }
        }
		return null;
	}
}
