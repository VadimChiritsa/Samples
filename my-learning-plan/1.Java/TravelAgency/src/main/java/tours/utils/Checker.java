package tours.utils;

import tours.Tour;
import tours.params.MealTypes;
import tours.params.TourTypes;
import tours.params.TransportTypes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Aliaksandr_Sheliutsi on 1/18/2017.
 */
public 	class Checker{
    StringBuilder param;
    public Checker(String param){
        this.param = new StringBuilder(param);
    }
    //Search parameter is a string like "Price>19.28&Length=7"
    //TourType, Price, Length, StartDate, MealType, TransportType
    public boolean Check(Tour tour){
        boolean tourType = true,
                transportType = true,
                price = true,
                length = true,
                startDate = true,
                mealType = true;

        //Price search
        Matcher matcher = Pattern.compile("Price(.*?)(&|$)").matcher(param);
        if(matcher.find())
        {

            String condition = matcher.group(1);
            char sign = condition.charAt(0);
            condition = condition.substring(1);
            switch(sign){
                case '>':
                    price = tour.GetPrice() > Float.valueOf(condition);
                    break;
                case '<':
                    price = tour.GetPrice() < Float.valueOf(condition);
                    break;
                case '=':
                    price = tour.GetPrice() == Float.valueOf(condition);
                    break;
            }
        }
        //TourType
        matcher = Pattern.compile("TourType(.*?)(&|$)").matcher(param);
        if(matcher.find())
        {
            String condition = matcher.group(1).substring(1);
            tourType = TourTypes.valueOf(condition) == tour.GetTourType();
        }

        //TransportType
        matcher = Pattern.compile("TransportType(.*?)(&|$)").matcher(param);
        if(matcher.find())
        {
            String condition = matcher.group(1).substring(1);
            transportType = TransportTypes.valueOf(condition) == tour.GetTransportType();
        }

        //Length search
        matcher = Pattern.compile("Lenght(.*?)(&|$)").matcher(param);
        if(matcher.find())
        {
            String condition = matcher.group(1);
            char sign = condition.charAt(0);
            condition = condition.substring(1);
            switch(sign){
                case '>':
                    length = tour.GetPrice() > Float.valueOf(condition);
                    break;
                case '<':
                    length = tour.GetPrice() < Float.valueOf(condition);
                    break;
                case '=':
                    length = tour.GetPrice() == Float.valueOf(condition);
                    break;
            }
        }

        //StartDate
        matcher = Pattern.compile("StartDate(.*?)(&|$)").matcher(param);
        if(matcher.find())
        {
            String condition = matcher.group(1);
            char sign = condition.charAt(0);
            condition = condition.substring(1);
            try{
                switch(sign){
                    case '>':
                        startDate = tour.GetStartDate().compareTo(new SimpleDateFormat("dd.mm.yyyy").parse((condition))) > 0;
                        break;
                    case '<':
                        startDate = tour.GetStartDate().compareTo(new SimpleDateFormat("dd.mm.yyyy").parse((condition))) < 0;
                        break;
                    case '=':
                        startDate = tour.GetStartDate().compareTo(new SimpleDateFormat("dd.mm.yyyy").parse((condition))) == 0;
                        break;
                }
            }catch(ParseException e ){
                e.printStackTrace();
            }
        }

        //MealType
        matcher = Pattern.compile("MealType(.*?)(&|$)").matcher(param);
        if(matcher.find())
        {
            String condition = matcher.group(1).substring(1);
            tourType = MealTypes.valueOf(condition) == tour.GetMealType();
        }

        return price&&length&startDate&tourType&&mealType&&transportType;
    }
}
