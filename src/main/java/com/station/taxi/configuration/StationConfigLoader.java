package com.station.taxi.configuration;

import com.station.taxi.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.station.taxi.spring.StationContext;
/**
 * Parse configuration xml and load station
 * @author alex
 * @version 0.2
 */
public class StationConfigLoader {
	private final String mFileName;
	private final StationContext mContext;

	/**
	 * 
	 * @param fileName path to configuration file
	 */
	public StationConfigLoader(String fileName, StationContext context) {
		mFileName = fileName;
		mContext = context;
	}
	
	/**
	 * Load station from configuration file
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Station load() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(mFileName));
        doc.getDocumentElement().normalize();
        
        // parse <system> tag
        TaxiMeter meter = readTaxiMeter(doc);
        Station station = readStation(doc, meter);
        ArrayList<ICab> taxis = readTaxiCabs(doc);
        ArrayList<IPassenger> passengers = readPassengers(doc);
        station.init(taxis, passengers);
		return station;
	}

	/**
	 * Creates passengers from configuration
	 * @param doc
	 * @return
	 */
	private ArrayList<IPassenger> readPassengers(Document doc) {
		ArrayList<IPassenger> result = new ArrayList<>();
		
        NodeList taxis = doc.getElementsByTagName("passenger");
        for(int i=0; i<taxis.getLength() ; i++){
        	 NamedNodeMap attrs = taxis.item(i).getAttributes();
             String name = attrs.getNamedItem("name").getNodeValue();
             String destination = attrs.getNamedItem("destination").getNodeValue();
        	 result.add(mContext.createPassenger(name, destination));
        }
		return result;		
	}

	/**
	 * Creates cabs from configuration
	 * @param doc
	 * @return
	 */
	private ArrayList<ICab> readTaxiCabs(Document doc) {
		ArrayList<ICab> result = new ArrayList<>();

        NodeList taxis = doc.getElementsByTagName("taxi");
        for(int i=0; i<taxis.getLength() ; i++){
        	 NamedNodeMap attrs = taxis.item(i).getAttributes();
             String cabNum = attrs.getNamedItem("number").getNodeValue();
             String whileWaiting = attrs.getNamedItem("whileWaiting").getNodeValue();
        	 result.add(mContext.createCab(Integer.valueOf(cabNum), whileWaiting));
        }
		return result;
	}

	/**
	 * Create a station from configuration
	 * @param doc
	 * @param meter
	 * @return
	 */
	private Station readStation(Document doc, TaxiMeter meter) {
        NodeList stations = doc.getElementsByTagName("station");
        
        NamedNodeMap attrs = stations.item(0).getAttributes();
        String stationName = attrs.getNamedItem("name").getNodeValue();
        String maxWaitingTaxis = attrs.getNamedItem("maxWaitingTaxis").getNodeValue();
        
		return new Station(stationName, Integer.valueOf(maxWaitingTaxis), meter);
	}

	/**
	 * Read taxi meter values
	 * @param doc
	 */
	private TaxiMeter readTaxiMeter(Document doc) {
		String pricePerSecond = doc.getDocumentElement().getAttribute("pricePerSecond");
        String startPrice = doc.getDocumentElement().getAttribute("startPrice");
        
        return new TaxiMeter(
       		Double.parseDouble(startPrice),
        	Double.parseDouble(pricePerSecond)
        );
	}	
	
	
}
