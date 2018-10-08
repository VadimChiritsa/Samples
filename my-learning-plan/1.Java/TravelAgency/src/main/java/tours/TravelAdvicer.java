package tours;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tours.params.TourTypes;
import tours.params.MealTypes;
import tours.params.TransportTypes;
import tours.utils.Checker;;

public class TravelAdvicer {
	
	private static TravelAdvicer instance;
		
	public static TravelAdvicer getInstance(){
		if(instance == null)
			instance = new TravelAdvicer();
		return instance;
	}
	/***
	 * 
	 * @param tours
	 * @param searchParameter - is string parameter like "Price>19.28&Length=7"
	 * @return
	 */
	public ArrayList<Tour> Search(ArrayList<Tour> tours, String searchParameter){
		
		ArrayList<Tour> list = new ArrayList<Tour>();
		Checker checker = new Checker(searchParameter);
		for(Tour tour : tours){
			if(checker.Check(tour) ){
				list.add(tour);
			}
		}
		return list;
	}
	
	public ArrayList<Tour> Sort(ArrayList<Tour> tours, String param){
		TourComparator comparator = new TourComparator(param);
		Collections.sort(tours, comparator);
		return tours;
	}
	

}
