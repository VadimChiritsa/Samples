package tours;

import java.util.Date;

import tours.params.MealTypes;
import tours.params.TourTypes;
import tours.params.TransportTypes;

public class RelaxTour extends Tour {

	public RelaxTour(
			Date _startDate, 
			byte _length,
			float _price,
			MealTypes _mealType,
			TransportTypes _transportType) {
		super(_startDate, _length, _price, _mealType, _transportType);
		
		this.tourType = TourTypes.RelaxTour;
	}
	

}
