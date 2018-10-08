package readers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tours.*;
import tours.params.MealTypes;
import tours.params.NoSuchTourTypeException;
import tours.params.TourTypes;
import tours.params.TransportTypes;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JSONReader implements IReader {
	JSONParser parser;
	private JSONObject jsonObject;
	
	public JSONReader(){
		parser = new JSONParser();
	}
	public JSONObject getSourceObject(){
		return jsonObject;
	}
	
	
	public ArrayList<Tour> Read(String filePath){
		ArrayList<Tour> list = new ArrayList<Tour>();
		try{
			//Read from file. It's always an object with key 
			jsonObject = (JSONObject) parser.parse(new FileReader(filePath));
			
			JSONArray jsonArr = (JSONArray) jsonObject.get("Tours");
			
			//Convert to a tours array
			for(Object o : jsonArr){
				list.add(Convert((JSONObject)o));
			}
			return list;
			
		} catch (IOException e1) {
            e1.printStackTrace();
        }catch (NoSuchTourTypeException e1) {
            System.out.println(e1.getMessage());
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return list;
	}
	
	private Tour Convert(JSONObject obj) throws NoSuchTourTypeException {
		TourTypes tourType = TourTypes.valueOf(obj.get("TourType").toString());
		TransportTypes transportType = TransportTypes.valueOf(obj.get("TransportType").toString());
		Date startDate = null;
		try {
			startDate = new SimpleDateFormat("dd.mm.yyyy").parse(( obj.get("StartDate").toString()));
		} catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        byte length = (byte)(long) obj.get("Length");
		float price = (float)(double) obj.get("Price");
		MealTypes mealType = MealTypes.valueOf(obj.get("MealType").toString());
		

		//Didn't do this a separate function cause different 
		//tours demand different approaches.
		//Returning proper type of tour	
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

}
