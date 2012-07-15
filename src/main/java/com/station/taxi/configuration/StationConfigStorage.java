package com.station.taxi.configuration;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.station.taxi.ICab;
import com.station.taxi.Passenger;
import com.station.taxi.Station;
import com.station.taxi.TaxiMeter;
/**
 * 
 * @author Eran Zimbler
 * @version 0.2
 */
public class StationConfigStorage {
	private String mConfigFile;
	private Element root = null;
	private Document doc = null;
	public StationConfigStorage(String fileName) {
		mConfigFile = fileName;
		try {
			setup();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void setup() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
        doc = docBuilder.newDocument();
        root = doc.createElement("system");
	}
	/**
	 * 
	 * @param station
	 */
	public void save(Station station) {
		saveStation(
			station.getDefaultTaxiMeter(), 
			station.getStationName(), 
			station.getMaxWaitingCount(),
			station.getCabs(), 
			station.getPassengers()
		);
	}	
	private void saveStation(TaxiMeter meter,String name,int maxWaitingTaxis,List<ICab> taxis,List<Passenger> passengers)
	{
		root.setAttribute("pricePerSecond", String.valueOf(meter.getPricePerSecond()));
		root.setAttribute("startPrice", String.valueOf(meter.getStartPrice()));
		root.appendChild(writeStationElement(name,maxWaitingTaxis));
		root.appendChild(writeTaxisElements(taxis));
		root.appendChild(writePassengerElements(passengers));
		doc.appendChild(root);
		exportDoc(doc);
	}
	private void exportDoc(Document data) {
		try {
		TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
        FileWriter sw = new FileWriter(mConfigFile);
        DOMSource source = new DOMSource(doc);
        StreamResult result =  new StreamResult(sw);
        trans.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private Node writePassengerElements(List<Passenger> passengers) {
		Element passengersRoot = doc.createElement("passengers");
		for(Passenger p: passengers) {
			Element pChild = doc.createElement("passenger");
			pChild.setAttribute("name", p.getPassangerName());
			pChild.setAttribute("destination", p.getDestination());
			passengersRoot.appendChild(pChild);
		}
		return passengersRoot;
	}
	private Node writeTaxisElements(List<ICab> taxis) {
		Element taxiRoot = doc.createElement("taxis");
		for(ICab c: taxis) {
			Element taxiChild = doc.createElement("taxi");
			taxiChild.setAttribute("number", String.valueOf(c.getNumber()));
			taxiChild.setAttribute("whileWaiting", c.getWhileWaiting());
			taxiRoot.appendChild(taxiChild);
		}
		return taxiRoot;
	}
	private Node writeStationElement(String name, int maxWaitingTaxis) {
		Element station = doc.createElement("station");
		station.setAttribute("name", name);
		station.setAttribute("maxWaitingTaxis", String.valueOf(maxWaitingTaxis));
		return station;
	}

}
