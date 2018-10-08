import java.util.ArrayList;

import readers.XMLReader;
import tours.Tour;
import tours.TravelAdvicer;

public class MainClass {
	
	static ArrayList<Tour> tourBase;
	static String filePath = "src\\main\\resources\\tours.xml";

	//Format - "Price>19.24&Length>7&TransportType=Ship"
	static String searchParam = "";

	//Sort params:
    //Price - sort by price from low.
    //StartDate - from the nearest date
    //Length - from low to high
    //TourType - in alphabetic order
    //MealType - by fullability
    //TransportType - in alphabetic order
	static String sortParam = "TransportType";
	
	public static void main(String[] args) {
		XMLReader reader = new XMLReader();
		tourBase = reader.Read(filePath);
		ArrayList<Tour> list = TravelAdvicer.getInstance().Search(tourBase, searchParam);
		tourBase = TravelAdvicer.getInstance().Sort(reader.Read(filePath), sortParam);
		System.out.print(list);
	}

}
