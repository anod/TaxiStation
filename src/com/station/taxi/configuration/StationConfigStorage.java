package com.station.taxi.configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.station.taxi.Cab;
import com.station.taxi.Passenger;
import com.station.taxi.Station;
import com.station.taxi.TaxiMeter;

public class StationConfigStorage {
	private String mConfigFile;
	private Element root = null;
	private Document doc = null;
	public StationConfigStorage(String fileName) {
		mConfigFile = fileName;
		try {
			setup();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void setup() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
        doc = docBuilder.newDocument();
        root = doc.createElement("system");
	}
	public void SaveStation(TaxiMeter meter,String name,int maxWaitingTaxis,ArrayList<Cab> taxis,ArrayList<Passenger> passengers)
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
	private Node writePassengerElements(ArrayList<Passenger> passengers) {
		Element passengersRoot = doc.createElement("passengers");
		for(Passenger p: passengers) {
			Element pChild = doc.createElement("passenger");
			pChild.setAttribute("name", p.getPassangerName());
			pChild.setAttribute("destination", p.getDestination());
			passengersRoot.appendChild(pChild);
		}
		return passengersRoot;
	}
	private Node writeTaxisElements(ArrayList<Cab> taxis) {
		Element taxiRoot = doc.createElement("taxis");
		for(Cab c: taxis) {
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
