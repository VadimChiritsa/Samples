package tours;
import tours.Tour;
import java.util.Comparator;

public class TourComparator implements Comparator<Tour>{
	String param = "";
	public TourComparator(String param){
		this.param = param;
	}

	public int compare(Tour o1, Tour o2) {
		switch(param){
		case "Price" :
			return  (int) (o1.GetPrice() - o2.GetPrice());
		case "StartDate" :
			return  o1.GetStartDate().compareTo(o2.GetStartDate());
		case "Length":
			return  o1.GetLength() - o2.GetLength();
		case "TourType":
			return o1.GetTourType().toString().compareTo(o2.GetTourType().toString());
		case "MealType":
			return o1.GetMealType().compareTo(o2.GetMealType());
		case "TransportType":
			return o1.GetTransportType().toString().compareTo(o2.GetTransportType().toString());
		default:
			return  (int) (o1.GetPrice() - o2.GetPrice());
		}
		
	}

}
