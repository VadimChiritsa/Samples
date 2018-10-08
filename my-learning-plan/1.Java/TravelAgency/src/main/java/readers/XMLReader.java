package readers;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import tours.Cruiz;
import tours.ExcursionTour;
import tours.HealingTour;
import tours.RelaxTour;
import tours.ShoppingTour;
import tours.Tour;
import tours.params.MealTypes;
import tours.params.NoSuchTourTypeException;
import tours.params.TourTypes;
import tours.params.TransportTypes;


public class XMLReader implements IReader{
	DocumentBuilder db;
	DocumentBuilderFactory dbf;
	Document doc = null;
	public XMLReader() {
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	public Document getSourceObject() {
		return doc;
		
	}
	
	
	public ArrayList<Tour> Read(String filePath){
		ArrayList<Tour> list = new ArrayList<>();
		
		try {
			doc = db.parse(new File(filePath));
			NodeList elements = doc.getElementsByTagName("Tour");
			for(int i = 0; i < elements.getLength(); i++){
				list.add(Convert((Element)elements.item(i)));
			}
			
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}catch (NoSuchTourTypeException e1) {
			System.out.println(e1.getMessage());
		}
		return list;
	}
	
	private Tour Convert(Element elem) throws NoSuchTourTypeException {
		TourTypes tourType = TourTypes.valueOf(getValue(elem, "TourType"));
		TransportTypes transportType = TransportTypes.valueOf(getValue(elem, "TransportType"));
		Date startDate = null;
		try {
			startDate = new SimpleDateFormat("dd.mm.yyyy").parse(( getValue(elem, "StartDate")));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		byte length = Byte.valueOf(getValue(elem,"Length"));
		float price = Float.parseFloat(getValue(elem,"Price"));
		MealTypes mealType = MealTypes.valueOf(getValue(elem, "MealType"));
		
		//Didn't do this a separate function cause different 
		//tours demand different approaches 
		switch(tourType){
		case RelaxTour:
			return new RelaxTour(startDate, length, price, mealType, transportType);
		case ExcursionTour:
			return new ExcursionTour(startDate, length, price, mealType, transportType);
		case HealingTour:
			return new HealingTour(startDate, length, price, mealType, transportType);
		case ShoppingTour:
			return new ShoppingTour(startDate, length, price, mealType, transportType);
		case Cruiz:
			return new Cruiz(startDate, length, price, mealType, transportType);
		default: 
			throw new NoSuchTourTypeException("Illegal expression in TourType");
		}
	}
	private String getValue(Element elem, String tagName){
		return elem.getElementsByTagName(tagName).item(0).getTextContent();
	}
}
