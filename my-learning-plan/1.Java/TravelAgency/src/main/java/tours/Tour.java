package tours;

import java.util.Date;

import tours.params.MealTypes;
import tours.params.TourTypes;
import tours.params.TransportTypes;

public abstract class  Tour {
	public Tour(
				//TourTypes _tourType,
				Date _startDate,
				byte _length,
				float _price,
				MealTypes _mealType,
				TransportTypes _trasportType){
		
		//this.tourType = _tourType;
		this.transportType = _trasportType;
		this.startDate = _startDate;
		this.length = _length;
		this.price = _price;
		this.mealType = _mealType;
		
	}

	protected TourTypes tourType;
	
	protected Date startDate;
	
	protected byte length;
	
	protected float price;

	protected TransportTypes transportType;
	
	protected MealTypes mealType;
	
	public  TourTypes GetTourType(){
		return tourType;
	}

	public  TransportTypes GetTransportType(){
		return transportType;
	}
	
	public  Date GetStartDate(){
		return startDate;
	}
	
	public  byte GetLength(){
		return length;
	}
	
	public  float GetPrice(){
		return price;
	}

	public  MealTypes GetMealType(){
		return mealType;
	}
	

	

}
